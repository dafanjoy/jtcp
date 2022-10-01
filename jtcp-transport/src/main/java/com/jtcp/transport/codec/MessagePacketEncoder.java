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

	private int magicByteBegin = 0x7e;
	private int magicByteEnd = 0x7e;

	public MessagePacketEncoder(int magicByteBegin, int magicByteEnd) {
		this.magicByteBegin = magicByteBegin;
		this.magicByteEnd = magicByteEnd;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] bytes, ByteBuf out) throws Exception {
		out.writeBytes(bytes);
		
//		ChannelFuture future =  ctx.write(out);
//		future.addListener(new ChannelFutureListener() {
//	         public void operationComplete(ChannelFuture future) {
//	        	 if(!future.isSuccess()) {
//	        		System.err.print("发送失败");
//	        	 }
//	         }
//	     });
	}
}
