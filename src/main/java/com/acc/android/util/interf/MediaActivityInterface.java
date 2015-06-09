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
package com.acc.android.util.interf;

import android.content.Intent;

import com.acc.android.model.ImageData;

public interface MediaActivityInterface {
	static final int REQUEST_CODE_IMAGE_GROUP = 3;
	static final int REQUEST_CODE_LOCFILE_PICK = 2;
	static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
	static final int REQUEST_CODE_VIDEO_CAPTURE = 0;

	interface OnActivityResultListener {
		void onImageCaptureResult(String localPath);

		void onLocalImageResult(String localPath);

		void onVedioCaptureResult(String localPath);

		void onImageGroupResult(ImageData imageData);
	}

	void registOnActivityResultListener(
			OnActivityResultListener onActivityResultListener);

	void unRegistOnActivityResultListener();

	void startPhotoCapture(String filePath);

	void startLocalImage();

	void startVedio();

	void startBigImage(ImageData imageData,
			String accImageGroupViewCallbackClassName, boolean isGoneAllAction);

	void onImageCaptureResult(Intent intent);

	void onLocalImageResult(Intent intent);

	void onVedioCaptureResult(Intent intent);

	void onImageGroupResult(Intent intent);
}