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
package com.acc.android.network.operator;

import android.content.Context;

import com.acc.android.model.http.request.RequestMethod;
import com.acc.android.model.http.response.GeoAddress;
import com.acc.android.network.api.ACCApi;
import com.acc.android.network.operator.base.BaseHttpOperator;

public class CommonHttpOperator extends BaseHttpOperator {

	public CommonHttpOperator(Context context) {
		super(context);
	}

	public GeoAddress getGeoAddress(double latitude, double longitude) {
		return this.getResultObject(
				ACCApi.getUrlReaquestAddress(latitude, longitude), null,
				RequestMethod.GET, GeoAddress.class);
	}

	// @Override
	// public BaseHttpOperator getInstance(Context context) {
	// return new CommonHttpOperator(context);
	// }
}