package org.geeksword.spring.boot.elastic.annotations;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.util.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface AJob {

    @AliasFor(attribute = "value", annotation = Component.class)
    String value() default "";

    /**
     * 缺省即为所有的监听器都会生效
     *
     * @return 监听器的beanName
     */
    String[] elasticJobListeners() default {};

    /**
     * 缺省时使用默认注册中心
     *
     * @return 指定注册中心的beanName
     */
    String registerCenterRef() default "";

    /**
     * 设置作业属性.
     *
     * @return 作业属性.
     * @see JobPro
     */

    JobPro[] jobProperties() default {};

    /**
     * 缺省为job类名
     *
     * @return 作业名字
     */
    String jobName() default "";

    /**
     * @return 作业分片总数
     */
    int shardingTotalCount() default 1;

    /**
     * @return 作业启动时间的cron表达式
     */
    String cron();

    /**
     * 设置分片序列号和个性化参数对照表.
     *
     * <p>
     * 分片序列号和参数用等号分隔, 多个键值对用逗号分隔. 类似map.
     * 分片序列号从0开始, 不可大于或等于作业分片总数.
     * 如:
     * 0=a,1=b,2=c
     * </p>
     *
     * @return 分片序列号和个性化参数对照表
     */
    String shardingItemParameters() default "";

    /**
     * 设置作业自定义参数.
     *
     * <p>
     * 可以配置多个相同的作业, 但是用不同的参数作为不同的调度实例.
     * </p>
     *
     * @return 作业自定义参数
     */
    String jobParameter() default "";


    /**
     * 设置是否开启失效转移.
     *
     * <p>
     * 只有对monitorExecution的情况下才可以开启失效转移.
     * </p>
     *
     * @return 是否开启失效转移
     */
    boolean failover() default false;

    /**
     * 设置是否开启misfire.
     * *
     *
     * @return 是否开启misfire
     */
    boolean misfire() default false;

    /**
     * 设置作业描述信息.
     *
     * @return 作业描述信息
     */
    String description() default "";


}
