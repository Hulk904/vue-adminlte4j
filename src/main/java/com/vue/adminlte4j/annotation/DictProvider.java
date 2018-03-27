package com.vue.adminlte4j.annotation;

/**
 * Created by bjliuyong on 2018/3/27.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Clinton Begin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DictProvider {

    Class<? extends Enum> type() ;

    String method() default "getDict";

}
