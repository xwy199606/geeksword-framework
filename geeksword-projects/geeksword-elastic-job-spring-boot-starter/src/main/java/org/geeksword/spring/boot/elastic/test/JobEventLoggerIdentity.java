package org.geeksword.spring.boot.elastic.test;

import com.dangdang.ddframe.job.event.JobEventIdentity;

public class JobEventLoggerIdentity implements JobEventIdentity {
    @Override
    public String getIdentity() {
        return "logger";
    }
}
