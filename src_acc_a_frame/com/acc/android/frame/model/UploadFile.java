/**
 * 
 * ACCAFrame - ACC Android Development Platform
 * Copyright (c) 2014, AfirSraftGarrier, afirsraftgarrier@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
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