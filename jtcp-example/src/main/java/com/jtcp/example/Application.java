package com.jtcp.example;



import com.jtcp.core.bootstrap.JtcpBootstrap;


public class Application {
	public static void main(String[] args) throws Exception {
		JtcpBootstrap bootstrap = new JtcpBootstrap();
		bootstrap.config().setHost("127.0.0.1");
		bootstrap.config().setPort(8030);
		bootstrap.start();
		
	
	}
}
