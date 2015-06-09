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
package com.acc.android.util.application;

import android.app.Application;

import com.baidu.mapapi.BMapManager;

public class BaseBaiduMapApplication extends Application {
	// private static BaiduMapApplication mInstance = null;
	// public boolean m_bKeyRight = true;
	private BMapManager bMapManager;

	// private List<Activity> activities = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		// this.bMapManager = new BMapManager(this);
		// this.bMapManager.init(null);
		this.makeSureInitBMapManager();
		// mInstance = this;
		// initEngineManager(this);
	}

	// @Override
	// public void onLowMemory() {
	// this.destroyBMap();
	// }

	@Override
	public void onTerminate() {
		super.onTerminate();
		this.destroyBMap();
	}

	private void destroyBMap() {
		if (this.bMapManager != null) {
			this.bMapManager.destroy();
			this.bMapManager = null;
		}
	}

	public BMapManager getBMapManager() {
		return this.makeSureInitBMapManager();
	}

	private BMapManager makeSureInitBMapManager() {
		if (this.bMapManager == null) {
			this.bMapManager = new BMapManager(this);
			this.bMapManager.init(null);
		}
		return this.bMapManager;
	}

	// public void initEngineManager(Context context) {
	// if (mBMapManager == null) {
	// mBMapManager = new BMapManager(context);
	// }
	//
	// if (!mBMapManager.init(new MyGeneralListener())) {
	// Toast.makeText(
	// BaiduMapApplication.getInstance().getApplicationContext(),
	// "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
	// }
	// }

	// public static BaiduMapApplication getInstance() {
	// return mInstance;
	// }

	// // 常用事件监听，用来处理通常的网络错误，授权验证错误等
	// public static class MyGeneralListener implements MKGeneralListener {
	//
	// @Override
	// public void onGetNetworkState(int iError) {
	// if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
	// Toast.makeText(
	// BaiduMapApplication.getInstance()
	// .getApplicationContext(), "您的网络出错啦！",
	// Toast.LENGTH_LONG).show();
	// } else if (iError == MKEvent.ERROR_NETWORK_DATA) {
	// Toast.makeText(
	// BaiduMapApplication.getInstance()
	// .getApplicationContext(), "输入正确的检索条件！",
	// Toast.LENGTH_LONG).show();
	// }
	// // ...
	// }
	//
	// @Override
	// public void onGetPermissionState(int iError) {
	// // 非零值表示key验证未通过
	// if (iError != 0) {
	// // 授权Key错误：
	// Toast.makeText(
	// BaiduMapApplication.getInstance()
	// .getApplicationContext(),
	// "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "
	// + iError, Toast.LENGTH_LONG).show();
	// BaiduMapApplication.getInstance().m_bKeyRight = false;
	// } else {
	// BaiduMapApplication.getInstance().m_bKeyRight = true;
	// Toast.makeText(
	// BaiduMapApplication.getInstance()
	// .getApplicationContext(), "key认证成功",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// }
	// public void addActivity(Activity activity) {
	// activities.add(activity);
	// }

	// 关闭百度地图
	// private void shutdownBMapManager() {
	// if (this.mBMapManager != null) {
	// this.mBMapManager.destroy();
	// this.mBMapManager = null;
	// }
	// }

	// @Override
	// public void onTerminate() {
	// super.onTerminate();
	// // for (Activity activity : activities) {
	// // activity.finish();
	// // }
	// // this.shutdownBMapManager();
	// // System.exit(0);
	// }
}