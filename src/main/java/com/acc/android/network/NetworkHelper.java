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
package com.acc.android.network;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.acc.android.network.operator.base.BaseHttpOperator;

public class NetworkHelper {
	private static NetworkHelper instance;
	private final Context context;

	private final Map<Class<? extends BaseHttpOperator>, BaseHttpOperator> httpOperatorMap;

	public void registHttpOprator(Class<? extends BaseHttpOperator> classT,
			BaseHttpOperator baseHttpOperator) {
		this.httpOperatorMap.put(classT, baseHttpOperator);
	}

	public void unRegistHttpOprator(Class<? extends BaseHttpOperator> classT) {
		this.httpOperatorMap.remove(classT);
	}

	public <T extends BaseHttpOperator> T getHttpOperator(
			Class<? extends T> classT) {
		BaseHttpOperator baseHttpOperator = this.httpOperatorMap.get(classT);
		if (baseHttpOperator == null) {
			try {
				baseHttpOperator = classT.getConstructor(Context.class)
						.newInstance(context);
				if (baseHttpOperator != null) {
					this.registHttpOprator(classT, baseHttpOperator);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return (T) baseHttpOperator;
	}

	private NetworkHelper(Context context) {
		this.context = context;
		this.httpOperatorMap = new HashMap<Class<? extends BaseHttpOperator>, BaseHttpOperator>();
	}

	public static NetworkHelper getInstance(Context context) {
		if (instance == null) {
			instance = new NetworkHelper(context);
		}
		return instance;
	}
}