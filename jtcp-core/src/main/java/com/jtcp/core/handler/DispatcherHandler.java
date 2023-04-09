package com.jtcp.core.handler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jtcp.common.constant.RouteEnum;
import com.jtcp.core.context.JtcpContext;
import com.jtcp.core.route.RouteMethod;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author
 * @version 创建时间：2022年2月12日 下午6:05:13
 */
@ChannelHandler.Sharable
public class DispatcherHandler extends ChannelInboundHandlerAdapter {
	

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
	}

	/**
	 * channel连接事件
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		JtcpContext sproutContext = new JtcpContext(ctx);
		RouteMethod.getInstance().invoke(RouteEnum.OnConnect, new Object[] { sproutContext });
	}

	/**
	 * channel断开事件
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		JtcpContext sproutContext = new JtcpContext(ctx);
		RouteMethod.getInstance().invoke(RouteEnum.OnDisconnect, new Object[] { sproutContext });
	}

	/**
	 * 连接状态监测事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				JtcpContext sproutContext = new JtcpContext(ctx);
				RouteMethod.getInstance().invoke(RouteEnum.OnDisconnect, new Object[] { sproutContext });
				ctx.close();
			}

		}
	}

	/**
	 * 接收消息事件
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object source) {
		try {
			byte[] dataBytes = (byte[]) source;
			JtcpContext sproutContext = new JtcpContext(ctx, dataBytes);
			RouteMethod.getInstance().invoke(RouteEnum.OnRecevie, new Object[] { sproutContext });
		} catch (Exception ex) {
		}
	}

	/**
	 * channel异常事件
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
