package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.RegistryCenter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.geeksword.spring.boot.elastic.annotations.AJob;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiFunction;

public class JobProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationContextAware, InstantiationAwareBeanPostProcessor, CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(JobProcessor.class);


    @Setter
    private BeanFactory beanFactory;
    @Setter
    private ApplicationContext applicationContext;

    private AnnotationAttributes enableElasticJobAttributes;
    private AnnotationAttributes enableJobScannerAttributes;

    @Autowired
    private ElasticJobConfig elasticJobConfig;

    @Autowired
    private RegistryCenter defaultRegistryCenter;

    @Autowired
    private Map<String, RegistryCenter> registryCenterMap;

    @Autowired(required = false)
    private Map<String, ElasticJobListener> elasticJobListenerMap;

    Map<Class<? extends ElasticJob>, BiFunction<Class, AJob, JobTypeConfiguration>> functionMap;

    {
        functionMap = new HashMap<>();
        functionMap.put(SimpleJob.class, this::createSimpleJob);
        functionMap.put(DataflowJob.class, this::createDataFlowJob);
    }


    public JobProcessor(AnnotationAttributes enableElasticJobAttributes, AnnotationAttributes enableJobScannerAttributes) {
        this.enableElasticJobAttributes = enableElasticJobAttributes;
        this.enableJobScannerAttributes = enableJobScannerAttributes;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String[] basePackages = enableJobScannerAttributes.getStringArray("basePackage");
        Class<?> beanClass = bean.getClass();
        if (!packageMatch(beanClass, basePackages)) {
            return bean;
        }

        Optional.ofNullable(AnnotationUtils.findAnnotation(beanClass, AJob.class)).
                ifPresent(annotation -> functionMap.forEach((c, f) -> {
                    if (c.isAssignableFrom(beanClass)) {
                        String name = beanName + c.getSimpleName();
                        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(f.apply(beanClass, annotation)).
                                overwrite(true).build();
                        register(beanName, name, liteJobConfiguration, annotation);
                    }
                }));
        return bean;
    }


    /**
     * 包路径匹配
     *
     * @param clazz
     * @param packages
     * @return
     */
    private boolean packageMatch(Class clazz, String[] packages) {
        if (ArrayUtils.isEmpty(packages)) {
            return true;
        }
        for (String aPackage : packages) {
            if (clazz.getPackage().getName().startsWith(aPackage)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 注册bean
     *
     * @param beanName
     * @param name
     * @param liteJobConfiguration
     */
    private void register(String beanName,
                          String name,
                          LiteJobConfiguration liteJobConfiguration,
                          AJob aJob) {
        boolean enableJobEvent = Optional.ofNullable(enableElasticJobAttributes).map(e -> e.getBoolean("enableJobEventConfiguration")).orElse(false);
//        boolean enableJobEventConfiguration = enableElasticJobAttributes.getBoolean("enableJobEventConfiguration");
        String registerCenterRef = aJob.registerCenterRef();
        RegistryCenter registryCenter;
        //获取注册中心
        if (StringUtils.isEmpty(registerCenterRef)) {
            registryCenter = defaultRegistryCenter;
        } else {
            registryCenter = registryCenterMap.get(registerCenterRef);
            if (registryCenter == null) {
                throw new NoSuchBeanDefinitionException("bean name {} do not exist", registerCenterRef);
            }
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
        beanDefinitionBuilder.addConstructorArgReference(beanName)
                .addConstructorArgValue(registryCenter)
                .addConstructorArgValue(liteJobConfiguration);
        beanDefinitionBuilder.setInitMethodName("init");
        beanDefinitionBuilder.setLazyInit(false);
        //是否开启job事件记录
        if (enableJobEvent) {
            JobEventConfiguration jobEventConfiguration = beanFactory.getBean(JobEventConfiguration.class);
            beanDefinitionBuilder.addConstructorArgValue(jobEventConfiguration);
        }
        //添加任务监听器
        ElasticJobListener[] elasticJobListeners;
        if (!CollectionUtils.isEmpty(elasticJobListenerMap)) {
            String[] elasticJobListenerRefs = aJob.elasticJobListeners();
            if (ArrayUtils.isEmpty(elasticJobListenerRefs)) {
                elasticJobListeners = elasticJobListenerMap.values().toArray(new ElasticJobListener[0]);
            } else {
                List<ElasticJobListener> listeners = new ArrayList<>(elasticJobListenerRefs.length);
                for (String elasticJobListenerRef : elasticJobListenerRefs) {
                    Optional.ofNullable(elasticJobListenerMap.get(elasticJobListenerRef)).ifPresent(listeners::add);
                }
                elasticJobListeners = listeners.toArray(new ElasticJobListener[0]);
            }
        } else {
            elasticJobListeners = new ElasticJobListener[0];
        }
        beanDefinitionBuilder.addConstructorArgValue(elasticJobListeners);
        //注册job调度类
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        beanDefinitionRegistry.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());
    }


    /**
     * 创建SimpleJobConfiguration
     *
     * @param beanClass
     * @return
     */
    private SimpleJobConfiguration createSimpleJob(Class beanClass, AJob aJob) {
        return new SimpleJobConfiguration(createJobCoreConfiguration(aJob, beanClass, elasticJobConfig.getSimpleJob()), beanClass.getCanonicalName());
    }

    private DataflowJobConfiguration createDataFlowJob(Class beanClass, AJob aJob) {
        boolean streamingProcess = Optional.ofNullable(elasticJobConfig.getDataflowJob()).map(ElasticJobConfig.JobConfig::isStreamProcess).orElse(true);
        return new DataflowJobConfiguration(createJobCoreConfiguration(aJob, beanClass, elasticJobConfig.getDataflowJob()), beanClass.getCanonicalName(), streamingProcess);
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
        }
        return JobCoreConfiguration.newBuilder(
                jobClass.getName(),
                cron,
                shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
    }

    /**
     * 激活SpringJobScheduler
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //此为如此实现的妥协
        applicationContext.getBeansOfType(SpringJobScheduler.class);
    }
}
