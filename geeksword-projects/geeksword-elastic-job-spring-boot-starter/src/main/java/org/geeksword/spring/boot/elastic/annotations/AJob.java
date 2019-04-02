package org.geeksword.spring.boot.elastic.annotations;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AJob {

    String registerCenterRef() default "";

    String cron() default "";

    int shardingTotalCount() default -1;

    String shardingItemParameters() default "";
}
