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
package com.acc.android.util.scan.decoding;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * Simple listener used to exit the app in a few cases.
 *
 * @author Sean Owen
 */
public final class FinishListener implements DialogInterface.OnClickListener,
		DialogInterface.OnCancelListener, Runnable {

	private final Activity activityToFinish;

	public FinishListener(Activity activityToFinish) {
		this.activityToFinish = activityToFinish;
	}

	public void onCancel(DialogInterface dialogInterface) {
		run();
	}

	public void onClick(DialogInterface dialogInterface, int i) {
		run();
	}

	public void run() {
		activityToFinish.finish();
	}

}