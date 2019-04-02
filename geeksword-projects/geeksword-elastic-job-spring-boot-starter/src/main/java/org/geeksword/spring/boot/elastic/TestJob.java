package org.geeksword.spring.boot.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.geeksword.spring.boot.elastic.annotations.ASimpleJob;

/**
 * @Author: zhoulinshun
 * @Description:
 * @Date: Created in 2019-03-13 14:26
 */
@ASimpleJob(cron = "0/10 * * * * ?", shardingTotalCount = 1)
public class TestJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("TestJob.execute");
    }
}
