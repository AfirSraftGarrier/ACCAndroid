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
package com.acc.android.frame.manager.viewmanager;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acc.android.frame.util.LogUtil;
import com.acc.android.frame.util.ResourceUtil;
import com.acc.android.frame.util.listener.DownloadActionListener;
import com.acc.frame.util.ListUtil;
import com.acc.frame.util.StringUtil;

public class DownloadControllerManager implements OnClickListener,
		DownloadActionListener {
	private final View controllerView;
	private final View startView;
	private final View beginView;
	private final View loadingView;
	private final View suspendView;
	private final View stopView;
	private final View finishView;
	private final ProgressBar progressBar;
	private final TextView noticeView;
	private final int progressMax;
	private boolean isTrigger;
	private DownloadStatus currentDownloadStatus;
	private List<DownloadActionListener> downloadActionListeners;

	public DownloadStatus getCurrentDownloadStatus() {
		return this.currentDownloadStatus;
	}

	public void registDownloadActionListener(
			DownloadActionListener downloadActionListener) {
		// LogUtil.systemOut("regist");
		this.downloadActionListeners = ListUtil
				.makeSureInit(this.downloadActionListeners);
		this.downloadActionListeners.add(downloadActionListener);
		LogUtil.systemOut(this.downloadActionListeners.size());
	}

	public void unRegistDownloadActionListener(
			DownloadActionListener downloadActionListener) {
		if (!ListUtil.isEmpty(this.downloadActionListeners)) {
			this.downloadActionListeners.remove(downloadActionListener);
		}
	}

	public enum DownloadStatus {
		START, BEGIN, LOADING, PROGRESS, SUSPEND, STOP, FINISH
	}

	public View findViewById(int viewId) {
		return this.controllerView.findViewById(viewId);
	}

	public DownloadControllerManager(View controllerView, Context context) {
		String prefix = "download_";
		int startViewId = ResourceUtil.getId(context, prefix + "start");
		int beginViewId = ResourceUtil.getId(context, prefix + "begin");
		int loadingViewId = ResourceUtil.getId(context, prefix + "loading");
		int suspendViewId = ResourceUtil.getId(context, prefix + "suspend");
		int stopViewId = ResourceUtil.getId(context, prefix + "stop");
		int finishViewId = ResourceUtil.getId(context, prefix + "finish");
		int progressBarId = ResourceUtil
				.getId(context, prefix + "progress_bar");
		int noticeViweId = ResourceUtil.getId(context, prefix + "notice");
		this.controllerView = controllerView;
		this.startView = this.controllerView.findViewById(startViewId);
		this.beginView = this.controllerView.findViewById(beginViewId);
		this.loadingView = this.controllerView.findViewById(loadingViewId);
		this.suspendView = this.controllerView.findViewById(suspendViewId);
		this.stopView = this.controllerView.findViewById(stopViewId);
		this.finishView = this.controllerView.findViewById(finishViewId);
		this.noticeView = (TextView) this.controllerView
				.findViewById(noticeViweId);
		this.progressBar = (ProgressBar) this.controllerView
				.findViewById(progressBarId);
		this.progressMax = 10000;
		this.currentDownloadStatus = DownloadStatus.START;
		this.initViewAction();
		this.onStart();
	}

	public DownloadControllerManager(View controllerView, int progressBarId,
			int stopViewId, int beginViewId, int suspendViewId,
			int loadingViewId, int finishViewId, int noticeViweId,
			int startViewId) {
		this.controllerView = controllerView;
		this.progressBar = (ProgressBar) this.controllerView
				.findViewById(progressBarId);
		this.startView = this.controllerView.findViewById(startViewId);
		this.beginView = this.controllerView.findViewById(beginViewId);
		this.loadingView = this.controllerView.findViewById(loadingViewId);
		this.suspendView = this.controllerView.findViewById(suspendViewId);
		this.stopView = this.controllerView.findViewById(stopViewId);
		this.finishView = this.controllerView.findViewById(finishViewId);
		this.noticeView = (TextView) this.controllerView
				.findViewById(noticeViweId);
		this.progressMax = 10000;
		this.currentDownloadStatus = DownloadStatus.START;
		this.initViewAction();
		this.onStart();
	}

	public void enableTrigger() {
		this.isTrigger = true;
	}

	public void publishProgress(int already, int total) {
		int progress = already * this.progressMax / total;
		if (progress == this.progressMax) {
			this.onFinish();
			return;
		}
		this.progressBar.setProgress(progress);
		this.noticeView.setText(this.getTitleText(already, total, progress));
		this.onBegin();
	}

	private String getTitleText(int already, int total, int progress) {
		return StringUtil.getString("" + already, "/", "" + total, "(", ""
				+ (progress / 100), "." + getNumber(progress % 100), "%)");
	}

	private String getNumber(int progressNum) {
		if (progressNum < 10) {
			return "0" + progressNum;
		}
		return "" + progressNum;
	}

	private void initViewAction() {
		this.isTrigger = false;
		this.progressBar.setMax(this.progressMax);
		this.progressBar.setProgress(0);
		this.startView.setOnClickListener(this);
		this.beginView.setOnClickListener(this);
		this.loadingView.setOnClickListener(this);
		this.suspendView.setOnClickListener(this);
		this.stopView.setOnClickListener(this);
		this.finishView.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == this.startView) {
			this.onLoading();
		} else if (view == this.beginView) {
			this.onLoading();
		} else if (view == this.loadingView) {
			this.onBegin();
		} else if (view == this.suspendView) {
			this.onSuspend();
		} else if (view == this.stopView) {
			this.onStop();
		} else if (view == this.progressBar) {
		} else if (view == this.finishView) {
			if (!this.isTrigger) {
				return;
			}
			this.onFinish();
		}
	}

	public static void actionListener(DownloadStatus currentDownloadStatus,
			DownloadActionListener downloadActionListener) {
		switch (currentDownloadStatus) {
		case START:
			downloadActionListener.onStart();
			break;
		case BEGIN:
			downloadActionListener.onBegin();
			break;
		case LOADING:
			// LogUtil.systemOut("actionListener222222222");
			downloadActionListener.onLoading();
			break;
		case PROGRESS:
			downloadActionListener.onProgress();
			break;
		case SUSPEND:
			downloadActionListener.onSuspend();
			break;
		case STOP:
			downloadActionListener.onStop();
			break;
		case FINISH:
			downloadActionListener.onFinish();
			break;
		}
	}

	public void actionListener() {
		// System.out.println("actionListener");
		// LogUtil.systemOut("actionListener");
		if (!ListUtil.isEmpty(this.downloadActionListeners)) {
			// LogUtil.systemOut("actionListener111");
			for (DownloadActionListener downloadActionListener : this.downloadActionListeners) {
				actionListener(currentDownloadStatus, downloadActionListener);
				// switch (this.currentDownloadStatus) {
				// case START:
				// downloadActionListener.onStart();
				// break;
				// case BEGIN:
				// downloadActionListener.onBegin();
				// break;
				// case LOADING:
				// // LogUtil.systemOut("actionListener222222222");
				// downloadActionListener.onLoading();
				// break;
				// case PROGRESS:
				// downloadActionListener.onProgress();
				// break;
				// case SUSPEND:
				// downloadActionListener.onSuspend();
				// break;
				// case STOP:
				// downloadActionListener.onStop();
				// break;
				// case FINISH:
				// downloadActionListener.onFinish();
				// break;
				// }
			}
		}
	}

	@Override
	public void onStart() {
		this.currentDownloadStatus = DownloadStatus.START;
		this.startView.setVisibility(View.VISIBLE);
		this.beginView.setVisibility(View.GONE);
		this.loadingView.setVisibility(View.GONE);
		this.suspendView.setVisibility(View.GONE);
		this.stopView.setVisibility(View.GONE);
		this.finishView.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.GONE);
		this.noticeView.setVisibility(View.GONE);
		this.actionListener();
	}

	@Override
	public void onBegin() {
		this.currentDownloadStatus = DownloadStatus.BEGIN;
		this.startView.setVisibility(View.GONE);
		this.beginView.setVisibility(View.VISIBLE);
		this.suspendView.setVisibility(View.GONE);
		this.loadingView.setVisibility(View.GONE);
		this.stopView.setVisibility(View.VISIBLE);
		this.finishView.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.VISIBLE);
		this.noticeView.setVisibility(View.VISIBLE);
		this.noticeView.setText("待开始...");
		this.actionListener();
	}

	@Override
	public void onLoading() {
		LogUtil.systemOut("onLoading");
		this.currentDownloadStatus = DownloadStatus.LOADING;
		this.startView.setVisibility(View.GONE);
		this.beginView.setVisibility(View.GONE);
		this.suspendView.setVisibility(View.GONE);
		this.loadingView.setVisibility(View.VISIBLE);
		this.stopView.setVisibility(View.VISIBLE);
		this.finishView.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.VISIBLE);
		this.noticeView.setVisibility(View.VISIBLE);
		this.noticeView.setText("计算中...");
		this.actionListener();
	}

	@Override
	public void onSuspend() {
		this.currentDownloadStatus = DownloadStatus.SUSPEND;
		this.startView.setVisibility(View.GONE);
		this.beginView.setVisibility(View.VISIBLE);
		this.suspendView.setVisibility(View.GONE);
		this.loadingView.setVisibility(View.GONE);
		this.stopView.setVisibility(View.VISIBLE);
		this.finishView.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.VISIBLE);
		this.noticeView.setVisibility(View.VISIBLE);
		this.noticeView.setText("暂停中...");
		this.actionListener();
	}

	@Override
	public void onProgress() {
		this.currentDownloadStatus = DownloadStatus.PROGRESS;
		this.startView.setVisibility(View.GONE);
		this.beginView.setVisibility(View.GONE);
		this.suspendView.setVisibility(View.VISIBLE);
		this.loadingView.setVisibility(View.GONE);
		this.stopView.setVisibility(View.VISIBLE);
		this.finishView.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.VISIBLE);
		this.noticeView.setVisibility(View.VISIBLE);
		this.actionListener();
	}

	@Override
	public void onStop() {
		this.currentDownloadStatus = DownloadStatus.STOP;
		this.startView.setVisibility(View.GONE);
		this.beginView.setVisibility(View.GONE);
		this.suspendView.setVisibility(View.GONE);
		this.loadingView.setVisibility(View.GONE);
		this.stopView.setVisibility(View.GONE);
		this.finishView.setVisibility(View.VISIBLE);
		this.progressBar.setVisibility(View.GONE);
		this.noticeView.setVisibility(View.GONE);
		this.actionListener();
	}

	@Override
	public void onFinish() {
		if (!this.isTrigger) {
			return;
		}
		this.currentDownloadStatus = DownloadStatus.FINISH;
		this.startView.setVisibility(View.VISIBLE);
		this.beginView.setVisibility(View.GONE);
		this.suspendView.setVisibility(View.GONE);
		this.loadingView.setVisibility(View.GONE);
		this.stopView.setVisibility(View.GONE);
		this.finishView.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.GONE);
		this.noticeView.setVisibility(View.GONE);
		this.actionListener();
	}

	// public void begin() {
	// this.startView.setVisibility(View.GONE);
	// this.finishView.setVisibility(View.GONE);
	// this.beginView.setVisibility(View.GONE);
	// this.stopView.setVisibility(View.VISIBLE);
	// this.suspendView.setVisibility(View.VISIBLE);
	// this.loadingView.setVisibility(View.VISIBLE);
	// this.progressBar.setVisibility(View.VISIBLE);
	// this.noticeView.setVisibility(View.VISIBLE);
	// this.noticeView.setText("已开始...");
	// }
	//
	// public void suspend() {
	// this.startView.setVisibility(View.GONE);
	// this.finishView.setVisibility(View.GONE);
	// this.suspendView.setVisibility(View.GONE);
	// this.beginView.setVisibility(View.VISIBLE);
	// this.stopView.setVisibility(View.VISIBLE);
	// this.progressBar.setVisibility(View.VISIBLE);
	// this.noticeView.setVisibility(View.VISIBLE);
	// this.noticeView.setText("暂停中...");
	// }
	//
	// public void stop() {
	// this.backToStart();
	// // this.finishView.setVisibility(View.GONE);
	// // this.beginView.setVisibility(View.GONE);
	// // this.stopView.setVisibility(View.GONE);
	// // this.suspendView.setVisibility(View.GONE);
	// // this.progressBar.setVisibility(View.GONE);
	// // if (this.isEnableFinishViewAction) {
	// // this.finishView.setVisibility(View.VISIBLE);
	// // this.noticeView.setVisibility(View.GONE);
	// // } else {
	// // this.noticeView.setVisibility(View.VISIBLE);
	// // this.noticeView.setText("已停止...");
	// // }
	// }

	// public void finish() {
	// this.beginView.setVisibility(View.GONE);
	// this.stopView.setVisibility(View.GONE);
	// this.suspendView.setVisibility(View.GONE);
	// this.progressBar.setVisibility(View.GONE);
	// // this.finishView.setVisibility(View.GONE);
	// if (this.isTrigger) {
	// this.noticeView.setVisibility(View.GONE);
	// this.finishView.setVisibility(View.VISIBLE);
	// } else {
	// this.finishView.setVisibility(View.GONE);
	// this.noticeView.setVisibility(View.VISIBLE);
	// this.noticeView.setText("暂停中...");
	// }
	// // if (this.isEnableFinishViewAction) {
	// // this.finishView.setVisibility(View.VISIBLE);
	// // this.noticeView.setVisibility(View.GONE);
	// // } else {
	// // this.noticeView.setVisibility(View.VISIBLE);
	// // this.noticeView.setText("已完成...");
	// // }
	// }
	//
	// public void backToStart() {
	// if (!this.isTrigger) {
	// return;
	// }
	// this.startView.setVisibility(View.VISIBLE);
	// this.finishView.setVisibility(View.GONE);
	// this.beginView.setVisibility(View.GONE);
	// this.stopView.setVisibility(View.GONE);
	// this.suspendView.setVisibility(View.GONE);
	// this.progressBar.setVisibility(View.GONE);
	// this.noticeView.setVisibility(View.GONE);
	// this.loadingView.setVisibility(View.GONE);
	// }

}