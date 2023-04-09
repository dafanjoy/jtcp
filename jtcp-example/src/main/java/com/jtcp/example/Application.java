package com.jtcp.example;



import com.jtcp.core.bootstrap.JtcpBootstrap;
import com.jtcp.core.bootstrap.JtcpServerBootstrap;


public class Application {
	public static void main(String[] args) throws Exception {
		//JtcpBootstrap bootstrap = new JtcpBootstrap();
		JtcpServerBootstrap bootstrap = new JtcpServerBootstrap();
		bootstrap.config().setHost("127.0.0.1");
		bootstrap.config().setPort(8030);
		bootstrap.start();
	}
}
