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
package com.acc.android.frame.util;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;

import com.acc.android.frame.manager.JsonManager;
import com.acc.android.frame.util.constant.ACCALibConstant;
import com.acc.frame.model.ACCFile;
import com.google.gson.reflect.TypeToken;

public class ACCFileUtil {
	public static List<ACCFile> getACCFiles(Activity activity) {
		try {
			String accFilesString = activity.getIntent().getExtras()
					.getString(ACCALibConstant.KEY_BUNDLE_ACC_FILE_S);
			Type type = new TypeToken<List<ACCFile>>() {
			}.getType();
			return JsonManager.getInstance().getObject(accFilesString, type);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
}