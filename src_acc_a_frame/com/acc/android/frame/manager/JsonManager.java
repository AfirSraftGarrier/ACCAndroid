package com.acc.android.frame.manager;

import com.acc.frame.manager.base.BaseJsonManager;

public class JsonManager extends BaseJsonManager {
	private static JsonManager instance;

	public static JsonManager getInstance() {
		if (instance == null) {
			instance = new JsonManager();
		}
		return instance;
	}
}
