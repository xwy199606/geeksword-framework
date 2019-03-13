package org.geeksword.spring.boot.elastic.annotations;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface AJob {

    @AliasFor(attribute = "value", annotation = Component.class)
    String value() default "";

    String registerCenterRef() default "";

    /**
     * 缺省即为所有的监听器都会生效
     *
     * @return
     */
    String[] elasticJobListeners() default {};

    String cron();

    int shardingTotalCount() default 1;

    String shardingItemParameters() default "";
}
