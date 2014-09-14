package com.acc.android.frame.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.acc.android.frame.manager.JsonManager;
import com.acc.frame.util.base.BaseLoggerUtil;
import com.acc.frame.util.constant.AppLibConstant;

public class LoggerUtil extends BaseLoggerUtil {
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

	public static void fileOut(Object tagObject,
			String informationPrefixString, Object informationObject) {
		String informationString = getStringFromObject(informationObject);
		if (tagObject instanceof String) {
			fileOut(((String) tagObject) + "--" + informationPrefixString + ":"
					+ informationString);
			// ;
		} else {
			fileOut(("" + tagObject.getClass()) + "--"
					+ informationPrefixString + ":" + informationString);
			// Log.d("" + tagObject.getClass(), informationString);
		}
	}

	public static void fileOut(Object informationObject) {
		String informationString = getStringFromObject(informationObject);
		File file = new File(AppLibConstant.getLogFilePath());
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(new FileOutputStream(file, true));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss  ");
			String time = sdf.format(new Date());
			dos.writeUTF(time);
			dos.writeUTF(informationString + "\r\n");
			dos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				dos = null;
			}
		}
	}
}
