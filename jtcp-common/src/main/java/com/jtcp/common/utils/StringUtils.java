package com.jtcp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 获取字符串中的数字
	 * 
	 * @param str
	 * @return
	 */
	public static int getNumFormString(String str) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return Integer.parseInt(m.replaceAll("").trim());
	}

	/**
	 * 根据时间（毫秒级别）+随机数，产生命令唯一标识
	 * 
	 * @throws InterruptedException
	 */
	public static boolean isEmpty(String val) {
		if (val == null || val.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
