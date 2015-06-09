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
package com.acc.android.manager.base;

import java.util.List;

import android.content.Context;

import com.acc.java.model.GeoData;
import com.acc.java.util.ListUtil;
import com.acc.java.util.callback.LocationCallback;

public abstract class LocationManager {
	protected List<LocationCallback> locationCallbacks;
	protected Context context;

	protected abstract void init(Context context);

	public abstract void start();

	public abstract void request();

	public abstract void removeUpdates();

	public abstract void stop();

	public LocationManager(Context context) {
		this.init(context);
		this.context = context;
	}

	public void rigist(LocationCallback gpsCallback) {
		this.add(gpsCallback);
	}

	public void unRigist(LocationCallback gpsCallback) {
		this.remove(gpsCallback);
	}

	private void add(LocationCallback locationCallback) {
		this.locationCallbacks = ListUtil.makeSureInit(this.locationCallbacks);
		this.locationCallbacks.add(locationCallback);
	}

	private void remove(LocationCallback gpsCallback) {
		if (!ListUtil.isEmpty(this.locationCallbacks)) {
			this.locationCallbacks.remove(gpsCallback);
		}
	}

	protected void clearLocationCallback() {
		if (this.locationCallbacks != null) {
			this.locationCallbacks.clear();
			this.locationCallbacks = null;
		}
	}

	protected void onReceiveGeoData(GeoData geoData) {
		if (!ListUtil.isEmpty(this.locationCallbacks)) {
			for (LocationCallback locationCallback : this.locationCallbacks) {
				locationCallback.receive(geoData);
			}
		}
	}
}