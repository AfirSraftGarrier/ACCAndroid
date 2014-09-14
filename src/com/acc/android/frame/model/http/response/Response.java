package com.acc.android.frame.model.http.response;

public class Response<T> {
	private ResponseStatus responseStatus;
	private Integer tagCode;
	private String description;
	private T resultObject;

	public T getResultObject() {
		return resultObject;
	}

	public void setResultObject(T resultObject) {
		this.resultObject = resultObject;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Integer getTagCode() {
		return tagCode;
	}

	public void setTagCode(Integer tagCode) {
		this.tagCode = tagCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
