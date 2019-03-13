package org.geeksword.spring.boot.elastic.test;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import org.geeksword.spring.boot.elastic.annotations.AJob;
import org.geeksword.spring.boot.elastic.annotations.JobPro;

/**
 * @Author: zhoulinshun
 * @Description:
 * @Date: Created in 2019-03-13 14:26
 */
@AJob(cron = "0/10 * * * * ?", elasticJobListeners = "heheh", jobProperties = {
        @JobPro(key = JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER, value = "")
})
public class TestJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("TestJob.execute");
    }
}
