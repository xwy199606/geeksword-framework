package org.geeksword.spring.boot.elastic.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@AJob
public @interface ASimpleJob {
    @AliasFor(attribute = "value", annotation = Component.class)
    String value() default "";

    @AliasFor(annotation = AJob.class, attribute = "registerCenterRef")
    String registerCenterRef() default "";

    @AliasFor(annotation = AJob.class, attribute = "cron")
    String cron();

    @AliasFor(annotation = AJob.class, attribute = "shardingTotalCount")
    int shardingTotalCount();

    @AliasFor(annotation = AJob.class, attribute = "shardingItemParameters")
    String shardingItemParameters() default "";
}
