package com.acc.android.frame.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BeanUtil {
	public static void copyProperties(Object from, Object to) {
		try {
			copyPropertiesExclude(from, to, null);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void copyPropertiesExclude(Object from, Object to,
			String[] excludsArray) throws Exception {
		List<String> excludesList = null;
		if (excludsArray != null && excludsArray.length > 0) {
			excludesList = Arrays.asList(excludsArray);
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			if (excludesList != null
					&& excludesList.contains(fromMethodName.substring(3)
							.toLowerCase())) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);
			if (value == null)
				continue;
			if (value instanceof Collection) {
				Collection newValue = (Collection) value;
				if (newValue.size() <= 0)
					continue;
			}
			toMethod.invoke(to, new Object[] { value });
		}
	}

	public static void copyPropertiesInclude(Object from, Object to,
			String[] includsArray) throws Exception {
		List<String> includesList = null;
		if (includsArray != null && includsArray.length > 0) {
			includesList = Arrays.asList(includsArray);
		} else {
			return;
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			String str = fromMethodName.substring(3);
			if (!includesList.contains(str.substring(0, 1).toLowerCase()
					+ str.substring(1))) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);
			if (value == null)
				continue;
			if (value instanceof Collection) {
				Collection newValue = (Collection) value;
				if (newValue.size() <= 0)
					continue;
			}
			toMethod.invoke(to, new Object[] { value });
		}
	}

	public static Method findMethodByName(Method[] methods, String name) {
		for (int j = 0; j < methods.length; j++) {
			if (methods[j].getName().equals(name))
				return methods[j];
		}
		return null;
	}
}
