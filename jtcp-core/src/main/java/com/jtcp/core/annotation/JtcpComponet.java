package com.jtcp.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类注解
 * @author Dafan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JtcpComponet {
	String value() default "";
}
