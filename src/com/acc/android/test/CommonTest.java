package com.acc.android.test;

import junit.framework.TestCase;

import com.acc.android.frame.network.NetworkHelper;
import com.acc.android.frame.network.operator.CommonHttpOperator;
import com.acc.android.frame.util.LoggerUtil;

public class CommonTest extends TestCase {

	public static void test() {
		// Assert.assertEquals(1, 1);
		CommonHttpOperator commonHttpOperator = NetworkHelper.getInstance(null)
				.getHttpOperator(CommonHttpOperator.class);
		LoggerUtil.systemOut(commonHttpOperator);
	}

	// public static void test2() {
	// CommonHttpOperator commonHttpOperator = NetworkHelper.getInstance(null)
	// .getHttpOperator(CommonHttpOperator.class);
	// LoggerUtil.systemOut(commonHttpOperator);
	// }

}
