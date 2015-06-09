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
package com.acc.android.frame.util;

import android.util.Log;

import com.acc.android.frame.manager.JsonManager;
import com.acc.frame.util.base.BaseLogUtil;
import com.acc.frame.util.constant.AppLibConstant;

public class LogUtil extends BaseLogUtil {

	public static String getLogString(Object informationObject) {
		return getLogString(null, informationObject, JsonManager.getInstance());
	}

	public static String getLogString(Object prefixObject,
			Object informationObject) {
		return getLogString(prefixObject, informationObject,
				JsonManager.getInstance());
	}

	private static void log(Object tagObject, Object prefixObject,
			Object informationObject, int logType) {
		if (!AppLibConstant.isUseLog() || informationObject == null) {
			return;
		}
		JsonManager jsonManager = JsonManager.getInstance();
		String tagString = logType == com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_SYSTEMOUT
				|| logType == com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_FILE ? null
				: getTagString(tagObject, jsonManager);
		String logString = getLogString(prefixObject, informationObject);
		switch (logType) {
		case com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_SYSTEMOUT:
			BaseLogUtil.systemOut(logString);
			break;
		case com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_FILE:
			BaseLogUtil.fileOut(logString);
			break;
		case Log.VERBOSE:
			Log.v(tagString, logString);
			break;
		case Log.DEBUG:
			Log.d(tagString, logString);
			break;
		case Log.INFO:
			Log.i(tagString, logString);
			break;
		case Log.WARN:
			Log.w(tagString, logString);
			break;
		case Log.ERROR:
			Log.e(tagString, logString);
			break;
		}
	}

	public static void systemOut(Object informationObject) {
		log(null,
				null,
				informationObject,
				com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_SYSTEMOUT);
	}

	public static void systemOut(Object prefixObject, Object informationObject) {
		log(null,
				prefixObject,
				informationObject,
				com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_SYSTEMOUT);
	}

	public static void fileOut(Object informationObject) {
		log(null,
				null,
				informationObject,
				com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_FILE);
	}

	public static void fileOut(Object prefixObject, Object informationObject) {
		log(null,
				prefixObject,
				informationObject,
				com.acc.android.frame.util.constant.ACCALibConstant.LOG_TYPE_FILE);
	}

	public static void verbose(Object informationObject) {
		log(null, null, informationObject, Log.VERBOSE);
	}

	public static void verbose(Object tagObject, Object informationObject) {
		log(tagObject, null, informationObject, Log.VERBOSE);
	}

	public static void verbose(Object tagObject, Object prefixObject,
			Object informationObject) {
		log(tagObject, prefixObject, informationObject, Log.VERBOSE);
	}

	public static void debug(Object informationObject) {
		log(null, null, informationObject, Log.DEBUG);
	}

	public static void debug(Object tagObject, Object informationObject) {
		log(tagObject, null, informationObject, Log.DEBUG);
	}

	public static void debug(Object tagObject, Object prefixObject,
			Object informationObject) {
		log(tagObject, prefixObject, informationObject, Log.DEBUG);
	}

	public static void info(Object informationObject) {
		log(null, null, informationObject, Log.INFO);
	}

	public static void info(Object tagObject, Object informationObject) {
		log(tagObject, null, informationObject, Log.INFO);
	}

	public static void info(Object tagObject, Object prefixObject,
			Object informationObject) {
		log(tagObject, prefixObject, informationObject, Log.INFO);
	}

	public static void warn(Object informationObject) {
		log(null, null, informationObject, Log.WARN);
	}

	public static void warn(Object tagObject, Object informationObject) {
		log(tagObject, null, informationObject, Log.WARN);
	}

	public static void warn(Object tagObject, Object prefixObject,
			Object informationObject) {
		log(tagObject, prefixObject, informationObject, Log.WARN);
	}

	public static void error(Object informationObject) {
		log(null, null, informationObject, Log.ERROR);
	}

	public static void error(Object tagObject, Object informationObject) {
		log(tagObject, null, informationObject, Log.ERROR);
	}

	public static void error(Object tagObject, Object prefixObject,
			Object informationObject) {
		log(tagObject, prefixObject, informationObject, Log.ERROR);
	}

	// public static String getLogString(Object informationObject) {
	// return getLogString(null, informationObject, JsonManager.getInstance());
	// }

	// public static void debug(Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// Log.d(getTagString(null, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void debug(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// Log.d(getTagString(tagObject, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void debug(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// Log.d(getTagString(tagObject, jsonManager),
	// getLogString(prefixObject, informationObject, jsonManager));
	// }
	//
	// public static void info(Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// Log.i(getTagString(null, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void info(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// Log.i(getTagString(tagObject, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void info(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// Log.i(getTagString(tagObject, jsonManager),
	// getLogString(prefixObject, informationObject, jsonManager));
	// }
	//
	// public static void warn(Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// warn(getTagString(null, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void warn(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// warn(getTagString(tagObject, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void warn(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// warn(getTagString(tagObject, jsonManager),
	// getLogString(prefixObject, informationObject, jsonManager));
	// }
	//
	// private static void warn(String tagString, String logString) {
	// Log.w(tagString, logString);
	// }
	//
	// public static void error(Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// error(getTagString(null, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void error(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// error(getTagString(tagObject, jsonManager),
	// getLogString(null, informationObject, jsonManager));
	// }
	//
	// public static void error(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// JsonManager jsonManager = JsonManager.getInstance();
	// error(getTagString(tagObject, jsonManager),
	// getLogString(prefixObject, informationObject, jsonManager));
	// }
	//
	// private static void error(String tagString, String logString) {
	// Log.e(tagString, logString);
	// }

	// public static boolean LOG = false;
	//
	// public void enableLog() {
	// LOG = true;
	// }
	//
	// public void disableLog() {
	// LOG = false;
	// }

	// static Log log;

	// private static String getStringFromObject(Object object) {
	// return JSONManager.getInstance(null).getJson(object);
	// }

	// private static String getStringFromObject(Object object) {
	// String resultString;
	// if (object instanceof String) {
	// resultString = (String) object;
	// } else {
	// try {
	// resultString = JsonManager.getInstance(null).getJson(object);
	// } catch (Exception e) {
	// resultString = "该类不符合规范，尝试传字符串类型";
	// }
	// }
	// return resultString;
	// }

	// public static void info(Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// info(this.getLogger(classType),
	// this.getLogString(informationObject, this.jsonManager));
	// }

	// public static void info(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// info(tagObject,
	// getLogString(tagObject, null, informationObject,
	// JsonManager.getInstance()));
	// }
	//
	// public static void info(Object tagObject, String informationPrefixString,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// info(tagObject,
	// getLogString(tagObject, informationPrefixString,
	// informationObject, JsonManager.getInstance()));
	// }
	//
	// private static void info(Object tagObject, String logString) {
	// info(tagObject.getClass().toString(), logString);
	// }
	//
	// private static void info(String tagString, String logString) {
	// Log.i(tagString, logString);
	// }

	// public static void warn(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// warn(tagObject,
	// getLogString(informationObject, JsonManager.getInstance()));
	// }
	//
	// public static void warn(Object tagObject, String informationPrefixString,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// warn(tagObject,
	// getLogString(tagObject, informationPrefixString,
	// informationObject, JsonManager.getInstance()));
	// }
	//
	// private static void warn(Object tagObject, String logString) {
	// warn(tagObject.getClass().toString(), logString);
	// }
	//
	// private static void warn(String tagString, String logString) {
	// Log.w(tagString, logString);
	// }

	// public static void warn(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// warn(tagObject,
	// getLogString(informationObject, JsonManager.getInstance()));
	// }
	//
	// public static void warn(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// warn(tagObject, getLogString(
	// // tagObject,
	// prefixObject, informationObject, JsonManager.getInstance()));
	// }
	//
	// private static void warn(Object tagObject, String logString) {
	// // verbose(tagObject.getClass().toString(), logString);
	// Log.w(getTagString(tagObject, JsonManager.getInstance()), logString);
	// }
	//
	// // public static void error(Object tagObject, Object informationObject) {
	// // if (!AppLibConstant.isUseLog()) {
	// // return;
	// // }
	// // error(tagObject,
	// // getLogString(informationObject, JsonManager.getInstance()));
	// // }
	//
	// // public static void error(Object tagObject, String
	// // informationPrefixString,
	// // Object informationObject) {
	// // if (!AppLibConstant.isUseLog()) {
	// // return;
	// // }
	// // error(tagObject,
	// // getLogString(tagObject, informationPrefixString,
	// // informationObject, JsonManager.getInstance()));
	// // }
	//
	// // private static void error(Object tagObject, String logString) {
	// // error(tagObject.getClass().toString(), logString);
	// // }
	//
	// // private static void error(String tagString, String logString) {
	// // Log.e(tagString, logString);
	// // }
	//
	// public static void error(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// error(tagObject,
	// getLogString(informationObject, JsonManager.getInstance()));
	// }
	//
	// public static void error(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// error(tagObject, getLogString(
	// // tagObject,
	// prefixObject, informationObject, JsonManager.getInstance()));
	// }
	//
	// private static void error(Object tagObject, String logString) {
	// // verbose(tagObject.getClass().toString(), logString);
	// Log.e(getTagString(tagObject, JsonManager.getInstance()), logString);
	// }
	//
	// // public static void debug(Object tagObject, Object informationObject) {
	// // if (!AppLibConstant.isUseLog()) {
	// // return;
	// // }
	// // debug(tagObject,
	// // getLogString(informationObject, JsonManager.getInstance()));
	// // }
	//
	// // public static void debug(Object tagObject, String
	// // informationPrefixString,
	// // Object informationObject) {
	// // if (!AppLibConstant.isUseLog()) {
	// // return;
	// // }
	// // debug(tagObject,
	// // getLogString(tagObject, informationPrefixString,
	// // informationObject, JsonManager.getInstance()));
	// // }
	//
	// // private static void debug(Object tagObject, String logString) {
	// // debug(tagObject.getClass().toString(), logString);
	// // }
	//
	// // private static void debug(String tagString, String logString) {
	// // Log.d(tagString, logString);
	// // }
	//
	// public static void debug(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// debug(tagObject,
	// getLogString(informationObject, JsonManager.getInstance()));
	// }
	//
	// public static void debug(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// debug(tagObject, getLogString(
	// // tagObject,
	// prefixObject, informationObject, JsonManager.getInstance()));
	// }
	//
	// private static void debug(Object tagObject, String logString) {
	// // verbose(tagObject.getClass().toString(), logString);
	// Log.d(getTagString(tagObject, JsonManager.getInstance()), logString);
	// }
	//
	// public static void verbose(Object tagObject, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// verbose(tagObject,
	// getLogString(informationObject, JsonManager.getInstance()));
	// }
	//
	// public static void verbose(Object tagObject, Object prefixObject,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// verbose(tagObject, getLogString(
	// // tagObject,
	// prefixObject, informationObject, JsonManager.getInstance()));
	// }
	//
	// private static void verbose(Object tagObject, String logString) {
	// // verbose(tagObject.getClass().toString(), logString);
	// Log.v(getTagString(tagObject, JsonManager.getInstance()), logString);
	// }

	// private static void verbose(String tagString, String logString) {
	// Log.v(tagString, logString);
	// }

	// public static void info(Object tagObject, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.i((String) tagObject, informationString);
	// } else {
	// Log.i("" + tagObject.getClass(), informationString);
	// }
	// }

	// public static void warning(Object tagObject, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.w((String) tagObject, informationString);
	// } else {
	// Log.w("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void error(Object tagObject, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.e((String) tagObject, informationString);
	// } else {
	// Log.e("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void debug(Object tagObject, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.d((String) tagObject, informationString);
	// } else {
	// Log.d("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void verbose(Object tagObject, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.v((String) tagObject, informationString);
	// } else {
	// Log.v("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// // public static void info(Object tagObject, String
	// informationPrefixString,
	// // Object informationObject) {
	// // // if (!AppConstant.LOG) {
	// // // return;
	// // // }
	// // String informationString = informationPrefixString
	// // + getStringFromObject(informationObject);
	// // if (tagObject instanceof String) {
	// // Log.i((String) tagObject, informationString);
	// // } else {
	// // Log.i("" + tagObject.getClass(), informationString);
	// // }
	// // }
	//
	// public static void warning(Object tagObject,
	// String informationPrefixString, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = informationPrefixString
	// + getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.w((String) tagObject, informationString);
	// } else {
	// Log.w("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void error(Object tagObject, String
	// informationPrefixString,
	// Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = informationPrefixString
	// + getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.e((String) tagObject, informationString);
	// } else {
	// Log.e("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void debug(Object tagObject, String
	// informationPrefixString,
	// Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = informationPrefixString
	// + getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.d((String) tagObject, informationString);
	// } else {
	// Log.d("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void verbose(Object tagObject,
	// String informationPrefixString, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = informationPrefixString
	// + getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// Log.v((String) tagObject, informationString);
	// } else {
	// Log.v("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void systemOut(Object tagObject, Object informationObject)
	// {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// System.out.println(((String) tagObject) + "--" + informationString);
	// // ;
	// } else {
	// System.out.println(("" + tagObject.getClass()) + "--"
	// + informationString);
	// // Log.d("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void systemOut(Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// // if (informationObject instanceof String) {
	// System.out.println(informationString);
	// // ;
	// // } else {
	// // System.out.println(("" + informationObject.getClass()) + "--"
	// // + informationString);
	// // Log.d("" + tagObject.getClass(), informationString);
	// // }
	// }
	//
	// public static void systemOut(Object tagObject,
	// String informationPrefixString, Object informationObject) {
	// // if (!AppConstant.LOG) {
	// // return;
	// // }
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// System.out.println(((String) tagObject) + "--"
	// + informationPrefixString + ":" + informationString);
	// // ;
	// } else {
	// System.out.println(("" + tagObject.getClass()) + "--"
	// + informationPrefixString + ":" + informationString);
	// // Log.d("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void fileOut(Object tagObject,
	// String informationPrefixString, Object informationObject) {
	// String informationString = getStringFromObject(informationObject);
	// if (tagObject instanceof String) {
	// fileOut(((String) tagObject) + "--" + informationPrefixString + ":"
	// + informationString);
	// // ;
	// } else {
	// fileOut(("" + tagObject.getClass()) + "--"
	// + informationPrefixString + ":" + informationString);
	// // Log.d("" + tagObject.getClass(), informationString);
	// }
	// }
	//
	// public static void fileOut(Object informationObject) {
	// String informationString = getStringFromObject(informationObject);
	// File file = new File(AppLibConstant.getLogFilePath());
	// if (file.exists() == false) {
	// try {
	// file.createNewFile();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// DataOutputStream dos = null;
	// try {
	// dos = new DataOutputStream(new FileOutputStream(file, true));
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss  ");
	// String time = sdf.format(new Date());
	// dos.writeUTF(time);
	// dos.writeUTF(informationString + "\r\n");
	// dos.flush();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (dos != null) {
	// try {
	// dos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// dos = null;
	// }
	// }
	// }
	//
	// public static String getLogString(Object tagObject,
	// String informationPrefixString, Object informationObject) {
	// return getLogString(tagObject, informationPrefixString,
	// informationObject, JsonManager.getInstance());
	// }

	// public static void systemOut(Object tagObject,
	// String informationPrefixString, Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// systemOut(tagObject, informationPrefixString, informationObject,
	// JsonManager.getInstance());
	// }

	// public void fileOut(Object tagObject, String informationPrefixString,
	// Object informationObject) {
	// if (!AppLibConstant.isUseLog()) {
	// return;
	// }
	// fileOut(tagObject, informationPrefixString, informationObject,
	// JsonManager.getInstance());
	// }
}