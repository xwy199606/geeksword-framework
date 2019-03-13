package org.geeksword.spring.boot.elastic.annotations;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.geeksword.spring.boot.elastic.ElasticJobAutoConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableJobScanner
@Import({ElasticJobAutoConfig.class})
public @interface EnableElasticJob {

    /**
     * 包扫描路径，不支持 * 号
     * @return
     */
    @AliasFor(annotation = EnableJobScanner.class, attribute = "basePackage")
    String[] basePackage() default {};

    boolean enableJobEvent() default false;

//    Class<? extends JobEventConfiguration> jobEventClass() default JobEventRdbConfiguration.class;

}
