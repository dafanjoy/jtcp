package com.jtcp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jtcp.common.constant.RouteEnum;

/**
 * 方法注解
* @author jianshaofan
* @version 创建时间：2019年9月16日 下午3:47:46
* 
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) //一般如果需要在运行时去动态获取注解信息，只能用 RUNTIME 注解
@Documented
public @interface JtcpRoute {
	RouteEnum value();
}
