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

import android.content.Context;

import com.acc.android.model.http.response.GeoAddress;
import com.acc.android.network.NetworkHelper;
import com.acc.android.network.operator.CommonHttpOperator;
import com.acc.java.model.GeoData;
import com.acc.java.model.GeoDataWithoutAddress;
import com.acc.java.model.GeoStatus;
import com.acc.java.util.StringUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class BaiduLocationManager extends LocationManager {
	// private List<GPSCallback> gpsCallbacks;
	private LocationClient locationClient;
	private LocationListenner locationListenner;
	// private Thread requestAddressThread;
	// private Context context;
	private static BaiduLocationManager instance;

	private BaiduLocationManager(Context context) {
		super(context);
	}

	public static BaiduLocationManager getInstance(Context context) {
		if (instance == null) {
			instance = new BaiduLocationManager(context);
		}
		return instance;
	}

	@Override
	public void init(Context context) {
		this.context = context;
		this.locationClient = new LocationClient(
				context.getApplicationContext());
		this.locationListenner = new LocationListenner();
		this.locationClient.registerLocationListener(locationListenner);
		this.initLocationOption();
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		this.locationClient.stop();
		this.clearLocationCallback();
	}

	// private void stopRequestAddressThread() {
	// if (this.requestAddressThread != null
	// && this.requestAddressThread.isAlive()) {
	// this.requestAddressThread.interrupt();
	// }
	// }

	// @Override
	// public void rigist(GPSCallback gpsCallback) {
	// this.add(gpsCallback);
	// }
	//
	// @Override
	// public void unRigist(GPSCallback gpsCallback) {
	// this.remove(gpsCallback);
	// }
	//
	// private void add(GPSCallback gpsCallback) {
	// gpsCallbacks = ListUtil.makeSureInit(gpsCallbacks);
	// gpsCallbacks.add(gpsCallback);
	// }
	//
	// private void remove(GPSCallback gpsCallback) {
	// if (!ListUtil.isEmpty(this.gpsCallbacks)) {
	// this.gpsCallbacks.remove(gpsCallback);
	// }
	// }
	//
	// private void clearGPSCallback() {
	// if (this.gpsCallbacks != null) {
	// this.gpsCallbacks.clear();
	// this.gpsCallbacks = null;
	// }
	// }

	private void initLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		option.setOpenGps(true);
		option.setIsNeedAddress(true);
		// option.setScanSpan(1000);
		// option.setPriority(LocationClientOption.GpsFirst);
		locationClient.setLocOption(option);
	}

	@Override
	public void onReceiveGeoData(GeoData geoData) {
		switch (geoData.getGeoDataWithoutAddress().getGeoStatus()) {
		case GPSNET:
		case NET:
			if (StringUtil.isEmpty(geoData.getAddress())) {
				GeoAddress geoAddress = NetworkHelper
						.getInstance(context)
						.getHttpOperator(CommonHttpOperator.class)
						.getGeoAddress(
								geoData.getGeoDataWithoutAddress()
										.getLatitude(),
								geoData.getGeoDataWithoutAddress()
										.getLongitude());
				if (geoAddress != null && geoAddress.getResult() != null) {
					geoData.setAddress(geoAddress.getResult()
							.getFormatted_address());
				}
				// geoData.setAddress(NetworkHelper
				// .getInstance(context)
				// .getHttpOpera(OtherHttpOpera.class)
				// .getGeoAddress(
				// geoData.getGeoDataWithoutAddress()
				// .getLatitude(),
				// geoData.getGeoDataWithoutAddress()
				// .getLongitude()).getResult()
				// .getFormatted_address());
			}
			break;
		default:
		}
		super.onReceiveGeoData(geoData);
		// if (!ListUtil.isEmpty(this.gpsCallbacks)) {
		// for (GPSCallback gpsCallback : this.gpsCallbacks) {
		// gpsCallback.receive(geoData);
		// }
		// }
	}

	//
	// private void onReceiveGeoDataWithoutAddress(
	// GeoDataWithoutAddress geoDataWithoutAddress) {
	//
	// }

	public class LocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			// if (StringUtil.isEmpty(location.getAddrStr())) {
			//
			// } else {
			GeoData geoData = new GeoData();
			// System.out.println("addr:" + location.getAddrStr());
			// System.out.println("addr11:" + location.getLatitude());
			// System.out.println("addr22:" + location.getLongitude());
			geoData.setAddress(location.getAddrStr());
			GeoDataWithoutAddress geoDataWithoutAddress = new GeoDataWithoutAddress();
			geoDataWithoutAddress.setAccuracy(location.getRadius());
			geoDataWithoutAddress.setGeoStatus(this.getGeoStatus(location));
			// ToastManager.getInstance(context).shortToast(
			// "baidu-" + geoDataWithoutAddress.getGeoStatus());
			geoDataWithoutAddress.setLatitude(location.getLatitude());
			geoDataWithoutAddress.setLongitude(location.getLongitude());
			geoData.setGeoDataWithoutAddress(geoDataWithoutAddress);
			BaiduLocationManager.this.onReceiveGeoData(geoData);
			// }
		}

		// @Override
		// public void onReceivePoi(BDLocation poiLocation) {
		// }

		private GeoStatus getGeoStatus(BDLocation location) {
			int locType = location.getLocType();
			switch (locType) {
			case 61:
				return GeoStatus.GPSNET;
			case 63:
				return GeoStatus._GPS_NET;
			case 68:
				return GeoStatus.GPS_NET;
			case 161:
				return GeoStatus.NET;
			default:
				return GeoStatus.OTHER;
			}
		}
	}

	@Override
	public void request() {
		this.locationClient.start();
		this.locationClient.requestLocation();
	}

	@Override
	public void removeUpdates() {
		this.locationClient.stop();
	}
}