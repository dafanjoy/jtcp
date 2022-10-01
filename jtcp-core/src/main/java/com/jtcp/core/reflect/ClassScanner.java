package com.jtcp.core.reflect;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jtcp.common.utils.ReflectUtils;
import com.jtcp.core.annotation.JtcpComponet;

/**
 * jar包扫描类
 * @version 创建时间：2019年6月19日 下午2:53:38 类说明
 */
public class ClassScanner {

	private static Set<Class<?>> classSet = null;
	
	private static Map<String, Class<?>> componetMap = null;

	/**
	 * 获取指定包名下所有class类
	 * @param packageName
	 * @return
	 * @throws Exception
	 */
    public static Set<Class<?>> getClasses(String packageName) throws Exception {

        if (classSet == null){
        	classSet = ReflectUtils.getClasses(packageName);

        }
        return classSet;
    }
    
	
	/**
	 * 缓存所有指定注解的class<?>类对象
	 * @param packageName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Class<?>> getBean(String packageName) throws Exception {

		if (componetMap == null) {
			Set<Class<?>> clsList = getClasses(packageName);

			if (clsList == null || clsList.isEmpty()) {
				return componetMap;
			}

			componetMap = new HashMap<>(16);
			for (Class<?> cls : clsList) {

				Annotation annotation = cls.getAnnotation(JtcpComponet.class);
				if (annotation == null) {
					continue;
				}

				JtcpComponet sproutComponet = (JtcpComponet) annotation;
				componetMap.put(sproutComponet.value() == null ? cls.getName() : sproutComponet.value(), cls);

			}
		}
		return componetMap;
	}

}
