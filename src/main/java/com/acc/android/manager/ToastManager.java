/**
 * 
 * ACCAndroid - ACC Android Development Platform
 * Copyright (c) 2014, AfirSraftGarrier, afirsraftgarrier@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.acc.android.manager;

import android.content.Context;
import android.widget.Toast;

import com.acc.android.util.LogUtil;

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
		// String sdfsd = LogUtil.getLogString(informationObject);
		// System.out.println(sdfsd);
		this.shortToast(LogUtil.getLogString(informationObject));
	}

	// public void shortToast(Object tagObject, Object informationObject) {
	// this.shortToast(LogUtil.getLogString(tagObject, informationObject));
	// }

	public void shortToast(Object prefixObject,
	// , String informationPrefixString,
			Object informationObject) {
		this.shortToast(LogUtil.getLogString(
		// tagObject,
				prefixObject, informationObject));
	}

	public void longToast(Object informationObject) {
		this.longToast(LogUtil.getLogString(informationObject));
	}

	public void longToast(Object prefixObject, Object informationObject) {
		this.longToast(LogUtil.getLogString(prefixObject, informationObject));
	}

	// public void longToast(Object tagObject, String informationPrefixString,
	// Object informationObject) {
	// this.longToast(LogUtil.getLogString(tagObject, informationPrefixString,
	// informationObject));
	// }
	// @Override
	// public void refresh() {
	// }
	//
	// @Override
	// public void init(Context context) {
	// }
}