package com.acc.android.frame.util.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.acc.android.frame.manager.ToastManager;
import com.acc.android.frame.model.Response;

public abstract class UploadAsyncTask<T> extends AsyncTask<T, Void, Response> {
	private ProgressDialog progressDialog;
	private final Context context;
	private final String dataName;

	// private final UploadDataToNetAsyncTaskListener<T>
	// getDataFromNetAsyncTaskListener;

	public UploadAsyncTask(Context context, String dataName
	// ,
	// UploadDataToNetAsyncTaskListener<T> getDataFromNetAsyncTaskListener
	) {
		this.context = context;
		this.dataName = dataName;
		// this.getDataFromNetAsyncTaskListener =
		// getDataFromNetAsyncTaskListener;
	}

	@Override
	protected void onPostExecute(Response response) {
		super.onPostExecute(response);
		this.dismissProgressDialog();
		if (response.isSuccess()) {
			ToastManager.getInstance(context)
					.shortToast(this.dataName + "上报成功");
			this
			// .getDataFromNetAsyncTaskListener
			.onSuccess();
		} else {
			ToastManager.getInstance(context)
					.shortToast(this.dataName + "上报失败");
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
		// progressDialog.setMessage("正在初始化公共数据...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.prepareAndShowProgressDialog();
		this.showProgressString("正在上报" + this.dataName + "中...");
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