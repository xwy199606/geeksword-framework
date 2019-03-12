package org.geeksword.spring.boot.elastic.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface DataflowJob {

    @AliasFor(attribute = "value", annotation = Component.class)
    String value() default "";

    String registerCenterRef() default "";

    String cron();

    int shardingTotalCount();

    String shardingItemParameters();
}
