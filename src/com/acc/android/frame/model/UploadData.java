package com.acc.android.frame.model;

import java.util.List;
import java.util.Map;

public class UploadData {
	private List<UploadFile> uploadFiles;
	private Map<Object, Object> paramMap;

	public void setParamMap(Map<Object, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public Map<Object, Object> getParamMap() {
		return paramMap;
	}

	public void setUploadFiles(List<UploadFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public List<UploadFile> getUploadFiles() {
		return uploadFiles;
	}
}
