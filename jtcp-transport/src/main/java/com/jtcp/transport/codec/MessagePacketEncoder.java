package com.jtcp.transport.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

/**
 * <pre>
 * 原始协议消息编码器
 * </pre>
 * 
 * @author DaFan
 * @version 创建时间：2019年4月17日 上午10:15:41 类说明
 */
public class MessagePacketEncoder extends MessageToByteEncoder<byte[]> {

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] bytes, ByteBuf out) throws Exception {
		out.writeBytes(bytes);
	}
}
