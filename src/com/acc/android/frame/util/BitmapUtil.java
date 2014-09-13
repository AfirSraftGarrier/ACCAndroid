package com.acc.android.frame.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class BitmapUtil {
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static Bitmap getBitmap(String bitmapPath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		return BitmapFactory.decodeFile(bitmapPath, options);
	}
}
