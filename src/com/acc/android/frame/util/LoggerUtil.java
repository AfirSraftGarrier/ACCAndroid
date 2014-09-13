package com.acc.android.frame.util;

import android.util.Log;

import com.acc.android.frame.manager.JsonManager;

public class LoggerUtil {
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

	private static String getStringFromObject(Object object) {
		String resultString;
		if (object instanceof String) {
			resultString = (String) object;
		} else {
			try {
				resultString = JsonManager.getInstance(null).getJson(object);
			} catch (Exception e) {
				resultString = "该类不符合规范，尝试传字符串类型";
			}
		}
		return resultString;
	}

	public static void info(Object tagObject, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.i((String) tagObject, informationString);
		} else {
			Log.i("" + tagObject.getClass(), informationString);
		}
	}

	public static void warning(Object tagObject, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.w((String) tagObject, informationString);
		} else {
			Log.w("" + tagObject.getClass(), informationString);
		}
	}

	public static void error(Object tagObject, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.e((String) tagObject, informationString);
		} else {
			Log.e("" + tagObject.getClass(), informationString);
		}
	}

	public static void debug(Object tagObject, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.d((String) tagObject, informationString);
		} else {
			Log.d("" + tagObject.getClass(), informationString);
		}
	}

	public static void verbose(Object tagObject, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.v((String) tagObject, informationString);
		} else {
			Log.v("" + tagObject.getClass(), informationString);
		}
	}

	public static void info(Object tagObject, String informationPrefixString,
			Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = informationPrefixString
				+ getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.i((String) tagObject, informationString);
		} else {
			Log.i("" + tagObject.getClass(), informationString);
		}
	}

	public static void warning(Object tagObject,
			String informationPrefixString, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = informationPrefixString
				+ getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.w((String) tagObject, informationString);
		} else {
			Log.w("" + tagObject.getClass(), informationString);
		}
	}

	public static void error(Object tagObject, String informationPrefixString,
			Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = informationPrefixString
				+ getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.e((String) tagObject, informationString);
		} else {
			Log.e("" + tagObject.getClass(), informationString);
		}
	}

	public static void debug(Object tagObject, String informationPrefixString,
			Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = informationPrefixString
				+ getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.d((String) tagObject, informationString);
		} else {
			Log.d("" + tagObject.getClass(), informationString);
		}
	}

	public static void verbose(Object tagObject,
			String informationPrefixString, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = informationPrefixString
				+ getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			Log.v((String) tagObject, informationString);
		} else {
			Log.v("" + tagObject.getClass(), informationString);
		}
	}

	public static void systemOut(Object tagObject, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			System.out.println(((String) tagObject) + "--" + informationString);
			// ;
		} else {
			System.out.println(("" + tagObject.getClass()) + "--"
					+ informationString);
			// Log.d("" + tagObject.getClass(), informationString);
		}
	}

	public static void systemOut(Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		// if (informationObject instanceof String) {
		System.out.println(informationString);
		// ;
		// } else {
		// System.out.println(("" + informationObject.getClass()) + "--"
		// + informationString);
		// Log.d("" + tagObject.getClass(), informationString);
		// }
	}

	public static void systemOut(Object tagObject,
			String informationPrefixString, Object informationObject) {
		// if (!AppConstant.LOG) {
		// return;
		// }
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			System.out.println(((String) tagObject) + "--"
					+ informationPrefixString + ":" + informationString);
			// ;
		} else {
			System.out.println(("" + tagObject.getClass()) + "--"
					+ informationPrefixString + ":" + informationString);
			// Log.d("" + tagObject.getClass(), informationString);
		}
	}
}
