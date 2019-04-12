package org.geeksword.spring.boot.elastic.test;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.springframework.stereotype.Component;

@Component
public class ElasticListenerImpl implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("ElasticListenerImpl.beforeJobExecuted");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("ElasticListenerImpl.afterJobExecuted");
    }
}
