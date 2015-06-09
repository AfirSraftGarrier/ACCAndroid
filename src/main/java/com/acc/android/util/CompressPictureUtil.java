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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

public class CompressPictureUtil {
	public static final boolean useOld = false;

	public interface OnCompressPictureOverListener {
		void onCompressPictureOver(String filePath);
	}

	public static void startAsyAsyncTaskOrNot(final Context context,
			final String filePath,
			final OnCompressPictureOverListener onCompressPictureOverListener) {
		startAsyAsyncTaskOrNot(context, filePath, filePath,
				onCompressPictureOverListener);
	}

	public static void startAsyAsyncTaskOrNot(final Context context,
			final String filePath, final String resultPath,
			final OnCompressPictureOverListener onCompressPictureOverListener) {
		// CommonInitDataUtil.initSetData(context);
		try {
			// int fileAvalible = new FileInputStream(new File(filePath))
			// .available();
			// // System.out.println("VVVVVVVVVVVVVv" + fileAvalible);
			// // System.out.println("VVVVVVVVVVVVVv" + filePath);
			// // new FileInputStream(filename)
			// int fileSizeInM = fileAvalible * 100 / 1048576;
			// double fileSize = fileSizeInM / 100.0d;
			if (
			// fileAvalible > 1048576
			true) {
				// new AlertDialog.Builder(context)
				// .setMessage(
				// "图片大小为" + fileSize + "M"
				// + ",为节省你的流量，建议进行图片压缩，是否要进行图片压缩？")
				// .setPositiveButton("是",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// finish();
				// if(AppConstant.FOR_BDP) {
				// System.out.println("VVVVVV");
				// System.out.println(filePath);
				// System.out.println(resultPath);
				new CompressPictureUtil.CompressPictureAsyncTask(context,
						filePath, resultPath, onCompressPictureOverListener)
						.execute();
				// }
				// ProblemUpload.this
				// .addPhotoAndUpdateImageAdapter();
				// }
				// })
				// .setNegativeButton("否",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// // finish();
				// onCompressPictureOverListener
				// .onCompressPictureOver();
				// }
				// }).show();
				// // }
				// Dialog dialog = new
				// AlertDialog.Builder(ProblemUpload.this)
				// // .setTitle("软件更新")
				// .setMessage("图片过大，建议进行图片压缩，是否要进行图片压缩？")// 设置内容
				// .setPositiveButton("是",// 设置确定按钮
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(
				// DialogInterface dialog,
				// int which) {
				// // finish();
				// }
				// }).setNegativeButton("是", new
				// DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(
				// DialogInterface dialog,
				// int which) {
				// // finish();
				// }).create();// 创建
				// // 显示对话框
				// dialog.show();
			} else {
				onCompressPictureOverListener.onCompressPictureOver(resultPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class CompressPictureAsyncTask extends
			AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;
		private final Context context;
		private final String filePath;
		private final String resultPath;
		private final OnCompressPictureOverListener onCompressPictureOverListener;

		public CompressPictureAsyncTask(Context context, String filePath,
				String resultPath,
				OnCompressPictureOverListener onCompressPictureOverListener) {
			this.context = context;
			this.filePath = filePath;
			this.resultPath = resultPath;
			// System.out.println("CCCCCCCCCCCCCC");
			// System.out.println(this.filePath);
			// System.out.println(this.resultPath);
			this.onCompressPictureOverListener = onCompressPictureOverListener;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Toast.makeText(this.context, "压缩成功", Toast.LENGTH_SHORT).show();
			dialog.dismiss();
			// this.printFileSize(this.filePath);
			this.onCompressPictureOverListener
					.onCompressPictureOver(this.resultPath);
		}

		private void printFileSize(String filePath) {
			try {
				// int fileAvalible = new FileInputStream(new File(filePath))
				// .available();
				int fileAvalible = new FileInputStream(new File(filePath))
						.available();
				int fileSizeInM = fileAvalible * 100 / 1048576;
				double fileSize = fileSizeInM / 100.0d;
				String fileSizeStr = "" + fileSize + "M";
				if (fileSize < 1) {
					int fileSizeInK = fileAvalible * 100 / 1024;
					fileSize = fileSizeInK / 100.0d;
					fileSizeStr = "" + fileSize + "K";
				}
				// System.out.println("11111111111111now" + fileSizeStr);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch
				// block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch
				// block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(this.context);
			dialog.setMessage("压缩图片中，请稍后...");
			dialog.setCancelable(false);
			// dialog.set
			// dialog.setButton("取消", new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int whichButton) {
			// dialog.dismiss();
			// }
			// });
			dialog.setIndeterminate(true);
			dialog.show();
		}

		// @Override
		// protected Void doInBackground(Void... params) {
		// try {
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;
		// }

		@Override
		protected Void doInBackground(Void... str) {
			// // TODO Auto-generated method stub
			// System.out.println("+++");
			// this.printFileSize(this.filePath);
			// System.out.println("REAL");
			try {
				// String filePath = str[0];
				if (!CompressPictureUtil.useOld) {
					// Bitmap rawBitmap =
					// BitmapFactory.decodeFile(this.filePath,
					// null);
					// if (rawBitmap != null) {
					// BitmapFactory.Options options = new
					// BitmapFactory.Options();
					// options.inJustDecodeBounds = true;
					// BitmapFactory.decodeFile(filePath, options);
					// int height = options.outHeight;
					// int width = options.outWidth;
					// int inSampleSize = 1;
					// int reqHeight = 800;
					// int reqWidth = 480;
					// if (height > reqHeight || width > reqWidth) {
					// final int heightRatio = Math.round((float) height
					// / (float) reqHeight);
					// final int widthRatio = Math.round((float) width
					// / (float) reqWidth);
					// inSampleSize = heightRatio < widthRatio ? heightRatio
					// : widthRatio;
					// }
					// 在内存中创建bitmap对象，这个对象按照缩放大小创建的
					// options.inSampleSize =
					// CompressUtil.calculateInSampleSize(
					// options, 480, 800);
					// options.inJustDecodeBounds = false;
					// Bitmap bitmap = BitmapFactory.decodeFile(filePath,
					// options);
					// System.out.println("VVVVVVVVVVVVVV");
					// System.out.println(this.filePath);
					// System.out.println(this.resultPath);
					if (!this.resultPath.equals(this.filePath)) {
						// System.out.println(this.filePath);
						// System.out.println(this.resultPath);
						FileUtil.copyFile(this.filePath, this.resultPath);
					}
					Bitmap bitmap = BitmapUtil.getSmallBitmap(this.filePath);
					FileOutputStream out = new FileOutputStream(this.resultPath);
					// System.out.println("XXXXXXXXXXXVVVVVVVVVVV123VVVVVVV");
					// System.out.println(this.filePath);
					// System.out.println(this.resultPath);
					// System.out.println(this.resultPath);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);
					// Bitmap rawBitmap = CompressUtil
					// .getSmallBitmap(this.filePath);
					// FileOutputStream out = new
					// FileOutputStream(this.filePath);
					// // System.out.println("filePath:" + this.filePath);
					// rawBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
					// }
				} else {
					Bitmap rawBitmap = BitmapFactory.decodeFile(this.filePath,
							null);
					if (rawBitmap != null) {
						rawBitmap = comp(rawBitmap);
						FileOutputStream out = new FileOutputStream(
								this.filePath);
						// System.out.println("filePath:" + this.filePath);
						rawBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
					}
				}
			} catch (Exception exception) {
			}
			return null;
		}
	}

	private static Bitmap comp(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// if (baos.toByteArray().length / 1024 > 1024) {//
		// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		// baos.reset();// 重置baos即清空baos
		// image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//
		// 这里压缩50%，把压缩后的数据存放到baos中
		// }
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		long size =
		// CommonInitDataUtil.setData != null ? CommonInitDataUtil.setData
		// .getCompressPictureSize() :
		defaultSize;
		while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	private static int defaultSize = 14;
}