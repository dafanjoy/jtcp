package com.jtcp.core.route;

import java.lang.reflect.Method;

import com.jtcp.common.constant.RouteEnum;
import com.jtcp.core.bean.JtcpApplicationContext;
import com.jtcp.core.reflect.RouterScanner;

/**
 * Route Method
 * @version 创建时间：2019年9月16日 下午5:02:32 类说明
 */
public class RouteMethod {

	private volatile static RouteMethod routeMethod;

	private final JtcpApplicationContext applicationContext = JtcpApplicationContext.getInstance();

	public static RouteMethod getInstance() {
		if (routeMethod == null) {
			synchronized (RouteMethod.class) {
				if (routeMethod == null) {
					routeMethod = new RouteMethod();
				}
			}
		}
		return routeMethod;
	}

	/**
	 * 调用方法
	 * @param method
	 * @param annotation
	 * @param args
	 * @throws Exception
	 */
	public void invoke(Method method, Object[] args) throws Exception {
		if (method == null) {
			return;
		}
		Object bean = applicationContext.getBean(method.getDeclaringClass().getName());
		if (args == null) {
			method.invoke(bean);
		} else {
			method.invoke(bean, args);
		}
	}
	
	
	/**
	 * 根据注解调用方法
	 * @param method
	 * @param annotation
	 * @param args
	 * @throws Exception
	 */
	public void invoke(RouteEnum routeEnum, Object[] args) throws Exception {
		Method method = RouterScanner.getInstance().routeMethod(routeEnum);
		if (method == null) {
			return;
		}
		Object bean = applicationContext.getBean(method.getDeclaringClass().getName());
		if (args == null) {
			method.invoke(bean);
		} else {
			method.invoke(bean, args);
		}
	}

}
