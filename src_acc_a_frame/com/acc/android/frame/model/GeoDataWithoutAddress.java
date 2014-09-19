package com.acc.android.frame.model;

public class GeoDataWithoutAddress {
	private double longitude;
	private double latitude;
	private GeoStatus geoStatus;
	private double accuracy;

	// private double speed;
	// private int satelliteNum;

	public GeoStatus getGeoStatus() {
		return geoStatus;
	}

	public void setGeoStatus(GeoStatus geoStatus) {
		this.geoStatus = geoStatus;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	// public double getSpeed() {
	// return speed;
	// }
	//
	// public void setSpeed(double speed) {
	// this.speed = speed;
	// }
	//
	// public int getSatelliteNum() {
	// return satelliteNum;
	// }
	//
	// public void setSatelliteNum(int satelliteNum) {
	// this.satelliteNum = satelliteNum;
	// }

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
