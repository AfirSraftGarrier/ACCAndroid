package com.acc.android.frame.network;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.acc.android.frame.network.operator.base.BaseHttpOperator;

public class NetworkHelper {
	private static NetworkHelper instance;
	private final Context context;

	private final Map<Class<? extends BaseHttpOperator>, BaseHttpOperator> httpOperatorMap;

	public void registHttpOprator(Class<? extends BaseHttpOperator> classT,
			BaseHttpOperator baseHttpOperator) {
		this.httpOperatorMap.put(classT, baseHttpOperator);
	}

	public void unRegistHttpOprator(Class<? extends BaseHttpOperator> classT) {
		this.httpOperatorMap.remove(classT);
	}

	public <T extends BaseHttpOperator> T getHttpOperator(
			Class<? extends T> classT) {
		BaseHttpOperator baseHttpOperator = this.httpOperatorMap.get(classT);
		if (baseHttpOperator == null) {
			try {
				baseHttpOperator = classT.getConstructor(Context.class)
						.newInstance(context);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		if (baseHttpOperator != null) {
			this.registHttpOprator(classT, baseHttpOperator);
		}
		return (T) baseHttpOperator;
	}

	private NetworkHelper(Context context) {
		this.context = context;
		this.httpOperatorMap = new HashMap<Class<? extends BaseHttpOperator>, BaseHttpOperator>();
	}

	public static NetworkHelper getInstance(Context context) {
		if (instance == null) {
			instance = new NetworkHelper(context);
		}
		return instance;
	}
}
