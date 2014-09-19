package com.acc.android.frame.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.baidu.mapapi.map.MapView;

public class MMapView extends MapView {
	private int currentTouchX;
	private int currentTouchY;

	public MMapView(Context context) {
		super(context);
	}

	public MMapView(Context context, AttributeSet set) {
		super(context, set);
	}

	public MMapView(Context context, AttributeSet set, int i) {
		super(context, set, i);
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		// TODO Auto-generated method stub
		this.setCurrentTouchX((int) motionEvent.getX());
		this.setCurrentTouchY((int) motionEvent.getY());
		// GeoPoint geoPoint = this.getProjection().fromPixels(x, y);
		// int xx = geoPoint.getLongitudeE6();
		// int yy = geoPoint.getLatitudeE6();
		// Log.d("xxxxxxxxxxx", Integer.toString(xx));
		// Log.d("yyyyyyyyyyy", Integer.toString(yy));
		return super.onTouchEvent(motionEvent);
	}

	public int getCurrentTouchY() {
		return currentTouchY;
	}

	public void setCurrentTouchY(int currentTouchY) {
		this.currentTouchY = currentTouchY;
	}

	public int getCurrentTouchX() {
		return currentTouchX;
	}

	public void setCurrentTouchX(int currentTouchX) {
		this.currentTouchX = currentTouchX;
	}
}