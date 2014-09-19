package com.acc.android.frame.manager.base;

import java.util.List;

import android.content.Context;

import com.acc.android.frame.model.GeoData;
import com.acc.android.frame.util.callback.LocationCallback;
import com.acc.frame.util.ListUtil;

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
