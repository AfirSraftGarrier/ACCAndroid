package com.acc.android.frame.manager;

import android.content.Context;
import android.widget.Toast;

import com.acc.android.frame.util.LoggerUtil;

public class ToastManager {
	private static ToastManager instance;
	private final Context context;

	public static ToastManager getInstance(Context context) {
		if (instance == null) {
			instance = new ToastManager(context);
		}
		return instance;
	}

	private ToastManager(Context context) {
		this.context = context;
		// super(context);
	}

	public void shortToast(int StringId) {
		Toast.makeText(context, StringId, Toast.LENGTH_SHORT).show();
	}

	public void shortToast(String toastString) {
		Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
	}

	public void longToast(String toastString) {
		Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
	}

	public void longToast(int StringId) {
		Toast.makeText(context, StringId, Toast.LENGTH_LONG).show();
	}

	public void shortToast(Object informationObject) {
		this.shortToast(LoggerUtil.getLogString(informationObject));
	}

	public void shortToast(Object tagObject, Object informationObject) {
		this.shortToast(LoggerUtil.getLogString(tagObject, informationObject));
	}

	public void shortToast(Object tagObject, String informationPrefixString,
			Object informationObject) {
		this.shortToast(LoggerUtil.getLogString(tagObject,
				informationPrefixString, informationObject));
	}

	public void longToast(Object informationObject) {
		this.longToast(LoggerUtil.getLogString(informationObject));
	}

	public void longToast(Object tagObject, Object informationObject) {
		this.longToast(LoggerUtil.getLogString(tagObject, informationObject));
	}

	public void longToast(Object tagObject, String informationPrefixString,
			Object informationObject) {
		this.longToast(LoggerUtil.getLogString(tagObject,
				informationPrefixString, informationObject));
	}
	// @Override
	// public void refresh() {
	// }
	//
	// @Override
	// public void init(Context context) {
	// }
}
