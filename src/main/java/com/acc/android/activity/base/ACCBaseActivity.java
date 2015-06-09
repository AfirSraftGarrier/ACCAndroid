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
package com.acc.android.activity.base;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.acc.android.util.ResourceUtil;

public class ACCBaseActivity extends Activity {
	public void back(View view) {
		this.finish();
	}

	// Stores names of traversed directories
	ArrayList<String> str
	// = new ArrayList<String>()
	;
	// Check if the first level of the directory structure is the one showing
	private Boolean firstLvl
	// = true
	;
	// private static final String TAG = "F_PATH";
	private Item[] fileList;
	private File path
	// = new File(Environment.getExternalStorageDirectory() + "")
	;
	// private static final int DIALOG_LOAD_FILE = 1000;
	private ListAdapter adapter;
	private final String upString = "...";
	private OnChooseFileListener onChooseFileListener;

	// protected String chosenFile;

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// // showDialog(DIALOG_LOAD_FILE);
	// // this.pickFile(null, null);
	// this.setContentView(R.layout.main);
	// this.findViewById(R.id.show).setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View view) {
	// FileExplore.this.show(view);
	// }
	// });
	// // Log.d(TAG, path.getAbsolutePath());
	// }

	protected void pickFile(String initPath,
			OnChooseFileListener onChooseFileListener) {
		if (initPath == null) {
			this.path = new File(Environment.getExternalStorageDirectory() + "");
		} else {
			this.path = new File(initPath);
		}
		this.str = new ArrayList<String>();
		this.firstLvl = true;
		this.onChooseFileListener = onChooseFileListener;
		loadFileList();
		showDialog(
		// DIALOG_LOAD_FILE
		);
	}

	public void show(View view) {
		// System.out.println("111111111111");
		this.pickFile(null, null);
	}

	public interface OnChooseFileListener {
		void onChooseFile(String fileName);
	}

	private void onChooseFile(String fileName) {
		this.path = null;
		this.str = null;
		this.firstLvl = null;
		this.adapter = null;
		if (this.onChooseFileListener != null) {
			this.onChooseFileListener.onChooseFile(fileName);
			this.onChooseFileListener = null;
		}
		// System.out.println(fileName);
		// try {
		// Thread.sleep(100);
		// this.pickFile(null, null);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void loadFileList() {
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			// Log.e(TAG, "unable to write on the sd card ");
		}
		// Checks whether path exists
		if (path.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					File sel = new File(dir, filename);
					// Filters based on whether the file is hidden or not
					return (sel.isFile() || sel.isDirectory())
							&& !sel.isHidden();
				}
			};
			String[] fList = path.list(filter);
			fileList = new Item[fList.length];
			int fileIconId = ResourceUtil.getDrawableId(this, "file_icon");
			int directoryIconId = ResourceUtil.getDrawableId(this,
					"directory_icon");
			int directoryUpId = ResourceUtil
					.getDrawableId(this, "directory_up");
			for (int i = 0; i < fList.length; i++) {
				fileList[i] = new Item(fList[i], fileIconId
				// ResourceUtil.getDrawableId(
				// this, "file_icon")
				);
				// Convert into file path
				File sel = new File(path, fList[i]);
				// Set drawables
				if (sel.isDirectory()) {
					fileList[i].icon = directoryIconId
					// R.drawable.directory_icon
					;
					// Log.d("DIRECTORY", fileList[i].file);
				} else {
					// System.out.println(fileList[i].file);
					// Log.d("FILE", fileList[i].file);
				}
			}
			if (!firstLvl) {
				Item temp[] = new Item[fileList.length + 1];
				for (int i = 0; i < fileList.length; i++) {
					temp[i + 1] = fileList[i];
				}
				temp[0] = new Item(this.upString, directoryUpId);
				fileList = temp;
			}
		} else {
			// Log.e(TAG, "path does not exist");
		}
		adapter = new ArrayAdapter<Item>(this,
				android.R.layout.select_dialog_item, android.R.id.text1,
				fileList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// creates view
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);

				// put the image on the text view
				textView.setCompoundDrawablesWithIntrinsicBounds(
						fileList[position].icon, 0, 0, 0);

				// add margin between image and text (support various screen
				// densities)
				int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
				textView.setCompoundDrawablePadding(dp5);

				return view;
			}
		};

	}

	private class Item {
		public String file;
		public int icon;

		public Item(String file, Integer icon) {
			this.file = file;
			this.icon = icon;
		}

		@Override
		public String toString() {
			return file;
		}
	}

	private void showDialog() {
		Dialog dialog = null;
		AlertDialog.Builder builder = new Builder(this);
		if (fileList == null) {
			// Log.e(TAG, "No files loaded");
			dialog = builder.create();
			// return dialog;
		}
		// switch (id) {
		// case DIALOG_LOAD_FILE:
		builder.setTitle("璇烽�夋嫨鏂囦欢");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String chosenFile = fileList[which].file;
				File sel = new File(path + "/" + chosenFile);
				if (sel.isDirectory()) {
					firstLvl = false;
					// Adds chosen directory to list
					str.add(chosenFile);
					fileList = null;
					path = new File(sel + "");
					loadFileList();
					dialog.dismiss();
					;
					showDialog(
					// DIALOG_LOAD_FILE
					);
					// Log.d(TAG, path.getAbsolutePath());
				} // Checks if 'up' was clicked
				else if (chosenFile
						.equalsIgnoreCase(ACCBaseActivity.this.upString)
						&& !sel.exists()) {
					// present directory removed from list
					String s = str.remove(str.size() - 1);
					// path modified to exclude present directory
					path = new File(path.toString().substring(0,
							path.toString().lastIndexOf(s)));
					fileList = null;
					// if there are no more directories in the list, then
					// its the first level
					if (str.isEmpty()) {
						firstLvl = true;
					}
					loadFileList();
					// removeDialog(DIALOG_LOAD_FILE);
					dialog.dismiss();
					showDialog(
					// DIALOG_LOAD_FILE
					);
					// Log.d(TAG, path.getAbsolutePath());
				}
				// File picked
				else {
					// System.out.println(sel.getPath());
					// System.out.println(sel);
					// System.out.println(sel.getAbsoluteFile().getPath());
					// System.out.println(sel.getName());
					// Perform action with file picked
					ACCBaseActivity.this.onChooseFile(sel.getPath());
				}

			}
		});
		// break;
		// }
		dialog = builder.show();
	}

	protected Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
	}
}