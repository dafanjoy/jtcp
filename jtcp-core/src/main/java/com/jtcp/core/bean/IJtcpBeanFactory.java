package com.jtcp.core.bean;

/**
 * BeanFacotry对象工厂接口
 * 
 * @author 作者 jianshaofan
 * @version 创建时间：2019年6月19日 下午4:27:55 类说明
 */
public interface IJtcpBeanFactory {

	/**
	 * Register into bean Factory
	 * 
	 * @param object
	 */
	void init(Object object);

	/**
	 * Get bean from bean Factory
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	Object getBean(String name) throws Exception;

	/**
	 * release all beans
	 */
	void release();

}
