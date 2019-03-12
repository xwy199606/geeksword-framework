package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.RegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.geeksword.spring.boot.elastic.annotations.AJob;
import org.geeksword.spring.boot.elastic.annotations.ASimpleJob;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;


public class AnnotationProcessor implements BeanPostProcessor, BeanFactoryAware, ImportBeanDefinitionRegistrar {

    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    @Autowired(required = false)
    private ElasticJobListener[] elasticJobListeners;

    @Autowired
    private ElasticJobConfig elasticJobConfig;

    @Autowired
    private RegistryCenter registryCenter;

    @Autowired(required = false)
    private JobEventConfiguration jobEventConfiguration;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        ASimpleJob annotation = AnnotationUtils.findAnnotation(aClass, ASimpleJob.class);
        if (annotation != null) {
            if (bean instanceof SimpleJob) {
                LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(createSimpleJon(aClass)).
                        overwrite(true).build();
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
                beanDefinitionBuilder.addConstructorArgValue(bean)
                        .addConstructorArgValue(registryCenter)
                        .addConstructorArgValue(liteJobConfiguration);
                beanDefinitionBuilder.setInitMethodName("init");
                if (true) {
                    beanDefinitionBuilder.addConstructorArgValue(jobEventConfiguration);
                }
                beanDefinitionBuilder.addConstructorArgValue(elasticJobListeners);
            }
        }

//        BeanDefinitionBuilder.rootBeanDefinition()

        return null;
    }


    private JobTypeConfiguration createSimpleJon(Class beanClass) {
        AJob mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanClass, AJob.class);
        return new SimpleJobConfiguration(createJobCoreConfiguration(mergedAnnotation, beanClass, elasticJobConfig.getSimpleJob()), beanClass.getCanonicalName());
    }

    private JobCoreConfiguration createJobCoreConfiguration(AJob aJob, Class jobClass, ElasticJobConfig.JobConfig jobConfig) {
        String cron = aJob.cron();
        int shardingTotalCount = aJob.shardingTotalCount();
        String shardingItemParameters = aJob.shardingItemParameters();
        if (StringUtils.isEmpty(cron)) {
            cron = jobConfig.getCron();
            if (StringUtils.isEmpty(cron)) {
                throw new IllegalArgumentException("corn cannot null or empty");
            }
        }
        if (shardingTotalCount == -1) {
            shardingTotalCount = jobConfig.getShardingTotalCount();
            if (shardingTotalCount == -1) {
                throw new IllegalArgumentException("shardingTotalCount cannot null or empty");
            }
        }
        if (StringUtils.isEmpty(shardingItemParameters)) {
            shardingItemParameters = jobConfig.getShardingItemParameters();
            if (StringUtils.isEmpty(shardingItemParameters)) {
                throw new IllegalArgumentException("shardingItemParameters cannot null or empty");
            }
        }
        return JobCoreConfiguration.newBuilder(
                jobClass.getName(),
                cron,
                shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    }

    public void setRegistryCenter(RegistryCenter registryCenter) {
        this.registryCenter = registryCenter;
    }
}
