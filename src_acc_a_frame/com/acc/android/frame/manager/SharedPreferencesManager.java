package com.acc.android.frame.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {
	// private Context context;
	private final SharedPreferences sharedPreferences;

	private static SharedPreferencesManager instance;

	private SharedPreferencesManager(Context context) {
		// this.context = context;
		// super(context);
		// this.init();
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
	}

	public static SharedPreferencesManager getInstance(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesManager(context);
		}
		return instance;
	}

	// public void init() {
	// }

	// @Override
	// public void refresh() {
	// // this.init();
	// this.init(context);
	// }

	// public Context getContext() {
	// return context;
	// }

	// public String getServerUrl() {
	// // if (AppConstant.PROJECT_NAME == ProjectName.XIANG_XIANG_CHANGE) {
	// // return this.nameUrl;
	// // }
	// if (AppConstant.FOR_44) {
	// return this.nameUrl;
	// }
	// return serverUrl;
	// }
	//
	// public String getRealServerUrl() {
	// return serverUrl;
	// }

	// public String getRealServerUrl() {
	// return this.serverUrl;
	// }

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	//
	// public void setContext(Context context) {
	// this.context = context;
	// }

	// public void setServerUrl(String serverUrl) {
	// this.serverUrl = serverUrl;
	// this.setStrToSharedPreferences(LoginConstant.PRE_SERVER_URL, serverUrl);
	// }
	//
	// public void setSharedPreferences(SharedPreferences sharedPreferences) {
	// this.sharedPreferences = sharedPreferences;
	// }
	//
	// public String getNameUrl() {
	// return nameUrl;
	// }
	//
	// public void setNameUrl(String nameUrl) {
	// this.nameUrl = nameUrl;
	// this.setStrToSharedPreferences(LoginConstant.PRE_NAME_URL, nameUrl);
	// }
	//
	// public String getUserId() {
	// // System.out.println("useId:" + userId);
	// return userId;
	// }
	//
	// public void setUserId(String userId) {
	// this.userId = userId;
	// this.setStrToSharedPreferences(LoginConstant.PRE_USER_ID, userId);
	// }

	// @Override
	// public void init(Context context) {
	// this.sharedPreferences = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// this.serverUrl = this.getStrFromSharedPreferences(
	// LoginConstant.PRE_SERVER_URL, LoginConstant.DEFAULT_SERVER_URL);
	// this.nameUrl = this.getStrFromSharedPreferences(
	// LoginConstant.PRE_NAME_URL, LoginConstant.DEFAULT_NAME_URL);
	// this.userName = this.getStrFromSharedPreferences(
	// LoginConstant.PRE_USER_NAME, LoginConstant.DEFAULT_USER_NAME);
	// this.userId = this.getStrFromSharedPreferences(
	// LoginConstant.PRE_USER_ID, LoginConstant.DEFAULT_USER_ID);
	// this.password = this.getStrFromSharedPreferences(
	// LoginConstant.PRE_PASSWORD, LoginConstant.DEFAULT_PASSWORD);
	// this.remember = this.getBooleanFromSharedPreferences(
	// LoginConstant.PRE_REMEBER, LoginConstant.DEFAULT_REMEBER);
	// this.rememberUpdate = this.getBooleanFromSharedPreferences(
	// LoginConstant.PRE_REMEBER_UPDATE,
	// LoginConstant.DEFAULT_REMEMBER_UPDATE);
	// this.showGPSNotice = this.getBooleanFromSharedPreferences(
	// LoginConstant.PRE_REMEBER_GPS_NOTE,
	// LoginConstant.DEFAULT_REMEMBER_GPS_NOTE);
	// }

	public String getString(String key, String defaultValue) {
		return this.sharedPreferences.getString(key, defaultValue);
	}

	public boolean setString(String key, String value) {
		return this.sharedPreferences.edit().putString(key, value).commit();
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return this.sharedPreferences.getBoolean(key, defaultValue);
	}

	public boolean setBoolean(String key, boolean value) {
		return this.sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public int getInt(String key, int defaultValue) {
		return this.sharedPreferences.getInt(key, defaultValue);
	}

	public boolean setInt(String key, int value) {
		return this.sharedPreferences.edit().putInt(key, value).commit();
	}
	// public void setRemember(boolean remember) {
	// this.remember = remember;
	// this.setBooleanToSharedPreferences(LoginConstant.PRE_REMEBER, remember);
	// }
	//
	// public boolean isRemember() {
	// return remember;
	// }
	//
	// public void setUserName(String userName) {
	// this.userName = userName;
	// this.setStrToSharedPreferences(LoginConstant.PRE_USER_NAME, userName);
	// }
	//
	// public String getUserName() {
	// return userName;
	// }
	//
	// public void setPassword(String password) {
	// this.password = password;
	// this.setStrToSharedPreferences(LoginConstant.PRE_PASSWORD, password);
	// }
	//
	// public String getPassword() {
	// return password;
	// }
	//
	// public void setRememberUpdate(boolean rememberUpdate) {
	// this.rememberUpdate = rememberUpdate;
	// this.setBooleanToSharedPreferences(LoginConstant.PRE_REMEBER_UPDATE,
	// rememberUpdate);
	// }
	//
	// public boolean isRememberUpdate() {
	// return rememberUpdate;
	// }
	//
	// public boolean isShowGPSNotice() {
	// return showGPSNotice;
	// }
	//
	// public void setShowGPSNotice(boolean showGPSNotice) {
	// this.showGPSNotice = showGPSNotice;
	// this.setBooleanToSharedPreferences(LoginConstant.PRE_REMEBER_GPS_NOTE,
	// showGPSNotice);
	// }
}
