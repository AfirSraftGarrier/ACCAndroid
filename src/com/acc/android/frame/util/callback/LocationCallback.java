package com.acc.android.frame.util.callback;

import com.acc.android.frame.model.GeoData;
import com.acc.android.frame.model.GeoDataWithoutAddress;

public interface LocationCallback {
	void receive(GeoDataWithoutAddress geoPoint);

	void receive(GeoData geoData);

	void receive(String address);
}
