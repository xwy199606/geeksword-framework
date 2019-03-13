package org.geeksword.spring.boot.elastic.annotations;

import com.dangdang.ddframe.job.executor.handler.JobProperties;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JobPro {
    JobProperties.JobPropertiesEnum key();

    String value();
}
