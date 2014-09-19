package com.acc.android.frame.manager;

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
		if(this.blankBitmap.get()!=null){
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
