package com.jtcp.core.bootstrap;

import com.jtcp.common.config.JtcpConfig;
import com.jtcp.common.constant.JtcpLogoConstant.SystemProperties;
import com.jtcp.common.utils.StringUtils;
import com.jtcp.core.bean.JtcpApplicationContext;
import com.jtcp.core.handler.DispatcherHandler;
import com.jtcp.core.reflect.RouterScanner;
import com.jtcp.transport.server.JtcpServer;

/**
 * @author  dafan
 * @version 创建时间：2019年1月25日 下午6:06:22
 */
public final class JtcpBootstrap {
	private Class<?> mainClass;

	/**
	 * 构造函数
	 *
	 * @param providerConfig
	 *            服务发布者配置
	 */
	public JtcpBootstrap() {
	}

	public JtcpBootstrap(JtcpConfig config) {
	}

	public JtcpConfig config() {
		return JtcpConfig.getInstance();
	}

	/**
	 * 发布服务
	 */
	public void start() {
		try {
			// 加载logo
			logo();
			resolveMainClass();
		    if(mainClass!=null) {
		    	JtcpApplicationContext.getInstance().init(mainClass.getPackage().getName());
		    }
		    new JtcpServer(new DispatcherHandler()).start();//启动服务
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void SetRootPackageName(String parth) {
		RouterScanner.getInstance().setRootPackageName(parth);
		try {
			RouterScanner.getInstance().routeMethod("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void logo() {
		System.out.println(SystemProperties.LOGO);
	}

	/**
	 * 查询main方法的class类
	 * 
	 * @return
	 */
	private Class<?> resolveMainClass() {
		try {
	    	if(!StringUtils.isEmpty(config().getRootPackageName())) {
	    		mainClass = Class.forName(config().getRootPackageName());
	    	}else {
				StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
				for (StackTraceElement stackTraceElement : stackTrace) {
					if ("main".equals(stackTraceElement.getMethodName())) {
						mainClass = Class.forName(stackTraceElement.getClassName());
						break;
					}
				}
	    	}

		} catch (Exception ex) {
			// ignore this ex
		}
		return mainClass;
	}

}
