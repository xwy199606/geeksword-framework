package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.reg.base.RegistryCenter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.geeksword.spring.boot.elastic.annotations.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class AnnotationProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationContextAware, ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware, BeanClassLoaderAware {


    @Setter
    private ApplicationContext applicationContext;
    @Setter
    private Environment environment;
    @Setter
    private ResourceLoader resourceLoader;
    @Setter
    private ClassLoader beanClassLoader;
    @Setter
    private BeanFactory beanFactory;


    @Autowired
    private RegistryCenter registryCenter;


    private AnnotationAttributes enableElasticJobAttributes;
    private AnnotationAttributes enableJobScannerAttributes;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        enableElasticJobAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableElasticJob.class.getName()));
        enableJobScannerAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableJobScanner.class.getName()));
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(JobProcessor.class);
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0, enableElasticJobAttributes);
        constructorArgumentValues.addIndexedArgumentValue(1, enableJobScannerAttributes);
        rootBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        beanDefinitionRegistry.registerBeanDefinition(JobProcessor.class.getSimpleName(), rootBeanDefinition);
//        boolean enableJobEvent = enableElasticJobAttributes.getBoolean("enableJobEvent");
//        String[] basePackages = enableJobScannerAttributes.getStringArray("basePackage");
//
//        ClassPathScanningCandidateComponentProvider classScanner = getClassScanner();
//        classScanner.setResourceLoader(this.resourceLoader);
//        AnnotationTypeFilter simpleJobSAnnotationTypeFilter = new AnnotationTypeFilter(ASimpleJob.class);
//        AnnotationTypeFilter dataFlowAnnotationTypeFilter = new AnnotationTypeFilter(ADataflowJob.class);
//        classScanner.addIncludeFilter(simpleJobSAnnotationTypeFilter);
//        classScanner.addIncludeFilter(dataFlowAnnotationTypeFilter);
//
//        if (ArrayUtils.isEmpty(basePackages)) {
//            basePackages = new String[]{"org.geeksword"};
//        }
//
//        for (String basePackage : basePackages) {
//            Set<BeanDefinition> candidateComponents = classScanner.findCandidateComponents(basePackage);
//            for (BeanDefinition candidateComponent : candidateComponents) {
//                try {
//                    AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) candidateComponent;
//                    Class<?> beanClass = abstractBeanDefinition.resolveBeanClass(classLoader);
//                    Component mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanClass, Component.class);
//                    String beanName;
//                    if (StringUtils.isNotEmpty(mergedAnnotation.value())) {
//                        beanName = mergedAnnotation.value();
//                    } else {
//                        beanName = "job" + beanClass.getSimpleName();
//                    }
//
//                    ASimpleJob annotation = AnnotationUtils.findAnnotation(beanClass, ASimpleJob.class);
//                    if (annotation != null) {
//                        if (SimpleJob.class.isAssignableFrom(beanClass)) {
//                            String name = beanName + "SimpleJob";
//                            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(createSimpleJob(beanClass)).
//                                    overwrite(true).build();
//                            register(beanDefinitionRegistry, enableJobEvent, beanName, name, liteJobConfiguration, beanClass);
//                            continue;
//                        }
//                        log.warn("{} is not SimpleJob Instance", beanName);
//                    }
//
//                    ADataflowJob aDataflowJob = AnnotationUtils.findAnnotation(beanClass, ADataflowJob.class);
//                    if (aDataflowJob != null) {
//                        if (SimpleJob.class.isAssignableFrom(beanClass)) {
//                            String name = beanName + "DataFlowJob";
//                            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(createDataFlowJob(beanClass)).
//                                    overwrite(true).build();
//                            register(beanDefinitionRegistry, enableJobEvent, , beanName, name, liteJobConfiguration, beanClass);
//                            continue;
//                        }
//                        log.warn("{} is not DataflowJob Instance", beanName);
//                    }
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        this.beanFactory = beanFactory;
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }


    /**
     * 构造Class扫描器，设置了只扫描顶级接口，不扫描内部类
     *
     * @return
     */
    private ClassPathScanningCandidateComponentProvider getClassScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment);
    }

}
