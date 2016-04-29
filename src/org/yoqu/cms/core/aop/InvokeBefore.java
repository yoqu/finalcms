package org.yoqu.cms.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yoqu
 * @date 2016-04-26
 * @description 该注解用来判断方法是否要被回调.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvokeBefore {
    String value() ;
}