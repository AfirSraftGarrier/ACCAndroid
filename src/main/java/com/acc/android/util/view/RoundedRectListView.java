/**
 * 
 * ACCAndroid - ACC Android Development Platform
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
package com.acc.android.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class RoundedRectListView extends ListView {
	private static final float RADIUS = 16;
	private Path mClip;

	public RoundedRectListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		GradientDrawable gd = new GradientDrawable();
		gd.setCornerRadius(RADIUS);
		gd.setColor(0xff208020);
		setBackgroundDrawable(gd);
		setCacheColorHint(0);
		setVerticalFadingEdgeEnabled(false);
		StateListDrawable sld = new StateListDrawable();
		sld.addState(PRESSED_ENABLED_STATE_SET, new GradientDrawable(
				Orientation.LEFT_RIGHT, new int[] { 0xffa58cf5, 0xffa13f99 }));
		sld.addState(EMPTY_STATE_SET, new GradientDrawable(
				Orientation.LEFT_RIGHT, new int[] { 0xff058cf5, 0xff013f99 }));
		setSelector(sld);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mClip = new Path();
		RectF rect = new RectF(0, 0, w, h);
		mClip.addRoundRect(rect, RADIUS, RADIUS, Direction.CW);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		canvas.save();
		canvas.clipPath(mClip);
		super.dispatchDraw(canvas);
		canvas.restore();
	}
}