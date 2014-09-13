package com.acc.android.frame.model;

public class UploadFile {
	// private String contentType;
	// private byte[] bytes;
	private String name;
	// private String fileName;
	private String filePath;
	private String contentType;

	// public String getContentType() {
	// return this.contentType;
	// }

	// public void setContentType(String contentType) {
	// this.contentType = contentType;
	// }

	// public byte[] getBytes() {
	// return this.bytes;
	// }

	// public void setBytes(byte[] bytes) {
	// this.bytes = bytes;
	// }

	// public String getFormName() {
	// return this.formName;
	// }
	//
	// public void setFormName(String formName) {
	// this.formName = formName;
	// }

	// public String getFileName() {
	// return this.fileName;
	// }
	//
	// public void setFileName(String fileName) {
	// this.fileName = fileName;
	// }

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
