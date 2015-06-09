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