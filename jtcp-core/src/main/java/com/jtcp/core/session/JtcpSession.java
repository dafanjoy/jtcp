package com.jtcp.core.session;

import java.net.SocketAddress;

import io.netty.channel.Channel;

public class JtcpSession {
	private String id;
	private String sessionNo;
	private Channel channel = null;
	// 客户端上次的连接时间
	private long lastCommunicateTimeStamp = 0l;

	public JtcpSession() {
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getSessionNo() {
		return sessionNo;
	}

	public void setSessionNo(String terminalNo) {
		this.sessionNo = terminalNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static String buildId(Channel channel) {
		return channel.id().asLongText();
	}

	public static JtcpSession buildSession(Channel channel) {
		return buildSession(channel, null);
	}

	public static JtcpSession buildSession(Channel channel, String sessionId) {
		JtcpSession session = new JtcpSession();
		session.setChannel(channel);
		session.setId(buildId(channel));
		session.setSessionNo(sessionId);
		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		return session;
	}

	public long getLastCommunicateTimeStamp() {
		return lastCommunicateTimeStamp;
	}

	public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
		this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
	}

	public SocketAddress getRemoteAddr() {
		System.out.println(this.channel.remoteAddress().getClass());

		return this.channel.remoteAddress();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JtcpSession other = (JtcpSession) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", sessionNo=" + sessionNo + ", channel=" + channel + "]";
	}

}
