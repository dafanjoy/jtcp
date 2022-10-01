package com.jtcp.common.config;

/**
 * 
 * @author dafan
 *
 */
public class JtcpConfig extends AbstractConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * simple singleton Object
	 */
	private static JtcpConfig config;

	public static JtcpConfig getInstance() {
		if (config == null) {
			config = new JtcpConfig();
		}
		return config;
	}

	/*------------- 参数配置项开始-----------------*/
	/**
	 * 实际监听IP，与网卡对应
	 */
	private String host = getStringValue(JtcpOptions.SERVER_HOST, JtcpOptions.SERVER_HOST_VALUE);
	/**
	 * 监听端口
	 */
	private int port = getIntValue(JtcpOptions.SERVER_PORT_START, JtcpOptions.SERVER_PORT_START_VALUE);

	/**
	 * accept连接线程池大小
	 */
	private int acceptThreads = getIntValue(JtcpOptions.DEFAULT_ACCEPT_THREADS,
			JtcpOptions.DEFAULT_ACCEPT_THREADS_VALUE);

	/**
	 * io线程池大小
	 */
	private int ioThreads = getIntValue(JtcpOptions.DEFAULT_IO_THREADS, JtcpOptions.DEFAULT_IO_THREADS_VALUE);

	private byte magicByteBegin = getByteValue(JtcpOptions.DEFAULT_BYTE_BEGIN,
			JtcpOptions.DEFAULT_BYTE_BEGIN_VALUE);

	private byte magicByteEnd = getByteValue(JtcpOptions.DEFAULT_BYTE_END, JtcpOptions.DEFAULT_BYTE_END_VALUE);

	private int idleMinutes = getIntValue(JtcpOptions.DEFAULT_IDLE_MINUTES, JtcpOptions.DEFAULT_IDLE_MINUTES_VALUE);
	
	private String rootPackageName;

	public String getHost() {
		return host;
	}

	public JtcpConfig setHost(String host) {
		this.host = host;
		return this;
	}

	public int getPort() {
		return port;
	}

	public JtcpConfig setPort(int port) {
		this.port = port;
		return this;
	}

	public int getAcceptThreads() {
		return acceptThreads;
	}

	public JtcpConfig setAcceptThreads(int acceptThreads) {
		this.acceptThreads = acceptThreads;
		return this;
	}

	public int getIoThreads() {
		return ioThreads;
	}

	public JtcpConfig setIoThreads(int ioThreads) {
		this.ioThreads = ioThreads;
		return this;
	}

	public byte getMagicByteBegin() {
		return magicByteBegin;
	}

	public JtcpConfig setMagicByteBegin(byte magicByteBegin) {
		this.magicByteBegin = magicByteBegin;
		return this;
	}

	public byte getMagicByteEnd() {
		return magicByteEnd;
	}

	public JtcpConfig setMagicByteEnd(byte magicByteEnd) {
		this.magicByteEnd = magicByteEnd;
		return this;
	}

	public int getIdleMinutes() {
		return idleMinutes;
	}

	public JtcpConfig setIdleMinutes(int idleMinutes) {
		this.idleMinutes = idleMinutes;
		return this;
	}
	
	public String getRootPackageName() {
		return rootPackageName;
	}

	public void setRootPackageName(String rootPackageName) {
		this.rootPackageName = rootPackageName;
	}
}
