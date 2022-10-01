package com.jtcp.core.context;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author DaFan
 * @version 创建时间：2019年12月30日 下午6:11:04 类说明
 */
public class JtcpContext {
	
	public ChannelHandlerContext context;	

	private byte[] recvBytes;

	public JtcpContext(ChannelHandlerContext ctx) {
		this.context = ctx;
	}

	public JtcpContext(ChannelHandlerContext ctx, byte[] recvBytes) {
		this.context = ctx;
		this.recvBytes = recvBytes;
	}

	public byte[] getRecvBytes() {
		return recvBytes;
	}

	public void setRecvBytes(byte[] recvBytes) {
		this.recvBytes = recvBytes;
	}

}
