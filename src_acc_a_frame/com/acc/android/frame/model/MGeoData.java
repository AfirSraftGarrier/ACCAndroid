package com.acc.android.frame.model;

public class MGeoData extends GeoData {
	private GeoData baiduGeoData;
	private GeoData systemGeoData;

	public GeoData getBaiduGeoData() {
		return baiduGeoData;
	}

	public void setBaiduGeoData(GeoData baiduGeoData) {
		this.baiduGeoData = baiduGeoData;
	}

	public GeoData getSystemGeoData() {
		return systemGeoData;
	}

	public void setSystemGeoData(GeoData systemGeoData) {
		this.systemGeoData = systemGeoData;
	}
}
