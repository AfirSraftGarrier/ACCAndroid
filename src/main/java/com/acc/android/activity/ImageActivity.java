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
package com.acc.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.acc.android.model.ImageData;
import com.acc.android.util.FileUtil;
import com.acc.android.util.ImageGroupViewUtil;
import com.acc.android.util.ResourceUtil;
import com.acc.android.util.constant.ACCALibConstant;
import com.acc.android.util.interf.MediaActivityInterface;
import com.acc.android.util.view.ImageGroupView;
import com.acc.java.util.StringUtil;
import com.acc.java.util.callback.ACCImageGroupViewCallback;

public class ImageActivity extends Activity
		implements
		com.acc.android.util.interf.MediaActivityInterface.OnActivityResultListener,
		MediaActivityInterface {
	private ImageGroupView imageGroupView;

	// private ImageData imageData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(ResourceUtil.getLayoutId(this, "image"));
		this.imageGroupView = (ImageGroupView) this.findViewById(ResourceUtil
				.getId(this, "image_group_picture"));
		String accImageGroupViewCallbackClassName = this.getIntent()
				.getExtras()
				.getString(ACCALibConstant.KEY_BUNDLE_ACC_FILE_CALLBACK_NAME);
		if (!StringUtil.isEmpty(accImageGroupViewCallbackClassName)) {
			try {
				ACCImageGroupViewCallback accImageGroupViewCallback = (ACCImageGroupViewCallback) Class
						.forName(accImageGroupViewCallbackClassName)
						.newInstance();
				// System.out.println("VVVVVVVVVVCCCCCCCCCCCCCCCCCCC"
				// + accImageGroupViewCallback);
				this.imageGroupView
						.resolveImageGroupCallBack(accImageGroupViewCallback);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ImageData imageData = ImageGroupViewUtil.getImageData(this);
		this.imageGroupView.setImageData(imageData);
		// boolean isGoneAllAciton = this.getIntent().getExtras()
		// .getBoolean(ACCALibConstant.KEY_BUNDLE_IS_GONE_ALL_ACTION);
		if (this.getIntent().getExtras()
				.getBoolean(ACCALibConstant.KEY_BUNDLE_IS_GONE_ALL_ACTION)) {
			this.imageGroupView.goneAllActionButton();
		}
		this.imageGroupView.goneAllActionButton();
		// ACCALibConstant.KEY_BUNDLE_ACC_FILE_CALLBACK_NAME,
		// accImageGroupViewCallbackClassName,
	}

	@Override
	public void onImageCaptureResult(String localPath) {
		this.imageGroupView.compressPhotoToAdd(localPath, localPath);
	}

	@Override
	public void onLocalImageResult(String localPath) {
		// System.out.println("VVVVVV11111");
		// System.out.println(localPath);
		this.imageGroupView.compressPhotoToAdd(localPath,
				FileUtil.getTimestampImageFilePath("l"));
		// this.imageGroupView.addPhoto(localPath);
	}

	@Override
	public void onVedioCaptureResult(String localPath) {
	}

	@Override
	protected void onDestroy() {
		this.imageGroupView.onDestroy();
	}

	// @Override
	// public void onImageCaptureResult(String localPath) {
	//
	// }
	//
	// @Override
	// public void onLocalImageResult(String localPath) {
	//
	// }
	//
	// @Override
	// public void onVedioCaptureResult(String localPath) {
	//
	// }

	@Override
	public void onImageGroupResult(ImageData imageData) {
	}

	@Override
	public void registOnActivityResultListener(
			OnActivityResultListener onActivityResultListener) {
	}

	@Override
	public void unRegistOnActivityResultListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startPhotoCapture(String filePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startLocalImage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startVedio() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startBigImage(ImageData imageData,
			String accImageGroupViewCallbackClassName, boolean isGoneAllAction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onImageCaptureResult(Intent intent) {
	}

	@Override
	public void onLocalImageResult(Intent intent) {
	}

	@Override
	public void onVedioCaptureResult(Intent intent) {
	}

	@Override
	public void onImageGroupResult(Intent intent) {
	}

	// @Override
	// protected void onStop() {
	// super.onStop();
	// }

	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// Intent intent = new Intent();
	// intent.putExtras(IntentUtil.getBundle(
	// ACCALibConstant.KEY_BUNDLE_ACC_FILE_S, this.imageGroupView
	// .getImageAdapter().getImageData()));
	// this.setResult(MediaActivity.REQUEST_CODE_IMAGE_GROUP, intent);
	// this.finish();
	// }
}