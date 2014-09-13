package com.acc.android.frame.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

public class IntentUtil {
	public static void startIntent(Context context, Class intentClass,
			Object... bundleObjects) {
		// List<Object> bundleObjectList = ListUtil.getList(bundleObjects);
		// // values.
		// // int sdf = values.length;
		// // sdf = values.;
		// // if(values == null) {
		// //
		// // }
		// Bundle bundle = new Bundle();
		// if (!ListUtil.isEmpty(bundleObjectList)) {
		// int bundleObjectSize = bundleObjectList.size();
		// for (int i = 0; i + 1 < bundleObjectSize; i = i + 2) {
		// Object keyObject = bundleObjectList.get(i);
		// Object valueObject = bundleObjectList.get(i + 1);
		// if (valueObject instanceof Serializable) {
		// bundle.putSerializable(keyObject.toString(),
		// (Serializable) valueObject);
		// } else if (valueObject instanceof String) {
		// bundle.putString(keyObject.toString(), (String) valueObject);
		// } else if (valueObject instanceof Long) {
		// bundle.putLong(keyObject.toString(), ((Long) valueObject));
		// } else if (valueObject instanceof Integer) {
		// bundle.putInt(keyObject.toString(), (Integer) valueObject);
		// } else if (valueObject instanceof Boolean) {
		// bundle.putBoolean(keyObject.toString(),
		// (Boolean) valueObject);
		// } else if (valueObject instanceof ArrayList) {
		// bundle.putParcelableArrayList(keyObject.toString(),
		// (ArrayList<? extends Parcelable>) valueObject);
		// }
		// // else if (valueObject instanceof Serializable) {
		// // bundle.putSerializable(keyObject.toString(),
		// // (Serializable) valueObject);
		// // }
		// }
		// }

		// Intent intent = new Intent(context, intentClass);
		// // Bundle bundle = new Bundle();
		// // bundle.putLong(BundleKeyConstant.PROBLEM_ID, problemId);
		// intent.putExtras(bundle);
		// context.startActivity(intent);
		startIntent(context, intentClass, getBundle(bundleObjects));
	}

	public static Bundle getBundle(Object... bundleObjects) {
		List<Object> bundleObjectList = ListUtil.getList(bundleObjects);
		Bundle bundle = new Bundle();
		if (!ListUtil.isEmpty(bundleObjectList)) {
			int bundleObjectSize = bundleObjectList.size();
			for (int i = 0; i + 1 < bundleObjectSize; i = i + 2) {
				Object keyObject = bundleObjectList.get(i);
				Object valueObject = bundleObjectList.get(i + 1);
				if (valueObject instanceof Serializable) {
					bundle.putSerializable(keyObject.toString(),
							(Serializable) valueObject);
				} else if (valueObject instanceof String) {
					bundle.putString(keyObject.toString(), (String) valueObject);
				} else if (valueObject instanceof Long) {
					bundle.putLong(keyObject.toString(), ((Long) valueObject));
				} else if (valueObject instanceof Integer) {
					bundle.putInt(keyObject.toString(), (Integer) valueObject);
				} else if (valueObject instanceof Boolean) {
					bundle.putBoolean(keyObject.toString(),
							(Boolean) valueObject);
				}
				// else if (valueObject instanceof ArrayList) {
				// bundle.putParcelableArrayList(keyObject.toString(),
				// (ArrayList<? extends Parcelable>) valueObject);
				// }
				// else if (valueObject instanceof Serializable) {
				// bundle.putSerializable(keyObject.toString(),
				// (Serializable) valueObject);
				// }
			}
		}
		return bundle;
	}

	@Deprecated
	public static void tempStartIntent(Context context, Class intentClass,
			Object... bundleObjects) {
		List<Object> bundleObjectList = ListUtil.getList(bundleObjects);
		Bundle bundle = new Bundle();
		if (!ListUtil.isEmpty(bundleObjectList)) {
			int bundleObjectSize = bundleObjectList.size();
			for (int i = 0; i + 1 < bundleObjectSize; i = i + 2) {
				Object keyObject = bundleObjectList.get(i);
				Object valueObject = bundleObjectList.get(i + 1);
				if (valueObject instanceof Serializable) {
					bundle.putSerializable(keyObject.toString(),
							(Serializable) valueObject);
				} else if (valueObject instanceof String) {
					bundle.putString(keyObject.toString(), (String) valueObject);
				} else if (valueObject instanceof Long) {
					bundle.putLong(keyObject.toString(), ((Long) valueObject));
				} else if (valueObject instanceof Integer) {
					bundle.putInt(keyObject.toString(), (Integer) valueObject);
				} else if (valueObject instanceof Boolean) {
					bundle.putBoolean(keyObject.toString(),
							(Boolean) valueObject);
				} else if (valueObject instanceof ArrayList) {
					bundle.putParcelableArrayList(keyObject.toString(),
							(ArrayList<? extends Parcelable>) valueObject);
				}
				// else if (valueObject instanceof Serializable) {
				// bundle.putSerializable(keyObject.toString(),
				// (Serializable) valueObject);
				// }
			}
		}
		startIntent(context, intentClass, bundle);
	}

	// public static void startIntent(Context context, Class intentClass,
	// String serializablekeyString, Serializable serializable) {
	// // List<Object> bundleObjectList = ListUtil.getList(bundleObjects);
	// // values.
	// // int sdf = values.length;
	// // sdf = values.;
	// // if(values == null) {
	// //
	// // }
	// Bundle bundle = new Bundle();
	// // if (!ListUtil.isEmpty(bundleObjectList)) {
	// // int bundleObjectSize = bundleObjectList.size();
	// // for (int i = 0; i + 1 < bundleObjectSize; i = i + 2) {
	// // Object keyObject = bundleObjectList.get(i);
	// // Object valueObject = bundleObjectList.get(i + 1);
	// // if (valueObject instanceof String) {
	// // bundle.putString(keyObject.toString(),
	// // valueObject.toString());
	// // } else if (valueObject instanceof Long) {
	// // bundle.putLong(keyObject.toString(), ((Long) valueObject));
	// // } else if (valueObject instanceof Integer) {
	// // bundle.putInt(keyObject.toString(), (Integer) valueObject);
	// // } else if (valueObject instanceof Boolean) {
	// // bundle.putBoolean(keyObject.toString(),
	// // (Boolean) valueObject);
	// // }
	// // }
	// // }
	// bundle.putSerializable(serializablekeyString, serializable);
	// // Intent intent = new Intent(context, intentClass);
	// // // Bundle bundle = new Bundle();
	// // // bundle.putLong(BundleKeyConstant.PROBLEM_ID, problemId);
	// // intent.putExtras(bundle);
	// // context.startActivity(intent);
	// startIntent(context, intentClass, bundle);
	// }

	public static void startIntent(Context context, Class intentClass,
			Bundle bundle) {
		Intent intent = new Intent(context, intentClass);
		// Bundle bundle = new Bundle();
		// bundle.putLong(BundleKeyConstant.PROBLEM_ID, problemId);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	public static void startActivityForResult(Activity activity,
			Class intentClass, int requestCode, Object... bundleObjects) {
		Intent intent = new Intent(activity, intentClass);
		// Bundle bundle = new Bundle();
		// bundle.putLong(BundleKeyConstant.PROBLEM_ID, problemId);
		Bundle bundle = getBundle(bundleObjects);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, requestCode);
	}
}
