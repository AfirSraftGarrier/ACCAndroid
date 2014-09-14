package com.acc.android.frame.model.http.request;

public class Request<T> {
	private RequestMethod requestMethod;
	private T requestObject;

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	public T getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(T requestObject) {
		this.requestObject = requestObject;
	}
}
