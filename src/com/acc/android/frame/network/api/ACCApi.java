package com.acc.android.frame.network.api;

import android.content.Context;

public abstract class ACCApi {
	private static String URL_REAQUEST_LOCATION;
	private final Context context;

	public ACCApi(Context context) {
		this.context = context;
		URL_REAQUEST_LOCATION = "http//api.map.baidu.com/geocoder?output=json&location=";
	}

	public static String getUrlReaquestAddress(double latitude, double longitude) {
		return URL_REAQUEST_LOCATION + latitude + "," + longitude;
	}
}
