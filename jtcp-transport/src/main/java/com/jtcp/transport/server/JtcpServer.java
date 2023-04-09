package com.jtcp.transport.server;

import java.util.concurrent.TimeUnit;

import com.jtcp.common.config.JtcpConfig;
import com.jtcp.transport.codec.MessagePacketDecoder;
import com.jtcp.transport.codec.MessagePacketEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author DaFan
 * @version 创建时间：2019年1月25日 下午6:17:45
 */
public class JtcpServer {

	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workerGroup;
	
	private ServerBootstrap serverBootstrap;

	private ChannelInboundHandlerAdapter dispatcherHandler;
	
	private JtcpConfig config;

	public JtcpServer(ChannelInboundHandlerAdapter dispatcherHandler) {
		this.dispatcherHandler = dispatcherHandler;
		config = JtcpConfig.getInstance();
	}

	public void init() {
		try {
			
//			bossGroup = new EpollEventLoopGroup(SproutConfig.getInstance().getAcceptThreads());
//			workerGroup = new EpollEventLoopGroup(SproutConfig.getInstance().getIoThreads());
			
			bossGroup = new NioEventLoopGroup(config.getAcceptThreads());
			workerGroup = new NioEventLoopGroup(config.getIoThreads());

			serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);

			// Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			/*
			 * Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。
			 * 可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能。
			 */
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(config.getIdleMinutes(), 0, 0, TimeUnit.MINUTES))
							.addLast(new MessagePacketDecoder(config.getMagicByteBegin(),
									config.getMagicByteBegin()))
							.addLast(new MessagePacketEncoder())
							.addLast("handler", dispatcherHandler);
				}
			});

		} catch (Exception ex) {

		}

	}

	/**
	 * startServer
	 * 
	 * @throws RemotingRuntimeException
	 */
	public void start() {
		try {
			init();
			startServer();
		} catch (Exception ex) {

		}
	}

	/**
	 * startServer
	 * 
	 * @throws InterruptedException
	 */
	private void startServer() throws InterruptedException {

		ChannelFuture channelFuture = serverBootstrap.bind(config.getHost(),config.getPort()).sync();

		if (channelFuture.isSuccess()) {
			System.out.println("server start 成功---------------");
		}
	}

}
