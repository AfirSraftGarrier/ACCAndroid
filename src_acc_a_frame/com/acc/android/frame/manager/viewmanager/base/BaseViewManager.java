package com.acc.android.frame.manager.viewmanager.base;

import android.content.Context;
import android.view.View;

public abstract class BaseViewManager {
	private final View containerView;
	protected final Context context;

	BaseViewManager(View containerView, Context context) {
		this.containerView = containerView;
		this.context = context;
	}

	protected View findViewById(int viewId) {
		if (containerView != null) {
			return containerView.findViewById(viewId);
		}
		return null;
	}
}
