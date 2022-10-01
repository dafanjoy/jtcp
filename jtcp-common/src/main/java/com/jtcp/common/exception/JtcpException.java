package com.jtcp.common.exception;
/** 
* @author DaFan
* @version 创建时间：2019年1月10日 下午3:17:22 
*/
public class JtcpException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	protected JtcpException() {

    }

    public JtcpException(String message) {
        super(message);
    }

    public JtcpException(String message, Throwable cause) {
        super(message, cause);
    }

    public JtcpException(Throwable cause) {
        super(cause);
    }

}
