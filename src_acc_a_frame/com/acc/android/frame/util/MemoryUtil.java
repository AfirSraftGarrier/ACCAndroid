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

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

public class MemoryUtil {
	// public static final String TAG = "Memory";
	//
	// public static void updateMemoryStatus(Context context) {
	// String status = Environment.getExternalStorageState();
	// String readOnly = "";
	// // 是否只读
	// if (status.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
	// status = Environment.MEDIA_MOUNTED;
	// readOnly = "SD卡为只读";
	// }
	// if (status.equals(Environment.MEDIA_MOUNTED)) {
	// try {
	// File path = Environment.getExternalStorageDirectory();
	// StatFs stat = new StatFs(path.getPath());
	// long blockSize = stat.getBlockSize();
	// long totalBlocks = stat.getBlockCount();
	// long availableBlocks = stat.getAvailableBlocks();
	// // SD卡总容量
	// String sdSize = formatSize(context, totalBlocks * blockSize);
	// Log.i(TAG, "SD卡总容量: " + sdSize);
	// // SD卡剩余容量
	// String sdAvail = formatSize(context, availableBlocks
	// * blockSize)
	// + readOnly;
	// Log.i(TAG, "SD卡剩余容量: " + sdAvail);
	// } catch (IllegalArgumentException e) {
	// status = Environment.MEDIA_REMOVED;
	// }
	// }
	// File path = Environment.getDataDirectory();
	// StatFs stat = new StatFs(path.getPath());
	// long blockSize = stat.getBlockSize();
	// long availableBlocks = stat.getAvailableBlocks();
	// // 手机内存剩余容量
	// String memoryAvail = formatSize(context, availableBlocks * blockSize);
	// Log.i(TAG, "手机内存剩余容量: " + memoryAvail);
	// long totalBlocks = stat.getBlockCount();
	// // 手机内存总容量
	// String memorySize = formatSize(context, totalBlocks * blockSize);
	// Log.i(TAG, "手机内存总容量: " + memorySize);
	// }

	public static double getSDCardRemainInMB(Context context) {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				double sdAvailInMB = formatSizeInMB(availableBlocks * blockSize);
				return sdAvailInMB;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	private static double formatSizeInMB(long size) {
		double sizeInMB = (int) (size / (1024 * 1024));
		return sizeInMB;
	}
}