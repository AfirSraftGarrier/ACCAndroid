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
package com.acc.android.util.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.acc.android.manager.ToastManager;

public abstract class BasicAsyncTask<T, K> extends AsyncTask<K, Void, T> {
	private ProgressDialog progressDialog;
	protected final Context context;
	// private final String dataName;
	private final boolean isShowDialog;
	private final boolean isShowSystemToastString;
	private final boolean isCancleAble;
	private final String successToastString;
	private final String failToastString;
	private final String loadingString;

	// private final GetDataFromNetAsyncTaskListener<T, K>
	// getDataFromNetAsyncTaskListener;

	public BasicAsyncTask(Context context,
			// String dataName,
			boolean isShowDialog, boolean isCancelAble, String loadingString,
			String successToastString, String failToastString
	// ,
	// GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
	) {
		// this.context = context;
		// // this.dataName = dataName;
		// this.isShowDialog = isShowDialog;
		// this.isCancleAble = isCancelAble;
		// this.loadingString = loadingString;
		// this.successToastString = successToastString;
		// this.failToastString = failToastString;
		this(context,
				// String dataName,
				isShowDialog, isCancelAble, loadingString, successToastString,
				failToastString, true);
		// this.getDataFromNetAsyncTaskListener =
		// getDataFromNetAsyncTaskListener;
	}

	public BasicAsyncTask(
			Context context,
			// String dataName,
			boolean isShowDialog, boolean isCancelAble, String loadingString,
			String successToastString, String failToastString,
			boolean isShowSystemToastString
	// ,
	// GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
	) {
		this.context = context;
		// this.dataName = dataName;
		this.isShowDialog = isShowDialog;
		this.isCancleAble = isCancelAble;
		this.loadingString = loadingString;
		this.successToastString = successToastString;
		this.failToastString = failToastString;
		this.isShowSystemToastString = isShowSystemToastString;
		// this.getDataFromNetAsyncTaskListener =
		// getDataFromNetAsyncTaskListener;
	}

	// public BasicAsyncTask(Context context, String dataName, String
	// loadingString
	// // ,
	// // boolean isShowDialog
	// // ,
	// // GetDataFromNetAsyncTaskListener<T, K> getDataFromNetAsyncTaskListener
	// ) {
	// this(context, dataName, true, true, loadingString, null, null);
	// }

	public abstract void onSuccess(T t);

	public abstract void onFail();

	public abstract void onCancel();

	public abstract T getResult(K... params);

	@Override
	protected void onPostExecute(T t) {
		super.onPostExecute(t);
		if (this.isCancelled()) {
			return;
		}
		if (t != null) {
			if (this.successToastString != null && this.isShowSystemToastString) {
				ToastManager.getInstance(context).shortToast(
						this.successToastString);
			}
			this
			// .getDataFromNetAsyncTaskListener
			.onSuccess(t);
		} else {
			if (this.successToastString != null && this.isShowSystemToastString) {
				ToastManager.getInstance(context).shortToast(
						this.failToastString);
			}
			// ToastManager.getInstance(context).shortToast(this.failToastString);
			this
			// .getDataFromNetAsyncTaskListener
			.onFail();
		}
		if (this.isShowDialog) {
			dismissProgressDialog();
		}
	}

	public void cancel() {
		if (!this.isCancleAble) {
			return;
		}
		if (this.isShowDialog) {
			dismissProgressDialog();
		}
		this.cancel(true);
		this.onCancel();
		ToastManager.getInstance(context).shortToast("已取消");
	}

	@Override
	protected T doInBackground(K... params) {
		return this
		// .getDataFromNetAsyncTaskListener
				.getResult(params);
	}

	private void showProgressString(String progressString) {
		progressDialog.setMessage(progressString);
	}

	private void dismissProgressDialog() {
		try {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void prepareAndShowProgressDialog() {
		this.progressDialog = new ProgressDialog(this.context);
		// progressDialog.setMessage("正在初始化公共数据...");
		if (this.loadingString != null) {
			this.showProgressString(this.loadingString);
		}
		this.progressDialog.setCancelable(this.isCancleAble);
		if (this.isCancleAble) {
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					BasicAsyncTask.this.cancel();
				}
			});
		}
		progressDialog.show();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (this.isShowDialog) {
			this.prepareAndShowProgressDialog();
		}
	}

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