package com.acc.android.frame.network.operator;

import android.content.Context;

import com.acc.android.frame.model.http.request.RequestMethod;
import com.acc.android.frame.model.http.response.GeoAddress;
import com.acc.android.frame.network.api.ACCApi;
import com.acc.android.frame.network.operator.base.BaseHttpOperator;

public class CommonHttpOperator extends BaseHttpOperator {

	public CommonHttpOperator(Context context) {
		super(context);
	}

	public GeoAddress getGeoAddress(double latitude, double longitude) {
		return this.getResultObject(
				ACCApi.getUrlReaquestAddress(latitude, longitude), null,
				RequestMethod.GET, GeoAddress.class);
	}

	// @Override
	// public BaseHttpOperator getInstance(Context context) {
	// return new CommonHttpOperator(context);
	// }
}
