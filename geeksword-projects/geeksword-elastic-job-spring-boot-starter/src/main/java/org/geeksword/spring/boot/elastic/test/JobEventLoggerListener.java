package org.geeksword.spring.boot.elastic.test;

import com.dangdang.ddframe.job.event.JobEventListener;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbIdentity;
import com.dangdang.ddframe.job.event.type.JobExecutionEvent;
import com.dangdang.ddframe.job.event.type.JobStatusTraceEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobEventLoggerListener extends JobEventRdbIdentity implements JobEventListener {
    private static final Logger log = LoggerFactory.getLogger(JobEventLoggerListener.class);


    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void listen(JobExecutionEvent jobExecutionEvent) {
        try {
            log.info("jobExecutorEvent:{}", objectMapper.writeValueAsString(jobExecutionEvent));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listen(JobStatusTraceEvent jobStatusTraceEvent) {
        try {
            log.info("jobStatusTraceEvent:{}", objectMapper.writeValueAsString(jobStatusTraceEvent));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
