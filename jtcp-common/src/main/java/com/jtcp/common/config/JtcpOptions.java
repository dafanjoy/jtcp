package com.jtcp.common.config;

/**
 * @author DaFan
 * @version 创建时间：2019年1月9日 下午4:31:18 默认配置信息
 */
public class JtcpOptions {
	
	/**
	 * 默认通信类型
	 */
	public static String REMOTING_TYPE = "default.remoting.type";
	/**
	 * 默认绑定I
	 */
	public static final String SERVER_HOST = "server.host";
	/**
	 * 默认启动端口，包括不配置或者随机，都从此端口开始计算
	 */
	public static final String SERVER_PORT_START = "server.port";
	/**
	 * 服务启动，默认执行协议
	 */
	public static final String DEFAULT_PROTOCOL = "default.protocol";
	/**
	 * 连接默认连接超时时间
	 */
	public static final String DEFAULT_CONNECT_TIMEOUT = "default.connect.timeout";
	/**
	 * 默认连接线程池大小
	 */
	public static final String DEFAULT_ACCEPT_THREADS = "default.accept.threads";
	/**
	 * 默认IO线程池大小
	 */
	public static final String DEFAULT_IO_THREADS = "default.io.threads";
	/**
	 * 默认业务线程池大小
	 */
	public static final String DEFAULT_WORK_THREADS = "default.work.threads";
	
	/**
	 * 默认通信类型
	 */
	public String REMOTING_TYPE_VALUE = "server";
	/**
	 * 默认绑定网卡
	 */
	public static final String SERVER_HOST_VALUE = "127.0.0.1";
	/**
	 * 默认启动端口，包括不配置或者随机，都从此端口开始计算
	 */
	public static final int SERVER_PORT_START_VALUE  = 5070;
	/**
	 * 默认连接线程池大小
	 */
	public static final int DEFAULT_ACCEPT_THREADS_VALUE  = 1;
	/**
	 * 默认IO线程池大小
	 */
	public static final int DEFAULT_IO_THREADS_VALUE  = 2;
	
	/**
	 * 默认业务
	 */
	public static final String DEFAULT_BYTE_BEGIN = "default.byte.begin";

	public static final byte DEFAULT_BYTE_BEGIN_VALUE = 0x7E;
	
	
	/**
	 * 默认业务线程池大小
	 */
	public static final String DEFAULT_BYTE_END = "default.byte.end";
	
	public static final byte DEFAULT_BYTE_END_VALUE = 0x7E;
	
	
	
	/**
	 * 默认通道空闲检查时间
	 */
	public static final String DEFAULT_IDLE_MINUTES = "default.idle.minutes";
	
	public static final int DEFAULT_IDLE_MINUTES_VALUE = 5;
}
