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
package com.acc.android.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

public class ACCGallery extends Gallery
// implements
// android.view.View.OnTouchListener
{
	// private GestureDetector gestureDetector;

	// private Activity contextActivity;

	public ACCGallery(Context context) {
		super(context);
	}

	public ACCGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.setOnTouchListener(this);
		// this.contextActivity = (Activity) context;
		// this.gestureDetector = new GestureDetector(new MGesture());
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		// ;
		View view = ACCGallery.this.getSelectedView();
		if (view instanceof AutoImageView) {
			AutoImageView autoImageView = (AutoImageView) view;
			try {
				autoImageView.onMTouchEvent(motionEvent);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return super.onTouchEvent(motionEvent);
		// View view = ACCGallery.this.getSelectedView();
		// if (view instanceof AutoImageView) {
		// AutoImageView autoImageView = (AutoImageView) view;
		// autoImageView.onDoubleClick();
		// }
		// return true;
	}

	@Override
	public boolean onScroll(MotionEvent motionEventFirst,
			MotionEvent motionEventSecond, float distanceX, float distanceY) {
		View view = ACCGallery.this.getSelectedView();
		if (view instanceof AutoImageView) {
			AutoImageView autoImageView = (AutoImageView) view;
			// try {
			// autoImageView.onMTouchEvent(motionEvent);
			// } catch (Exception exception) {
			// exception.printStackTrace();
			// }
			// boolean is =
			if (!autoImageView.isNeedNextMotionEvent(motionEventFirst,
					motionEventSecond)) {
				return super.onScroll(motionEventFirst, motionEventSecond,
						distanceX, distanceY);
			}
			// return autoImageView.isNeedNextMotionEvent(motionEventFirst,
			// motionEventSecond);
		}
		return false;
	}
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// return false;
	// }

	//
	// private class MGesture extends SimpleOnGestureListener {
	// @Override
	// public boolean onDoubleTap(MotionEvent motionEvent) {
	// // View view = ACCGallery.this.getSelectedView();
	// // if (view instanceof AutoImageView) {
	// // AutoImageView autoImageView = (AutoImageView) view;
	// // autoImageView.onDoubleClick();
	// // }
	// return false;
	// }
	//
	// @Override
	// public boolean onSingleTapUp(MotionEvent motionEvent) {
	// // ACCGallery.this.contextActivity.finish();
	// // return super.onSingleTapUp(motionEvent);
	// return false;
	// }
	//
	// @Override
	// public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
	// // System.out.println("SSSSSSSSSGGGGGGGGGGGGGGGG");
	// // ACCGallery.this.contextActivity.finish();
	// return false;
	// }
	//
	// @Override
	// public boolean onDoubleTapEvent(MotionEvent e) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean onDown(MotionEvent e) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// // @Override
	// // public void onLongPress(MotionEvent e) {
	// // // TODO Auto-generated method stub
	// // super.onLongPress(e);
	// // };
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2,
	// float distanceX, float distanceY) {
	// // TODO Auto-generated method stub
	// return false;
	// };
	// }

	// @Override
	// public boolean onTouch(View view, MotionEvent motionevent) {
	// // TODO Auto-generated method stub
	// return false;
	// }
}