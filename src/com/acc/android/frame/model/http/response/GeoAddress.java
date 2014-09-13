package com.acc.android.frame.model.http.response;

import com.acc.android.frame.model.Result;
import com.acc.android.frame.model.Status;

public class GeoAddress {
	private Status status;
	private Result result;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
