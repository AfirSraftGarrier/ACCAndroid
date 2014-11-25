package com.acc.android.frame.util;

import android.content.Context;

public class ExceptionUtil {
	public static IllegalArgumentException getIllegalArgumentException(
			Context context, int stringId) {
		return new IllegalArgumentException(context.getString(stringId));
	}

	public static IllegalArgumentException getIllegalArgumentException(
			String noteString) {
		return new IllegalArgumentException(noteString);
	}
}
