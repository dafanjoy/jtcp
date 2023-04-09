package com.jtcp.transport.client;

import java.util.concurrent.TimeUnit;

import com.jtcp.common.config.JtcpConfig;
import com.jtcp.transport.codec.MessagePacketDecoder;
import com.jtcp.transport.codec.MessagePacketEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class JtcpClient {
	
	private static EventLoopGroup workerGroup;
	
	private Bootstrap bootstrap;

	private ChannelInboundHandlerAdapter dispatcherHandler;
	
	private JtcpConfig config;

	NioEventLoopGroup nioEventLoopGroup;
	
	public JtcpClient (ChannelInboundHandlerAdapter dispatcherHandler) {
		this.dispatcherHandler = dispatcherHandler;
		config = JtcpConfig.getInstance();
	}
	
	public void init() {
        bootstrap = new Bootstrap();
        //创建非阻塞的线程组
        nioEventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(nioEventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("idleStateHandler",
                        new IdleStateHandler(0, config.getIdleMinutes() + 1, 0, TimeUnit.MINUTES))
                .addLast(new MessagePacketDecoder(config.getMagicByteBegin(),
						config.getMagicByteBegin()))
				.addLast(new MessagePacketEncoder())
                .addLast("clientManageHandler", dispatcherHandler);
            }
        });
	}
               
	 
	
	private void doConnect(){
		try {
			init();
			bootstrap.remoteAddress(config.getHost(), config.getPort());
	        ChannelFuture f = bootstrap.connect();
	        f.channel().closeFuture().sync();
		}catch(Exception ex) {
			
		}finally {
			nioEventLoopGroup.shutdownGracefully();
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            doConnect(); // 断线重连
			
		}
	
	}
	
	
	public void connect(){
	
		doConnect();
	}

}
