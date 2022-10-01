package com.jtcp.core.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jtcp.core.annotation.JtcpRoute;


/**
 * 路由扫描静态类
 * 
 * @author 作者
 * @version 创建时间：2019年9月16日 下午3:56:50 类说明
 */
public class RouterScanner {

	private String rootPackageName;

	private static Map<Object, Method> routes = null;

	private List<Method> methods;

	private volatile static RouterScanner routerScanner;

	/**
	 * get single Instance
	 *
	 * @return
	 */
	public static RouterScanner getInstance() {
		if (routerScanner == null) {
			synchronized (RouterScanner.class) {
				if (routerScanner == null) {
					routerScanner = new RouterScanner();
				}
			}
		}
		return routerScanner;
	}

	private RouterScanner() {
	}

	public String getRootPackageName() {
		return rootPackageName;
	}

	public void setRootPackageName(String rootPackageName) {
		this.rootPackageName = rootPackageName;
	}

	/**
	 * 根据注解 指定方法 get route method
	 *
	 * @param queryStringDecoder
	 * @return
	 * @throws Exception
	 */
	public Method routeMethod(Object key) throws Exception {
		if (routes == null) {
			routes = new HashMap<>(16);
			loadRouteMethods(getRootPackageName());
		}

		Method method = routes.get(key);

		if (method == null) {
			throw new Exception();
		}

		return method;

	}

	/**
	 * 加载指定包下Method对象
	 * 
	 * @param packageName
	 * @throws Exception
	 */
	private void loadRouteMethods(String packageName) throws Exception {
		Set<Class<?>> classSet = ClassScanner.getClasses(packageName);

		for (Class<?> sproutClass : classSet) {
			Method[] declaredMethods = sproutClass.getMethods();

			for (Method method : declaredMethods) {
				JtcpRoute annotation = method.getAnnotation(JtcpRoute.class);
				if (annotation == null) {
					continue;
				}
				routes.put(annotation.value(), method);
			}
		}

	}

}
