package com.jtcp.transport.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * <pre>
 * 原始协议消息解码器
 * </pre>
 *
 * @author DaFan
 * @version 创建时间：2019年4月17日 上午10:15:41 类说明
 */
public class MessagePacketDecoder extends ByteToMessageDecoder {
	private int magicByteBegin = 0x7e;
	private int magicByteEnd = 0x7e;
	private int state = 0;
	private ByteArrayOutputStream dataStream;

	public MessagePacketDecoder(int magicByteBegin, int magicByteEnd) throws Exception {
		this.magicByteBegin = magicByteBegin;
		this.magicByteEnd = magicByteEnd;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out){
		try {
			if (buffer.readableBytes() > 0) {
				// 待处理的消息包
				byte[] bytesReady = new byte[buffer.readableBytes()];
				buffer.readBytes(bytesReady);
				parseCompletePackets(ctx, bytesReady, out, magicByteBegin, magicByteEnd);
			}
		}catch(Exception ex) {
			
		}

	}

	/**
	 * state = 0 开始解析
	 * state = 1 解析（递归处理粘包）
	 * state = 2 半包
	 */
	private void parseCompletePackets(ChannelHandlerContext ctx, byte[] bytesReady, List<Object> out,
			int magicByteBegin, int magicByteEnd) throws IOException {
		if (state == 0) { // 开始解析
			dataStream = new ByteArrayOutputStream();
			// 包数据开始状态，查找开始标识
			if (bytesReady[0] != magicByteBegin) {//第一包必须从协议报文头开始
				return;
			}
			state = 1;
		}
		if (state > 0) {
			int pos = indexOfMagicByte(bytesReady, magicByteEnd);//寻找尾部标识index，跳过头部标识位从1开始
            if(state == 2) {//半包状态
            	if(bytesReady[0] == magicByteEnd) {//半包状态，但下段报文7E开头，明显是不正常的
            		dataStream.reset(); //只能清除目前已累积的所有数据
            	}
        	}
			if (pos != -1) {
				// 结束标识
				dataStream.write(bytesReady, 0, pos);
				
				byte[] ad = dataStream.toByteArray();
				// 读取完整一个报文
				out.add(ad);
				// 重置为包开始处理状态
				state = 0;
				// 将剩余字节写入内存字节流中
				if (pos != bytesReady.length) {
					byte[] remainBytes = new byte[bytesReady.length - pos];
					System.arraycopy(bytesReady, pos, remainBytes, 0, remainBytes.length);
					parseCompletePackets(ctx, remainBytes, out, magicByteBegin, magicByteEnd);
				}
			} else {
				// 无结束标识，非完成报文，继续后续处理
                state = 2; //报文体读取状态，直接将当前数据写内存字节流中
				// 在下一次数据过来时处理结束标识
				dataStream.write(bytesReady, 0, bytesReady.length);
			}
		}
	}

	private int indexOfMagicByte(byte[] bytesReady, int magicByte) {
		// 跳过第一个标记位字节，寻找结束标记字节位置
		for (int i = 1; i < bytesReady.length; i++) {
			if (bytesReady[i] == magicByte)
				return i + 1;
		}
		return -1;
	}


}
