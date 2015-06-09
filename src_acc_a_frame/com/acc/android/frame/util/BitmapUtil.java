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
package com.acc.android.frame.util;

import java.lang.ref.SoftReference;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.acc.android.frame.util.constant.ACCALibConstant;
import com.acc.frame.util.ACCFileUtil;
import com.acc.frame.util.MapUtil;

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

	public static void recycleBitmapMap(
			Map<? extends Object, SoftReference<Bitmap>> softBitmapMap) {
		if (MapUtil.isEmpty(softBitmapMap)) {
			return;
		}
		for (SoftReference<Bitmap> softBitmap : softBitmapMap.values()) {
			Bitmap bitmap = softBitmap.get();
			if (bitmap == null || bitmap.isRecycled()) {
				continue;
			}
			bitmap.recycle();
		}
		softBitmapMap.clear();
	}

	public static Bitmap getRightBitmap(String filePath) {
		Bitmap bitmap = null;
		try {
			if (ACCFileUtil.getFileSize(filePath, ACCFileUtil.FileSizeType.MB) > ACCALibConstant.BITMAPNOTMAXTHANM) {
				return getSmallBitmap(filePath);
			}
			bitmap = BitmapFactory.decodeFile(filePath, null);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
}