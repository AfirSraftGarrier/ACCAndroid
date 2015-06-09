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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.acc.android.manager.ToastManager;
import com.acc.java.model.Response;
import com.acc.java.model.ResponseStatus;

public abstract class UploadAsyncTask<T> extends AsyncTask<T, Void, Response> {
	private ProgressDialog progressDialog;
	private final Context context;
	private final String dataName;
	private final String actionName;

	// private final UploadDataToNetAsyncTaskListener<T>
	// getDataFromNetAsyncTaskListener;

	public UploadAsyncTask(Context context, String dataName, String actionName
	// ,
	// UploadDataToNetAsyncTaskListener<T> getDataFromNetAsyncTaskListener
	) {
		this.context = context;
		this.dataName = dataName;
		this.actionName = actionName == null ? "涓婃姤" : actionName;
		// this.getDataFromNetAsyncTaskListener =
		// getDataFromNetAsyncTaskListener;
	}

	@Override
	protected void onPostExecute(Response response) {
		super.onPostExecute(response);
		this.dismissProgressDialog();
		if (response.getResponseStatus() == ResponseStatus.SUCCESS) {
			ToastManager.getInstance(context).shortToast(
					this.dataName + actionName + "鎴愬姛");
			this
			// .getDataFromNetAsyncTaskListener
			.onSuccess();
		} else {
			ToastManager.getInstance(context).shortToast(
					this.dataName + actionName + "澶辫触");
			this
			// .getDataFromNetAsyncTaskListener
			.onFail();
		}
	}

	@Override
	protected Response doInBackground(T... params) {
		return this
		// .getDataFromNetAsyncTaskListener
				.doUpload(params);
	}

	private void showProgressString(String progressString) {
		progressDialog.setMessage(progressString);
	}

	private void dismissProgressDialog() {
		progressDialog.dismiss();
	}

	private void prepareAndShowProgressDialog() {
		progressDialog = new ProgressDialog(this.context);
		// progressDialog.setMessage("姝ｅ湪鍒濆鍖栧叕鍏辨暟鎹�...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.prepareAndShowProgressDialog();
		this.showProgressString("姝ｅ湪" + this.actionName + this.dataName + "涓�...");
	}

	public abstract Response doUpload(T... params);

	public abstract void onSuccess();

	public abstract void onFail();

	// public interface UploadDataToNetAsyncTaskListener<T> {
	// public Response doUpload(T... params);
	//
	// public void onSuccess();
	//
	// public void onFail();
	// }
}