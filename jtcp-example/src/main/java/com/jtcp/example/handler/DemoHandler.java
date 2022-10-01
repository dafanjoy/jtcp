package com.jtcp.example.handler;

import com.jtcp.common.constant.RouteEnum;
import com.jtcp.common.utils.BytesUtils;
import com.jtcp.core.annotation.JtcpComponet;
import com.jtcp.core.annotation.JtcpRoute;
import com.jtcp.core.context.JtcpContext;

@JtcpComponet
public class DemoHandler{
	
	@JtcpRoute(RouteEnum.OnRecevie)
    public void res(JtcpContext jtcpContext) {
		System.err.println(BytesUtils.toHexString(jtcpContext.getRecvBytes()));
		jtcpContext.context.writeAndFlush(jtcpContext.getRecvBytes());
    }
	
	@JtcpRoute(RouteEnum.OnConnect)
    public void onConnect(JtcpContext context ) {
    	System.err.println("连接成功");
    }
	
	
	@JtcpRoute(RouteEnum.OnDisconnect)
    public void onDisConnect(JtcpContext context ) {
    	System.err.println("连接断开");
    }
}
