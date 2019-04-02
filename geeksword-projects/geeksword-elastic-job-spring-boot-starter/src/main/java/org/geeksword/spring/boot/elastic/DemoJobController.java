package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

@Component
public class DemoJobController implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {

    }
}
