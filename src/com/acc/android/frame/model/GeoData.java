package com.acc.android.frame.model;

public class GeoData {
	private String address;
	private GeoDataWithoutAddress geoDataWithoutAddress;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public GeoDataWithoutAddress getGeoDataWithoutAddress() {
		return geoDataWithoutAddress;
	}

	public void setGeoDataWithoutAddress(GeoDataWithoutAddress geoDataWithoutAddress) {
		this.geoDataWithoutAddress = geoDataWithoutAddress;
	}
}
