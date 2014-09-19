package com.acc.android.frame.model;

import android.graphics.drawable.Drawable;

import com.acc.frame.model.MGeoPoint;

public class BaiduMapData {
	private MGeoPoint mGeoPoint;
	private Drawable drawable;

	public MGeoPoint getmGeoPoint() {
		return mGeoPoint;
	}

	public void setmGeoPoint(MGeoPoint mGeoPoint) {
		this.mGeoPoint = mGeoPoint;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
}
