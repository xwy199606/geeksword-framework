package org.geeksword.spring.boot.elastic.test;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.JobEventListener;
import com.dangdang.ddframe.job.event.JobEventListenerConfigurationException;

public class JobEventLoggerConfiguration extends JobEventLoggerIdentity implements JobEventConfiguration {

    @Override
    public JobEventListener createJobEventListener() throws JobEventListenerConfigurationException {
        return new JobEventLoggerListener();
    }
}
