/**
 * 
 * ACCAndroid - ACC Android Development Platform
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
package com.acc.android.model;

import java.util.ArrayList;
import java.util.List;

import com.acc.java.model.ACCFile;

public class ImageData {
	private List<String> localPaths = new ArrayList<String>();
	private List<Long> fileIds = new ArrayList<Long>();
	private List<Long> toBeDeleteFileIds = new ArrayList<Long>();
	private Integer selectIndex;
	private List<ACCFile> accFiles;

	public List<Long> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}

	public List<Long> getToBeDeleteFileIds() {
		return toBeDeleteFileIds;
	}

	public void setToBeDeleteFileIds(List<Long> toBeDeleteFileIds) {
		this.toBeDeleteFileIds = toBeDeleteFileIds;
	}

	public Integer getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(Integer selectIndex) {
		this.selectIndex = selectIndex;
	}

	public List<ACCFile> getAccFiles() {
		return accFiles;
	}

	public void setAccFiles(List<ACCFile> accFiles) {
		this.accFiles = accFiles;
	}

	public List<String> getLocalPaths() {
		return localPaths;
	}

	public void setLocalPaths(List<String> localPaths) {
		this.localPaths = localPaths;
	}

	// public List<Cphoto> getcPhotos() {
	// return cPhotos;
	// }
	//
	// public void setcPhotos(List<Cphoto> cPhotos) {
	// this.cPhotos = cPhotos;
	// }

	// public List<Long> getSysFileIds() {
	// return fileIds;
	// }
	//
	// public void setSysFileIds(List<Long> sysFileIds) {
	// this.fileIds = sysFileIds;
	// }
	//
	// public List<ACCFile> getAccFiles() {
	// return accFiles;
	// }
	//
	// public void setAccFiles(List<ACCFile> accFiles) {
	// this.accFiles = accFiles;
	// }
	//
	// public List<Long> getToBeDeleteSysFileIds() {
	// return toBeDeleteFileIds;
	// }
	//
	// public void setToBeDeleteSysFileIds(List<Long> toBeDeleteSysFileIds) {
	// this.toBeDeleteFileIds = toBeDeleteSysFileIds;
	// }
	//
	// public Integer getSelectIndex() {
	// return selectIndex;
	// }
	//
	// public void setSelectIndex(Integer selectIndex) {
	// this.selectIndex = selectIndex;
	// }
}