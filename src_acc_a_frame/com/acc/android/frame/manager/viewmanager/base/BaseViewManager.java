/**
 * 
 * ACCAFrame - ACC Android Development Platform
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