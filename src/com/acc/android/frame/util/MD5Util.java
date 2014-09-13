package com.acc.android.frame.util;

public class MD5Util {
	public static String getMD5(String sourceString) {
		try {
			java.security.MessageDigest messageDigest = java.security.MessageDigest
					.getInstance("MD5");
			byte[] byts = messageDigest.digest(sourceString.getBytes());
			String result = "";
			for (int i = 0; i < byts.length; i++) {
				String temp = Integer.toHexString(byts[i] & 0XFf);
				if (temp.length() == 1) {
					result += "0" + temp;
				} else {
					result += temp;
				}
			}
			return result;
		} catch (Exception e) {
			return "";
		}
	}
}