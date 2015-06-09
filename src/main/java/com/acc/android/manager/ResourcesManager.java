package com.acc.android.manager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class ResourcesManager {
	private static ResourcesManager instance;
	private final Context context;

	private ResourcesManager(Context context) {
		this.context = context;
	}

	// @Override
	// public void init(Context context) {
	// }
	//
	// @Override
	// public void refresh() {
	// }

	public static ResourcesManager getInstance(Context context) {
		if (instance == null) {
			instance = new ResourcesManager(context);
		}
		return instance;
	}

	public String getString(int stringId) {
		return (String) context.getResources().getText(stringId);
	}

	public int getColor(int colorId) {
		return context.getResources().getColor(colorId);
	}

	public Drawable getDrawable(int drawableId) {
		return context.getResources().getDrawable(drawableId);
		// R.drawable.txt_search_null)
	}

	public String[] getStringArray(int stringArrayId) {
		return context.getResources().getStringArray(stringArrayId);
	}

	public Resources getResources() {
		return context.getResources();
	}
}
