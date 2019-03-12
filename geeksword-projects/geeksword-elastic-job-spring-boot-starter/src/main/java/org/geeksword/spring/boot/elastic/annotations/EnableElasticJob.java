package org.geeksword.spring.boot.elastic.annotations;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.geeksword.spring.boot.elastic.ElasticJobAutoConfig;
import org.geeksword.spring.boot.elastic.ElasticJobRegister;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableJobScanner
@Import(ElasticJobAutoConfig.class)
public @interface EnableElasticJob {

    @AliasFor(annotation = EnableJobScanner.class, attribute = "basePackage")
    String[] basePackage() default {};

    boolean enableJobEvent() default false;

    Class<? extends JobEventConfiguration> jobEventClass() default JobEventRdbConfiguration.class;

}
