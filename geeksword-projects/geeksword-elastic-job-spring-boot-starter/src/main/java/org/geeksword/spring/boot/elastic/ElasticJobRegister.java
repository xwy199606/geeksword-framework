package org.geeksword.spring.boot.elastic;

import org.geeksword.spring.boot.elastic.annotations.EnableElasticJob;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class ElasticJobRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes enableElasticJobAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableElasticJob.class.getName()));
        boolean enableJobEvent = enableElasticJobAttributes.getBoolean("enableJobEvent");
        if (enableJobEvent) {
            Class<?> jobEventClass = enableElasticJobAttributes.getClass("jobEventClass");
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(jobEventClass);
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            registry.registerBeanDefinition(beanDefinition.getBeanClassName(),beanDefinition);
        }
    }


}
