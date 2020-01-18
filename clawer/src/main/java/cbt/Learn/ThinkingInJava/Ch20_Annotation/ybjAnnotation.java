package cbt.Learn.ThinkingInJava.Ch20_Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author DefineAnnotation
 * 自定义的注解
 * @Date $ $
 * @Param $
 * @return $
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ybjAnnotation {
}
