package com.jtcp.core.bean;

import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jianshaofan
 * @version 创建时间：2019年6月19日 下午4:37:01 类说明
 */
public class JtcpBeanFactory implements IJtcpBeanFactory {

	/**
	 * 对象map
	 */
	private static Map<Object, Object> beans = new HashMap<>(8);
	
	/**
	 * 对象map
	 */
	private static List<Method> methods = new ArrayList<>(2);

	@Override
	public void init(Object object) {
		beans.put(object.getClass().getName(), object);
	}

	@Override
	public Object getBean(String name) {
		return beans.get(name);
	}
	
	
	public List<Method> getMethods() {
		return methods;
	}

	@Override
	public void release() {
		beans = null;
	}
}
