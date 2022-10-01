package com.jtcp.common.config;

import java.io.Serializable;

import com.jtcp.common.exception.JtcpException;



public abstract class AbstractConfig implements Serializable {

	private static final long serialVersionUID = 1932911135229369183L;

	/**
	 * Gets string value.
	 *
	 * @param primaryKey
	 *            the primary key
	 * @return the string value
	 */
	public static String getStringValue(String key, String value) {
		if (value == null || value == "") {
			throw new JtcpException("Not found value:" + key);
		} else {
			return value;
		}
	}

	/**
	 * Gets int value.
	 *
	 * @param primaryKey
	 *            the primary key
	 * @return the int value
	 */
	public static int getIntValue(String key, int value) {
		if (value == 0) {
			throw new JtcpException("Not found value:" + key);
		} else {
			return value;
		}
	}

	/**
	 * Gets byte value.
	 *
	 * @param primaryKey
	 *            the primary key
	 * @return the int value
	 */
	public static byte getByteValue(String key, Byte value) {
		if (value == null) {
			throw new JtcpException("Not found value:" + key);
		} else {
			return value;
		}
	}
}
