package com.acc.android.frame.util;

import android.util.Log;

import com.acc.android.frame.manager.JsonManager;
import com.acc.frame.util.base.BaseLogUtil;
import com.acc.frame.util.constant.AppLibConstant;

public class LogUtil extends BaseLogUtil {
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

	public static void info(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		info(tagObject,
				getLogString(informationObject, JsonManager.getInstance()));
	}

	public static void info(Object tagObject, String informationPrefixString,
			Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		info(tagObject,
				getLogString(tagObject, informationPrefixString,
						informationObject, JsonManager.getInstance()));
	}

	private static void info(Object tagObject, String logString) {
		info(tagObject.getClass().toString(), logString);
	}

	private static void info(String tagString, String logString) {
		Log.i(tagString, logString);
	}

	public static void warn(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		warn(tagObject,
				getLogString(informationObject, JsonManager.getInstance()));
	}

	public static void warn(Object tagObject, String informationPrefixString,
			Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		warn(tagObject,
				getLogString(tagObject, informationPrefixString,
						informationObject, JsonManager.getInstance()));
	}

	private static void warn(Object tagObject, String logString) {
		warn(tagObject.getClass().toString(), logString);
	}

	private static void warn(String tagString, String logString) {
		Log.w(tagString, logString);
	}

	public static void error(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		error(tagObject,
				getLogString(informationObject, JsonManager.getInstance()));
	}

	public static void error(Object tagObject, String informationPrefixString,
			Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		error(tagObject,
				getLogString(tagObject, informationPrefixString,
						informationObject, JsonManager.getInstance()));
	}

	private static void error(Object tagObject, String logString) {
		error(tagObject.getClass().toString(), logString);
	}

	private static void error(String tagString, String logString) {
		Log.e(tagString, logString);
	}

	public static void debug(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		debug(tagObject,
				getLogString(informationObject, JsonManager.getInstance()));
	}

	public static void debug(Object tagObject, String informationPrefixString,
			Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		debug(tagObject,
				getLogString(tagObject, informationPrefixString,
						informationObject, JsonManager.getInstance()));
	}

	private static void debug(Object tagObject, String logString) {
		debug(tagObject.getClass().toString(), logString);
	}

	private static void debug(String tagString, String logString) {
		Log.d(tagString, logString);
	}

	public static void verbose(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		verbose(tagObject,
				getLogString(informationObject, JsonManager.getInstance()));
	}

	public static void verbose(Object tagObject,
			String informationPrefixString, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		verbose(tagObject,
				getLogString(tagObject, informationPrefixString,
						informationObject, JsonManager.getInstance()));
	}

	private static void verbose(Object tagObject, String logString) {
		verbose(tagObject.getClass().toString(), logString);
	}

	private static void verbose(String tagString, String logString) {
		Log.v(tagString, logString);
	}

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
	public static String getLogString(Object informationObject) {
		return getLogString(informationObject, JsonManager.getInstance());
	}

	public static String getLogString(Object tagObject, Object informationObject) {
		return getLogString(tagObject, informationObject,
				JsonManager.getInstance());
	}

	public static String getLogString(Object tagObject,
			String informationPrefixString, Object informationObject) {
		return getLogString(tagObject, informationPrefixString,
				informationObject, JsonManager.getInstance());
	}

	public static void systemOut(Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		systemOut(informationObject, JsonManager.getInstance());
	}

	public static void systemOut(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		systemOut(tagObject, informationObject, JsonManager.getInstance());
	}

	public static void systemOut(Object tagObject,
			String informationPrefixString, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		systemOut(tagObject, informationPrefixString, informationObject,
				JsonManager.getInstance());
	}

	protected static void fileOut(Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		fileOut(informationObject, JsonManager.getInstance());
	}

	protected static void fileOut(Object tagObject, Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		fileOut(tagObject, informationObject, JsonManager.getInstance());
	}

	public void fileOut(Object tagObject, String informationPrefixString,
			Object informationObject) {
		if (!AppLibConstant.isUseLog()) {
			return;
		}
		fileOut(tagObject, informationPrefixString, informationObject,
				JsonManager.getInstance());
	}
}
