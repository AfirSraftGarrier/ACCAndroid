package com.acc.android.frame.util;

public class StringUtil {
	public static boolean isEmpty(String string) {
		return string == null || "".equals(string.trim());
	}

	// public static String getNotNullString(String string, String
	// defaultString) {
	// return string == null ? defaultString : string;
	// }

	public static String getNotNullString(Object stringObject,
			String defaultString) {
		return stringObject == null ? defaultString : stringObject.toString();
	}

	// public static String replaceNullString(String string) {
	//
	// return string.replace("null", "");
	// }
}
