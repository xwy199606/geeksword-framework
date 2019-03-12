package org.geeksword.spring.boot.elastic.annotations;

import org.geeksword.spring.boot.elastic.AnnotationProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AnnotationProcessor.class)
public @interface EnableJobScanner {

    String[] value() default {};

    String[] basePackage() default {};

}
