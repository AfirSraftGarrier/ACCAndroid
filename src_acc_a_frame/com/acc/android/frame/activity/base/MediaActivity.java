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
package com.acc.android.frame.activity.base;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

import com.acc.android.frame.activity.ImageActivity;
import com.acc.android.frame.manager.JsonManager;
import com.acc.android.frame.model.ImageData;
import com.acc.android.frame.util.IntentUtil;
import com.acc.android.frame.util.constant.ACCALibConstant;
import com.acc.android.frame.util.interf.MediaActivityInterface;

public abstract class MediaActivity extends ACCBaseActivity implements
		MediaActivityInterface {
	// protected static final int REQUEST_CODE_IMAGE_GROUP = 3;
	// protected static final int REQUEST_CODE_LOCFILE_PICK = 2;
	// protected static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
	// protected static final int REQUEST_CODE_VIDEO_CAPTURE = 0;
	private static final String DEFAULT_VEDIO_TYPE = "video/3gp";
	private OnActivityResultListener onActivityResultListener;
	private String tempPhotoPath;

	// public interface OnActivityResultListener {
	// void onImageCaptureResult(String localPath);
	//
	// void onLocalImageResult(String localPath);
	//
	// void onVedioCaptureResult(String localPath);
	//
	// void onImageGroupResult(ImageData imageData);
	// }

	@Deprecated
	public boolean isUseOld() {
		return this.onActivityResultListener == null;
	}

	@Override
	public void registOnActivityResultListener(
			OnActivityResultListener onActivityResultListener) {
		this.onActivityResultListener = onActivityResultListener;
	}

	@Override
	public void unRegistOnActivityResultListener() {
		this.onActivityResultListener = null;
	}

	// public void setCurrentImageGroupView(ImageGroupView
	// currentImageGroupView) {
	// this.currentImageGroupView = currentImageGroupView;
	// }

	@Override
	public void startPhotoCapture(String filePath) {
		// FileUtil.getFileName(fileName);
		// System.out.println("XXXXXVVVVVV" + filePath);
		this.tempPhotoPath = filePath;
		File mImageFile = new File(filePath);
		Uri bitmapDataUri = Uri.fromFile(mImageFile);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, bitmapDataUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
	}

	@Override
	public void startLocalImage() {
		// Intent intent = new Intent();
		// intent.setType("image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, REQUEST_CODE_LOCFILE_PICK);
	}

	@Override
	public void startVedio() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		startActivityForResult(intent, REQUEST_CODE_VIDEO_CAPTURE);
	}

	@Override
	public void startBigImage(ImageData imageData,
			String accImageGroupViewCallbackClassName, boolean isGoneAllAction) {
		IntentUtil.startActivityForResult(this, ImageActivity.class,
				REQUEST_CODE_IMAGE_GROUP,
				ACCALibConstant.KEY_BUNDLE_ACC_FILE_S, imageData,
				ACCALibConstant.KEY_BUNDLE_ACC_FILE_CALLBACK_NAME,
				accImageGroupViewCallbackClassName,
				ACCALibConstant.KEY_BUNDLE_IS_GONE_ALL_ACTION, isGoneAllAction);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// System.out.println("XXXXXXXX11111111");
		// System.out.println(intent == null);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_IMAGE_CAPTURE:
				this.onImageCaptureResult(intent);
				break;
			case REQUEST_CODE_LOCFILE_PICK:
				this.onLocalImageResult(intent);
				break;
			case REQUEST_CODE_VIDEO_CAPTURE:
				this.onVedioCaptureResult(intent);
				break;
			case REQUEST_CODE_IMAGE_GROUP:
				this.onImageGroupResult(intent);
				break;
			}
		}
	}

	public String getDataPath(Intent intent) {
		String resultString = null;
		Uri uri = intent.getData();
		try {
			Cursor cursor = this.getContentResolver().query(intent.getData(),
					null, null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				// System.out.println("VXXXXX");
				// System.out.println(intent.getDataString());
				return cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
			} else {
				String pathString = intent.getDataString();
				return pathString.substring(7, pathString.length());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		// try {
		// String pathString = intent.getDataString();
		// return pathString.substring(pathString.indexOf("//") + 1,
		// pathString.length());
		// } catch (Exception exception) {
		// exception.printStackTrace();
		// }
		// Toast.makeText(this, resultString, Toast.LENGTH_LONG).show();
		return null;
	}

	public String getPhotoDataPath(Intent intent) {
		if (intent == null) {
			return this.tempPhotoPath;
		}
		try {
			String[] pojo = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(intent.getData(), pojo, null,
					null, null);
			if (cursor != null) {
				int colunmIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				cursor.moveToFirst();
				return cursor.getString(colunmIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

		// Uri uri = intent.getData();
		// try {
		// String[] pojo = { MediaStore.Images.Media.DATA };
		// Cursor cursor = managedQuery(uri, pojo, null, null, null);
		// if (cursor != null) {
		// ContentResolver cr = this.getContentResolver();
		// int colunm_index = cursor
		// .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		// cursor.moveToFirst();
		// String path = cursor.getString(colunm_index);
		// /***
		// * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
		// * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
		// */
		// if (path.endsWith("jpg") || path.endsWith("png")) {
		// mPhotoFilePath = path;
		// addPhoto();
		// // File fromFile = new File(path);
		// /*
		// * try { FileInputStream fosfrom = new FileInputStream(
		// * fromFile); FileOutputStream fosto = new
		// * FileOutputStream( mFilePath); byte[] bt = new
		// * byte[1024]; int c;
		// *
		// * while ((c = fosfrom.read(bt)) > 0) { fosto.write(bt,
		// * 0, c); }
		// *
		// * // 关闭输入、输出流 fosfrom.close(); fosto.close(); } catch
		// * (FileNotFoundException e) { // TODO Auto-generated
		// * catch block e.printStackTrace(); } catch (IOException
		// * e) { // TODO Auto-generated catch block
		// * e.printStackTrace(); }
		// */
		// }
		// }
		// } catch (Exception e) {
		// }

	}

	@Override
	public void onImageCaptureResult(Intent intent) {
		// System.out.println("XXXXXXXX333");
		// System.out.println(intent == null);
		if (this.onActivityResultListener != null) {
			this.onActivityResultListener.onImageCaptureResult(this
					.getPhotoDataPath(intent));
		}
	}

	@Override
	public void onLocalImageResult(Intent intent) {
		if (this.onActivityResultListener != null) {
			this.onActivityResultListener.onLocalImageResult(this
					.getDataPath(intent));
		}
	}

	// public void onLocalImageResult(Intent intent) {
	// Uri uri = intent.getData();
	// try {
	// String[] pojo = { MediaStore.Images.Media.DATA };
	// Cursor cursor = managedQuery(uri, pojo, null, null, null);
	// if (cursor != null) {
	// ContentResolver contentResolver = this.getContentResolver();
	// int colunmIndex = cursor
	// .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	// cursor.moveToFirst();
	// String path = cursor.getString(colunmIndex);
	// }
	// } catch (Exception e) {
	// }
	// }

	// public void onVideoCaptureResult(String vedioPath) {
	//
	// }

	@Override
	public void onVedioCaptureResult(Intent intent) {
		// this.onVidelCaptureResult(this.getVideoPath(intent));
		if (this.onActivityResultListener != null) {
			this.onActivityResultListener.onVedioCaptureResult(this
					.getDataPath(intent));
		}
	}

	@Override
	public void onImageGroupResult(Intent intent) {
		// this.onVidelCaptureResult(this.getVideoPath(intent));
		if (this.onActivityResultListener != null) {
			String imageDataString = intent.getExtras().getString(
					ACCALibConstant.KEY_BUNDLE_ACC_FILE_S);
			ImageData imageData = JsonManager.getInstance().getObject(
					imageDataString, ImageData.class);
			// ImageData imageData = ImageGroupViewUtil.getImageData(this);
			this.onActivityResultListener.onImageGroupResult(imageData);
		}
	}

	public Bitmap getVideoThumbnail(String videoPath) {
		ContentResolver cr = this.getContentResolver();
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		String whereClause = MediaStore.Video.Media.DATA + " = '" + videoPath
				+ "'";
		Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.Media._ID }, whereClause, null,
				null);
		if (cursor == null || cursor.getCount() == 0) {
			if (cursor != null)
				cursor.close();
			return null;
		}
		cursor.moveToFirst();
		String videoId = cursor.getString(cursor
				.getColumnIndex(MediaStore.Video.Media._ID));
		cursor.close();
		if (videoId == null) {
			return null;
		}
		long videoIdLong = Long.parseLong(videoId);
		bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
				Images.Thumbnails.MICRO_KIND, options);
		return bitmap;
	}

	public void playVedio(String vedioPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri data = Uri.fromFile(new File(vedioPath));
		intent.setDataAndType(data, DEFAULT_VEDIO_TYPE);
		startActivity(intent);
	}
}