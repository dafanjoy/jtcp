package com.jtcp.example.handler;

import com.jtcp.common.constant.RouteEnum;
import com.jtcp.common.utils.BytesUtils;
import com.jtcp.core.annotation.JtcpComponet;
import com.jtcp.core.annotation.JtcpRoute;
import com.jtcp.core.context.JtcpContext;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

@JtcpComponet
public class DemoHandler{
	
	@JtcpRoute(RouteEnum.OnRecevie)
    public void res(JtcpContext jtcpContext) {
		System.err.println(BytesUtils.toHexString(jtcpContext.getRecvBytes()));
		
		ChannelFuture future = jtcpContext.context.writeAndFlush(jtcpContext.getRecvBytes());
		future.addListener(new ChannelFutureListener() {
	         public void operationComplete(ChannelFuture future) {
	        	 if(!future.isSuccess()) {
	        		System.err.print("发送失败");
	        	 }else {
					System.err.println("发送成功");
				}
	         }
	     });
    }
	
	@JtcpRoute(RouteEnum.OnConnect)
    public void onConnect(JtcpContext context ) {
    	System.out.println("连接成功");
    }
	
	
	@JtcpRoute(RouteEnum.OnDisconnect)
    public void onDisConnect(JtcpContext context ) {
    	System.err.println("连接断开");
    }
}
