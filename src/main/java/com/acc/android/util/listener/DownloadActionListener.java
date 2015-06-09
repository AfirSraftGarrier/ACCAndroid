package com.acc.android.util.listener;


public interface DownloadActionListener {
	void onStart();

	void onBegin();

	void onLoading();

	void onSuspend();

	void onProgress();

	void onStop();

	void onFinish();
}