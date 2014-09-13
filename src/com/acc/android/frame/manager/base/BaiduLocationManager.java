package com.acc.android.frame.manager.base;

import android.content.Context;

import com.acc.android.frame.model.GeoData;
import com.acc.android.frame.model.GeoDataWithoutAddress;
import com.acc.android.frame.model.GeoStatus;
import com.acc.android.frame.model.http.response.GeoAddress;
import com.acc.android.frame.network.NetworkHelper;
import com.acc.android.frame.network.operator.CommonHttpOperator;
import com.acc.android.frame.util.StringUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiduLocationManager extends LocationManager {
	// private List<GPSCallback> gpsCallbacks;
	private LocationClient locationClient;
	private LocationListenner locationListenner;
	private Thread requestAddressThread;
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

	private void stopRequestAddressThread() {
		if (this.requestAddressThread != null
				&& this.requestAddressThread.isAlive()) {
			this.requestAddressThread.interrupt();
		}
	}

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
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setScanSpan(1000);
		option.setPriority(LocationClientOption.GpsFirst);
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

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
		}

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
