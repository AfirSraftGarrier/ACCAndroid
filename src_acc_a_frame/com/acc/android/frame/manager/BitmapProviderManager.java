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
package com.acc.android.frame.manager;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.acc.android.frame.util.BitmapUtil;
import com.acc.android.frame.util.FileUtil;
import com.acc.frame.model.ACCFile;
import com.acc.frame.util.StreamUtil;
import com.acc.frame.util.callback.FileDownloadCallback;
import com.acc.frame.util.listener.FileDownloadListener;
import com.acc.frame.util.listener.RequestListener;
import com.acc.frame.util.listener.RequestListener.RequestFailReason;

//import com.augurit.android.hfss.network.operator.UploadHttpOpera;

public class BitmapProviderManager {
	private Context context;
	private FileDownloadListener fileDownloadListener;
	private FileDownloadCallback fileDownloadCallback;
	private Map<String, SoftReference<Bitmap>> softBitmapMap;
	private List<String> downloadingKey;
	protected ExecutorService threadPool;

	public void setFileDownloadCallback(
			FileDownloadCallback fileDownloadCallback) {
		this.fileDownloadCallback = fileDownloadCallback;
	}

	public BitmapProviderManager(Context context,
			FileDownloadListener fileDownloadListener,
			FileDownloadCallback fileDownloadCallback) {
		this.context = context;
		this.fileDownloadListener = fileDownloadListener;
		this.softBitmapMap = new HashMap<String, SoftReference<Bitmap>>();
		this.downloadingKey = new ArrayList<String>();
		this.threadPool = Executors.newCachedThreadPool();
		this.fileDownloadCallback = fileDownloadCallback;
	}

	public Bitmap getBitmap(ACCFile accFile) {
		SoftReference<Bitmap> bitmapSoftRefrence = this.softBitmapMap
				.get(accFile.getKey());
		Bitmap bitmap = bitmapSoftRefrence == null ? null : bitmapSoftRefrence
				.get();
		if (bitmap != null && !bitmap.isRecycled()) {
			return bitmap;
		}
		if (FileUtil.isFileExists(accFile.getLocalPath())) {
			bitmap = BitmapUtil.getRightBitmap(accFile.getLocalPath());
		} else {
			// accFile.setProgress(progress);
			this.downloadBitmap(accFile);
			if (FileUtil.isFileExists(accFile.getTempPath())) {
				bitmap = BitmapUtil.getRightBitmap(accFile.getTempPath());
			} else {
				bitmap = BitmapManager.getInstance(context).getBlankBitmap();
			}
		}
		this.softBitmapMap.put(accFile.getKey(), new SoftReference<Bitmap>(
				bitmap));
		return bitmap;
	}

	private void downloadBitmap(final ACCFile accFile) {
		accFile.setProgress(0f);
		if (accFile.getNetUrl() == null
				|| this.downloadingKey.contains(accFile.getKey())) {
			return;
		} else {
			this.downloadingKey.add(accFile.getKey());
		}

		BitmapProviderManager.this.threadPool.execute(new Runnable() {

			@Override
			public void run() {
				InputStream inputStream = null;
				try {
					Bitmap bitmap = null;
					try {
						byte[] bytes = null;
						if (BitmapProviderManager.this.fileDownloadCallback != null) {
							bytes = BitmapProviderManager.this.fileDownloadCallback
									.getByteArray(accFile.getNetUrl(),
											new RequestListener() {

												@Override
												public void onSuccess(
														Object valueObject) {
													BitmapProviderManager.this
															.excuteFileDownloadListener(accFile);
												}

												@Override
												public void onFail(
														RequestFailReason fileDownloadFailReason) {
													BitmapProviderManager.this
															.excuteFileDownloadListener(
																	accFile,
																	fileDownloadFailReason);
												}

												@Override
												public void onProgress(
														double already,
														double total) {
													float progress = (float) (already * 1f / total);
													// System.out
													// .println("++++++++++++++");
													// System.out.println(already);
													// System.out.println(total);
													// System.out
													// .println(progress);
													if (progress < 0
															&& (accFile
																	.getProgress() == null || accFile
																	.getProgress() <= 0.9999)) {
														// progress =
														// Math.max(progress,
														// 0);
														if (accFile
																.getProgress() == null) {
															accFile.setProgress(0f);
														}
														progress = Math.min(
																accFile.getProgress() + 0.001f,
																0.999f);
														// System.out
														// .println("111");
													} else {
														// System.out
														// .println(progress);
														// System.out
														// .println(accFile
														// .getProgress());
														// System.out
														// .println("VVVVV");
														progress = Math.max(
																progress, 0);
														progress = Math.min(
																progress, 1);
														// this.currentProgress
														// =
														// progress;
													}
													accFile.setProgress(progress);
													BitmapProviderManager.this
															.excuteFileDownloadListener(
																	accFile,
																	already,
																	total);
												}
											});
						}
						// com.augurit.android.hfss.network.helper.NetworkHelper
						// .getInstance(BitmapProviderManager.this.context)
						// .getHttpOpera(UploadHttpOpera.class)
						// .makeSureSSO();
						// byte[] bytes =
						// com.augurit.android.hfss.network.helper.NetworkHelper
						// .getInstance(BitmapProviderManager.this.context)
						// .getHttpOpera(UploadHttpOpera.class)
						// .getByteArray(
						// accFile.getNetUrl(),
						// com.augurit.android.hfss.model.RequestMethod.GET,
						// null, new RequestListener() {
						//
						// @Override
						// public void onSuccess(
						// Object valueObject) {
						// BitmapProviderManager.this
						// .excuteFileDownloadListener(accFile);
						// }
						//
						// @Override
						// public void onFail(
						// RequestFailReason fileDownloadFailReason) {
						// BitmapProviderManager.this
						// .excuteFileDownloadListener(
						// accFile,
						// fileDownloadFailReason);
						// }
						//
						// @Override
						// public void onProgress(
						// double already, double total) {
						// float progress = (float) (already * 1f / total);
						// // System.out
						// // .println("++++++++++++++");
						// // System.out.println(progress);
						// if (progress < 0
						// && (accFile
						// .getProgress() == null || accFile
						// .getProgress() < 0.99)) {
						// // progress =
						// // Math.max(progress, 0);
						// if (accFile.getProgress() == null) {
						// accFile.setProgress(0f);
						// }
						// progress = accFile
						// .getProgress() + 0.01f;
						// } else {
						// progress = Math.max(
						// progress, 0);
						// progress = Math.min(
						// progress, 1);
						// // this.currentProgress =
						// // progress;
						// }
						// accFile.setProgress(progress);
						// BitmapProviderManager.this
						// .excuteFileDownloadListener(
						// accFile,
						// already, total);
						// }
						// });
						if (bytes != null) {
							bitmap = BitmapFactory.decodeByteArray(bytes, 0,
									bytes.length);
						}
						if (bitmap != null && bitmap.getRowBytes() != 0) {
							FileUtil.saveMapBitmapFile(accFile.getLocalPath(),
									bitmap);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (bitmap == null) {
						bitmap = BitmapManager.getInstance(context)
								.getBlankBitmap();
					} else {
						BitmapProviderManager.this.softBitmapMap.put(accFile
								.getKey(), new SoftReference<Bitmap>(bitmap));
						BitmapProviderManager.this
								.excuteFileDownloadListener(accFile);
					}
				} catch (Exception e) {
					BitmapProviderManager.this.excuteFileDownloadListener(
							accFile, RequestFailReason.EXCEPTION);
					e.printStackTrace();
				} finally {
					StreamUtil.closeStream(inputStream);
					BitmapProviderManager.this.downloadingKey.remove(accFile
							.getKey());
				}
			}
		});
	}

	private void excuteFileDownloadListener(final ACCFile accFile,
			final Object... objects) {
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (objects != null) {
					if (objects.length == 0) {
						BitmapProviderManager.this.fileDownloadListener
								.onSuccess(accFile.getKey(), null);
						accFile.setProgress(null);
					} else if (objects.length == 1) {
						BitmapProviderManager.this.fileDownloadListener.onFail(
								accFile.getKey(),
								(RequestFailReason) objects[0]);
					} else if (objects.length == 2) {
						BitmapProviderManager.this.fileDownloadListener
								.onProgress(accFile.getKey(),
										(Double) objects[0],
										(Double) objects[1]);
					}
				}
			}
		});
	}

	public void onDestroy() {
		this.context = null;
		this.fileDownloadListener = null;
		this.fileDownloadCallback = null;
		this.downloadingKey.clear();
		this.downloadingKey = null;
		BitmapUtil.recycleBitmapMap(this.softBitmapMap);
		this.softBitmapMap = null;
		this.threadPool.shutdownNow();
	}
}