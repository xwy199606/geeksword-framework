package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.geeksword.spring.boot.elastic.test.JobEventLoggerConfiguration;
import org.springframework.context.annotation.Bean;

public class ElasticJobAutoConfig {

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

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zookeeperConfiguration) {
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean
    public JobEventConfiguration jobEventRdbConfiguration(){
        return new JobEventLoggerConfiguration();
    }

}
