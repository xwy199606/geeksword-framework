package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

public class ElasticJobAutoConfig {

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Autowired
    private ElasticJobConfig elasticJobConfig;


//    public ElasticJobAutoConfig(ObjectProvider<JobEventConfiguration> jobEventConfigurations) {
//
//
//    }

//    @Bean
//    public JobEventConfiguration jobEventConfiguration() {
//
//    }


    @Bean
    public ZookeeperConfiguration zookeeperConfiguration(ElasticJobConfig elasticJobConfig) {
        if (elasticJobConfig.getServerLists() == null) {
            throw new IllegalArgumentException("serverLists cannot be null");
        }
        if (elasticJobConfig.getNamespace() == null) {
            throw new IllegalArgumentException("namespace cannot be null");
        }
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(elasticJobConfig.getServerLists(), elasticJobConfig.getNamespace());
        zookeeperConfiguration.setDigest(elasticJobConfig.getDigest());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(elasticJobConfig.getBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(elasticJobConfig.getConnectionTimeoutMilliseconds());
        zookeeperConfiguration.setMaxRetries(elasticJobConfig.getMaxRetries());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(elasticJobConfig.getSessionTimeoutMilliseconds());
        return zookeeperConfiguration;
    }

    @Bean
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zookeeperConfiguration) {
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob, @Value("${simpleJob.cron}") final String cron, @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, null, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration,new ElasticJobListener[]{});
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }


}
