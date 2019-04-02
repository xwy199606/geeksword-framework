package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

//@Configuration
public class ElasticJobAutoConfig implements EnvironmentAware {


    @Autowired
    private ElasticJobConfig elasticJobConfig;
    private Environment environment;

    @Bean
    public ZookeeperConfiguration zookeeperConfiguration(ElasticJobConfig elasticJobConfig) {
        String severLists = environment.getProperty("job.serverLists");
        String namespace = environment.getProperty("job.namespace");

//        if (elasticJobConfig.getServerLists() == null) {
//            throw new IllegalArgumentException("serverLists cannot be null");
//        }
//        if (elasticJobConfig.getNamespace() == null) {
//            throw new IllegalArgumentException("namespace cannot be null");
//        }
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(severLists, namespace);
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

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

//    @Bean(initMethod = "init")
//    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob, @Value("${simpleJob.cron}") final String cron, @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
//                                           @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {
//        return new SpringJobScheduler(simpleJob, null, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
//    }
//
//    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
//        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
//                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
//    }
//

}
