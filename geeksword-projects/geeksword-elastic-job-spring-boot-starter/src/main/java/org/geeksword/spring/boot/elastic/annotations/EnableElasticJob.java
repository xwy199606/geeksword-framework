package org.geeksword.spring.boot.elastic.annotations;

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
     * 缺省的时候会添加所有spring容器内被@AJob注释的类
     *
     * @return
     */
    @AliasFor(annotation = EnableJobScanner.class, attribute = "basePackage")
    String[] basePackage() default {};

    boolean enableJobEventConfiguration() default false;

}
