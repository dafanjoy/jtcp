package com.jtcp.core.bean;

import java.util.Map;

import com.jtcp.core.reflect.ClassScanner;

/**
 * SproutApplicationContext bean容器类
 * 
 * @author jianshaofan
 * @version 创建时间：2019年4月4日 下午4:45:27
 */
public class JtcpApplicationContext {

	private JtcpApplicationContext() {
	}

	private static volatile JtcpApplicationContext sproutApplicationContext;

	private IJtcpBeanFactory sproutBeanFactory;

	
	public static JtcpApplicationContext getInstance() {
		if (sproutApplicationContext == null) {
			synchronized (JtcpApplicationContext.class) {
				if (sproutApplicationContext == null) {
					sproutApplicationContext = new JtcpApplicationContext();
				}
			}
		}
		return sproutApplicationContext;
	}

	/**
	  *   声明bena工厂实例，扫描指定jar包，加载指定jar包下的实例
	 * 
	 * @param packageName
	 * @throws Exception
	 */
	public void init(String packageName) throws Exception {
		//获取到指定注解类的Map
		Map<String, Class<?>> sproutBeanMap = ClassScanner.getBean(packageName);

		sproutBeanFactory = new JtcpBeanFactory();
         
		//注入实例工厂
		for (Map.Entry<String, Class<?>> classEntry : sproutBeanMap.entrySet()) {
			Object instance = classEntry.getValue().newInstance();
			sproutBeanFactory.init(instance);
		}

	}

	/**
	 * 根据名称获取获取对应实例
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Object getBean(String name) throws Exception {
		return sproutBeanFactory.getBean(name);
	}

	/**
	 * release all beans
	 */
	public void releaseBean() {
		sproutBeanFactory.release();
	}

}
