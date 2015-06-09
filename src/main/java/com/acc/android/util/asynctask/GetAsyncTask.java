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
package com.acc.android.util.asynctask;

import android.content.Context;

public abstract class GetAsyncTask<T, K> extends BasicAsyncTask<T, K> {
	// private ProgressDialog progressDialog;
	// private final Context context;
	// private final String dataName;
	// private final boolean isShowDialog;
	// private final boolean isCancleAble;

	// private final GetDataFromNetAsyncTaskListener<T, K>
	// getDataFromNetAsyncTaskListener;

	public GetAsyncTask(Context context, String dataName, boolean isShowDialog,
			boolean isCancelAble, boolean isShowSystemString
	// ,
	// GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
	) {
		// String loadingString = "";
		// String successToastString = "";
		// String failToastString = "";
		super(context, isShowDialog, isCancelAble, "正在获取" + dataName + "中...",
				dataName + "加载成功", dataName + "加载失败", isShowSystemString);
		// this.context = context;
		// this.dataName = dataName;
		// this.isShowDialog = isShowDialog;
		// this.isCancleAble = isCancelAble;
		// this.getDataFromNetAsyncTaskListener =
		// getDataFromNetAsyncTaskListener;
	}

	public GetAsyncTask(Context context, String dataName, boolean isShowDialog,
			boolean isCancelAble
	// ,
	// GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
	) {
		// String loadingString = "";
		// String successToastString = "";
		// String failToastString = "";
		this(context, dataName, isShowDialog, isCancelAble, true);
		// this.context = context;
		// this.dataName = dataName;
		// this.isShowDialog = isShowDialog;
		// this.isCancleAble = isCancelAble;
		// this.getDataFromNetAsyncTaskListener =
		// getDataFromNetAsyncTaskListener;
	}

	public GetAsyncTask(Context context, String dataName
	// ,
	// boolean isShowDialog
	// ,
	// GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
	) {
		this(context, dataName, true, true);
	}

	// public abstract void onSuccess(T t);
	//
	// @Override
	// public abstract void onFail();
	//
	// @Override
	// public abstract void onCancel();
	//
	// public abstract T getResult(K... params);

	// @Override
	// protected void onPostExecute(T t) {
	// super.onPostExecute(t);
	// if (this.isCancelled()) {
	// return;
	// }
	// if (t != null) {
	// ToastManager.getInstance(context)
	// .shortToast(this.dataName + "加载成功");
	// this
	// // .getDataFromNetAsyncTaskListener
	// .onSuccess(t);
	// } else {
	// ToastManager.getInstance(context)
	// .shortToast(this.dataName + "加载失败");
	// this
	// // .getDataFromNetAsyncTaskListener
	// .onFail();
	// }
	// if (this.isShowDialog) {
	// dismissProgressDialog();
	// }
	// }

	// @Override
	// public void cancel() {
	// if (!this.isCancleAble) {
	// return;
	// }
	// if (this.isShowDialog) {
	// dismissProgressDialog();
	// }
	// this.cancel(true);
	// this.onCancel();
	// ToastManager.getInstance(context).shortToast("已取消");
	// }

	// @Override
	// protected T doInBackground(K... params) {
	// return this
	// // .getDataFromNetAsyncTaskListener
	// .getResult(params);
	// }

	// private void showProgressString(String progressString) {
	// progressDialog.setMessage(progressString);
	// }
	//
	// private void dismissProgressDialog() {
	// try {
	// if (progressDialog != null && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// }
	// } catch (Exception exception) {
	// exception.printStackTrace();
	// }
	// }
	//
	// private void prepareAndShowProgressDialog() {
	// progressDialog = new ProgressDialog(this.context);
	// this.showProgressString("正在联网获取" + this.dataName + "中...");
	// // progressDialog.setMessage("正在初始化公共数据...");
	// progressDialog.setCancelable(this.isCancleAble);
	// if (this.isCancleAble) {
	// progressDialog.setOnCancelListener(new OnCancelListener() {
	//
	// @Override
	// public void onCancel(DialogInterface dialog) {
	// GetAsyncTask.this.cancel();
	// }
	// });
	// }
	// progressDialog.show();
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// if (this.isShowDialog) {
	// this.prepareAndShowProgressDialog();
	// }
	// }

	// public interface GetDataFromNetAsyncTaskListener<T, K> {
	// public T getResult(K... params);
	//
	// // public void onReceiveResult(T t);
	//
	// public void onSuccess(T t);
	//
	// public void onFail();
	// }
}