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
package com.acc.android.manager;

import java.lang.ref.SoftReference;

import android.content.Context;
import android.graphics.Bitmap;

public class BitmapManager {
	private SoftReference<Bitmap> blankBitmap;
	private static BitmapManager instance;

	public static BitmapManager getInstance(Context context) {
		if (instance == null) {
			instance = new BitmapManager(context);
		}
		return instance;
	}

	private BitmapManager(Context context) {
	}

	public Bitmap getBlankBitmap() {
		if (this.blankBitmap == null || this.blankBitmap.get() == null
				|| this.blankBitmap.get().isRecycled()) {
			this.initBlankBitmap();
			if (this.blankBitmap == null) {
				return null;
			}
		}
		return this.blankBitmap.get();
	}

	public void recycleBlankBitmap() {
		if (this.blankBitmap == null) {
			return;
		}
		if (this.blankBitmap.get() != null) {
			this.blankBitmap.get().recycle();
		}
		this.blankBitmap.clear();
		this.blankBitmap = null;
	}

	private void initBlankBitmap() {
		try {
			this.blankBitmap = new SoftReference<Bitmap>(Bitmap.createBitmap(1,
					1, Bitmap.Config.ARGB_8888));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}