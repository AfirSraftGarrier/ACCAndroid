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
package com.acc.android.util;

import java.io.File;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.acc.android.activity.base.MediaActivity;
import com.acc.android.util.widget.adapter.ImageAdapter;
import com.acc.java.model.ACCFile;

public class ACCImageUtil {
	public interface OnDeletedPhotoListener {
		void onDeletedPhoto();
	}

	// public static void showImage(Context context, String imagePath) {
	// Intent intent = new Intent();
	// Bundle bundle = new Bundle();
	// bundle.putString("FilePath", imagePath);
	// intent.putExtras(bundle);
	// intent.setClass(context, MyphotoActivity.class);
	// context.startActivity(intent);
	// }

	// public static void showMyImage(Context context, String smallPath,
	// String imagePath, String url) {
	// Intent intent = new Intent();
	// Bundle bundle = new Bundle();
	// bundle.putString("SmallPath", smallPath);
	// bundle.putString("FilePath", imagePath);
	// bundle.putString("Url", url);
	// intent.putExtras(bundle);
	// intent.setClass(context, MyphotoActivity.class);
	// context.startActivity(intent);
	// }

	// public static void showImage(Context context, String tempShowPath,
	// String filePath, String downloadUrl) {
	// // Intent intent = new Intent();
	// // Bundle bundle = new Bundle();
	// // bundle.putString("SmallPath", smallPath);
	// // bundle.putString("FilePath", imagePath);
	// // bundle.putString("Url", url);
	// ImageData imageData = new ImageData();
	// imageData.setDownloadUrl(downloadUrl);
	// imageData.setFilePath(filePath);
	// imageData.setTempShowPath(tempShowPath);
	// // intent.putExtras(bundle);
	// // intent.setClass(context, MyphotoActivity.class);
	// // context.startActivity(intent);
	// IntentUtil.startIntent(context, ImageActivity.class,
	// BundleKeyConstant.IMAGE_DATA, imageData);
	// }

	public static void deleteImage(final Context context,
			final String imagePath,
			final OnDeletedPhotoListener onDeletedPhotoListener) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("鍒犻櫎鍥剧墖").setMessage("纭畾瑕佸垹闄ゅ悧锛�")
				.setPositiveButton("纭畾", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							// if (gallery.getSelectedItem() != null) {
							// int id = gallery.getSelectedItemPosition();
							// Photo photo = pList.get(id);
							// if (photo.getDbID() != null) {
							// PhotoDao photoDao = new PhotoDao(context);
							// photoDao.DeletePhoto(photo);
							// }
							// pList.remove(id);
							// edtPhotoAddr.setText("");
							// edtPhotoInfo.setText("");
							// imageAdapter.notifyDataSetChanged();
							// Utils.MessageBox(context, "鍒犻櫎鍥剧墖", "鍒犻櫎鎴愬姛锛�");
							// } else {
							// Utils.MessageBox(context, "鍒犻櫎鍥剧墖", "閿欒锛氭病鏈夐�夋嫨鍥剧墖锛�");
							// }
							if (imagePath != null) {
								File file = new File(imagePath);
								file.delete();
							}
							onDeletedPhotoListener.onDeletedPhoto();
						} catch (Exception e) {
							// TODO: handle exception
							TimeUtil.MessageBox(context, "鍒犻櫎鍥剧墖", "鍒犻櫎澶辫触锛�");
						}
					}
				})
				.setNegativeButton("鍙栨秷", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setCancelable(false).create().show();
	}

	public void goToBigImage(List<ACCFile> accFiles,
			MediaActivity contextActivity, ImageAdapter imageAdapter) {
	}
}