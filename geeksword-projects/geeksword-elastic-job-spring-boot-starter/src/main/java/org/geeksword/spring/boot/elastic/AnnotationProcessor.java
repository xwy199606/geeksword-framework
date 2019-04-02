package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.RegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.geeksword.spring.boot.elastic.annotations.*;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class AnnotationProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationContextAware, ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware, BeanClassLoaderAware {


    private ApplicationContext applicationContext;
    private Environment environment;
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;
    private BeanFactory beanFactory;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return null;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes enableElasticJobAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableElasticJob.class.getName()));
        AnnotationAttributes enableJobScannerAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableJobScanner.class.getName()));

        boolean enableJobEvent = enableElasticJobAttributes.getBoolean("enableJobEvent");
        String[] basePackages = enableJobScannerAttributes.getStringArray("basePackage");

        ClassPathScanningCandidateComponentProvider classScanner = getClassScanner();
        classScanner.setResourceLoader(this.resourceLoader);
        AnnotationTypeFilter simpleJobSAnnotationTypeFilter = new AnnotationTypeFilter(ASimpleJob.class);
        AnnotationTypeFilter dataFlowAnnotationTypeFilter = new AnnotationTypeFilter(ADataflowJob.class);
        classScanner.addIncludeFilter(simpleJobSAnnotationTypeFilter);
        classScanner.addIncludeFilter(dataFlowAnnotationTypeFilter);

        if (ArrayUtils.isEmpty(basePackages)) {
            basePackages = new String[]{"org.geeksword"};
        }

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = classScanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                try {
                    AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) candidateComponent;
                    Class<?> beanClass = abstractBeanDefinition.resolveBeanClass(classLoader);
                    Component mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanClass, Component.class);
                    String beanName;
                    if (StringUtils.isNotEmpty(mergedAnnotation.value())) {
                        beanName = mergedAnnotation.value();
                    } else {
                        beanName = "job" + beanClass.getSimpleName();
                    }

                    ASimpleJob annotation = AnnotationUtils.findAnnotation(beanClass, ASimpleJob.class);
                    if (annotation != null) {
                        if (SimpleJob.class.isAssignableFrom(beanClass)) {
                            String name = beanName + "SimpleJob";
                            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(createSimpleJob(beanClass)).
                                    overwrite(true).build();
                            register(beanDefinitionRegistry, enableJobEvent, candidateComponent, beanName, name, liteJobConfiguration, beanClass);
                            continue;
                        }
                        log.warn("{} is not SimpleJob Instance", beanName);
                    }

                    ADataflowJob aDataflowJob = AnnotationUtils.findAnnotation(beanClass, ADataflowJob.class);
                    if (aDataflowJob != null) {
                        if (SimpleJob.class.isAssignableFrom(beanClass)) {
                            String name = beanName + "DataFlowJob";
                            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(createDataFlowJob(beanClass)).
                                    overwrite(true).build();
                            register(beanDefinitionRegistry, enableJobEvent, candidateComponent, beanName, name, liteJobConfiguration, beanClass);
                            continue;
                        }
                        log.warn("{} is not DataflowJob Instance", beanName);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注册bean
     *
     * @param beanDefinitionRegistry
     * @param enableJobEvent
     * @param candidateComponent
     * @param beanName
     * @param name
     * @param liteJobConfiguration
     */
    private void register(BeanDefinitionRegistry beanDefinitionRegistry, boolean enableJobEvent,
                          BeanDefinition candidateComponent,
                          String beanName,
                          String name,
                          LiteJobConfiguration liteJobConfiguration,
                          Class beanClass) {
        AJob mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanClass, AJob.class);
        String registerCenterRef = mergedAnnotation.registerCenterRef();
        RegistryCenter registryCenter;
        //获取注册中心
        if (StringUtils.isEmpty(registerCenterRef)) {
            registryCenter = beanFactory.getBean(RegistryCenter.class);
        } else {
            registryCenter = ((ListableBeanFactory) beanFactory).getBeansOfType(RegistryCenter.class).get(registerCenterRef);
            if (registryCenter == null) {
                throw new NoSuchBeanDefinitionException("bean name {} do not exist", registerCenterRef);
            }
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
        beanDefinitionBuilder.addConstructorArgReference(beanName)
                .addConstructorArgValue(registryCenter)
                .addConstructorArgValue(liteJobConfiguration);
        beanDefinitionBuilder.setInitMethodName("init");
        //是否开启job事件监听
        if (enableJobEvent) {
            ElasticJobListener[] elasticJobListeners;
            Map<String, ElasticJobListener> beansOfType = ((ListableBeanFactory) beanFactory).getBeansOfType(ElasticJobListener.class);
            if (!CollectionUtils.isEmpty(beansOfType)) {
                elasticJobListeners = beansOfType.keySet().toArray(new ElasticJobListener[0]);
            } else {
                elasticJobListeners = new ElasticJobListener[0];
            }
            JobEventConfiguration jobEventConfiguration = beanFactory.getBean(JobEventConfiguration.class);
            beanDefinitionBuilder.addConstructorArgValue(jobEventConfiguration);
            beanDefinitionBuilder.addConstructorArgValue(elasticJobListeners);
        } else {
            beanDefinitionBuilder.addConstructorArgValue(new ElasticJobListener[0]);
        }
        //注册job类
        beanDefinitionRegistry.registerBeanDefinition(beanName, candidateComponent);
        //注册job调度类
        beanDefinitionRegistry.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());
    }


    /**
     * 创建SimpleJobConfiguration
     *
     * @param beanClass
     * @return
     */
    private SimpleJobConfiguration createSimpleJob(Class beanClass) {
        AJob mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanClass, AJob.class);
        ElasticJobConfig elasticJobConfig = beanFactory.getBean(ElasticJobConfig.class);
        return new SimpleJobConfiguration(createJobCoreConfiguration(mergedAnnotation, beanClass, elasticJobConfig.getSimpleJob()), beanClass.getCanonicalName());
    }

    private DataflowJobConfiguration createDataFlowJob(Class beanClass) {
        AJob mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanClass, AJob.class);
        ElasticJobConfig elasticJobConfig = beanFactory.getBean(ElasticJobConfig.class);
        return new DataflowJobConfiguration(createJobCoreConfiguration(mergedAnnotation, beanClass, elasticJobConfig.getDataflowJob()), beanClass.getCanonicalName(), true);
    }

    private JobCoreConfiguration createJobCoreConfiguration(AJob aJob, Class jobClass, ElasticJobConfig.JobConfig jobConfig) {
        String cron = aJob.cron();
        int shardingTotalCount = aJob.shardingTotalCount();
        String shardingItemParameters = aJob.shardingItemParameters();
        if (StringUtils.isEmpty(cron)) {
            cron = Optional.ofNullable(jobConfig).map(ElasticJobConfig.JobConfig::getCron).orElse(null);
            if (StringUtils.isEmpty(cron)) {
                throw new IllegalArgumentException("corn cannot null or empty");
            }
        }
        if (shardingTotalCount == -1) {
            shardingTotalCount = Optional.ofNullable(jobConfig).map(ElasticJobConfig.JobConfig::getShardingTotalCount).orElse(-1);
            if (shardingTotalCount == -1) {
                throw new IllegalArgumentException("shardingTotalCount cannot null or empty");
            }
        }
        if (StringUtils.isEmpty(shardingItemParameters)) {
            shardingItemParameters = Optional.ofNullable(jobConfig).map(ElasticJobConfig.JobConfig::getShardingItemParameters).orElse(null);
//            if (StringUtils.isEmpty(shardingItemParameters)) {
//                throw new IllegalArgumentException("shardingItemParameters cannot null or empty");
//            }
        }
        return JobCoreConfiguration.newBuilder(
                jobClass.getName(),
                cron,
                shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 构造Class扫描器，设置了只扫描顶级接口，不扫描内部类
     *
     * @return
     */
    private ClassPathScanningCandidateComponentProvider getClassScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment)
                ;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
