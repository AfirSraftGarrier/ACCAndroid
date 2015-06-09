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
package com.acc.android.frame.manager;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Looper;

import com.acc.android.frame.util.ResourceUtil;
import com.acc.frame.util.ListUtil;

//import com.augurit.android.hfss.R;

public class CrashReportManager {
	private static CrashReportManager instance;
	private UncaughtExceptionHandler uncaughtExceptionHandler;
	private UncaughtExceptionHandler defaultUncaughtExceptionHandler;
	private final List<Context> contexts;

	public static CrashReportManager getInstance(Context context) {
		if (instance == null) {
			instance = new CrashReportManager(context);
			instance.regist(context);
			// contexts = new ArrayList<Context>();
		}
		return instance;
	}

	private CrashReportManager(Context context) {
		this.contexts = new ArrayList<Context>();
	}

	// public static final String TAG = "CrashHandler";
	// private static CrashReportManager INSTANCE = new CrashReportManager();
	// private static Context CONTEXT;
	// private Context context;
	// private Thread.UncaughtExceptionHandler mDefaultHandler;

	// private CrashReportManager(Context context) {
	// this.context = context;
	// }

	// public static CrashReportManager getInstance() {
	// return INSTANCE;
	// }

	// public void attachContext(Context context) {
	// CONTEXT = context;
	// mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	// Thread.setDefaultUncaughtExceptionHandler(INSTANCE);
	// }

	// @Override
	// public void uncaughtException(Thread thread, Throwable ex) {
	// if (!handleException(ex) && mDefaultHandler != null) {
	// mDefaultHandler.uncaughtException(thread, ex);
	// } else {
	// android.os.Process.killProcess(android.os.Process.myPid());
	// System.exit(10);
	// }
	// System.out.println("uncaughtException");
	// System.exit(0);

	// @Override
	// public void refresh() {
	// // TODO Auto-generated method stub
	// }

	public void unRegist(Context context) {
		if (context != null && !ListUtil.isEmpty(this.contexts)) {
			this.contexts.remove(context);
		}
	}

	// @Override
	public void regist(final Context context) {
		this.contexts.add(context);
		this.defaultUncaughtExceptionHandler = Thread
				.getDefaultUncaughtExceptionHandler();
		this.uncaughtExceptionHandler = new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread,
					final Throwable throwable) {
				// if (!handleException(throwable)
				// && defaultUncaughtExceptionHandler != null) {
				// // defaultUncaughtExceptionHandler.uncaughtException(thread,
				// // throwable);
				// return;
				// } else {
				// // defaultUncaughtExceptionHandler.uncaughtException(thread,
				// // throwable);
				// // throwable.printStackTrace();
				// // AppManager.getInstance(context)
				// // .exit();
				// // System.exit(0);
				// return;
				// }
				new Thread() {
					@Override
					public void run() {
						// System.out.println("HHHHHHHHH");
						Looper.prepare();
						// if (ex instanceof OutOfMemoryError) {
						// // System.out.println("11111111111111HHHHHHHHH");
						// new AlertDialog.Builder(context)
						// .setTitle(R.string.hint)
						// .setCancelable(false)
						// .setMessage(
						// R.string.sys_no_catch_exception_oom)
						// .setNeutralButton(R.string.i_see,
						// new OnClickListener() {
						// @Override
						// public void onClick(
						// DialogInterface dialog,
						// int which) {
						// // System.out
						// // .println("GGGGGGGGGGGGGGGGGGGGG");
						// // LogUtil.info(
						// // CrashReportManager.this,
						// // ex);
						// ex.printStackTrace();
						// // AppManager.getInstance(context)
						// // .exit();
						// System.exit(0);
						// }
						// })
						// // .setNegativeButton(R.string.i_see,
						// // new OnClickListener() {
						// // @Override
						// // public void onClick(
						// // DialogInterface dialog,
						// // int which) {
						// // // System.out
						// // // .println("GGGGGGGGGGGGGGGGGGGGG");
						// // // LogUtil.info(
						// // // CrashReportManager.this,
						// // // ex);
						// // ex.printStackTrace();
						// // // AppManager.getInstance(context)
						// // // .exit();
						// // // System.exit(0);
						// // }
						// // })
						// .create().show();
						// } else {
						// System.out.println("11111111111111HHHHHHHHH");
						new AlertDialog.Builder(context)
								.setTitle(
										ResourceUtil.getStringId(context,
												"hint")
								// R.string.hint
								)
								.setCancelable(false)
								.setMessage(
										ResourceUtil.getStringId(context,
												"sys_no_catch_exception")
								// R.string.sys_no_catch_exception
								)
								.setNeutralButton(
										ResourceUtil.getStringId(context,
												"i_see")
										// R.string.i_see
										, new OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// System.out
												// .println("GGGGGGGGGGGGGGGGGGGGG");
												// LogUtil.info(
												// CrashReportManager.this,
												// ex);
												// if (TestConstant.MEMORY) {
												// OldSystemUtil
												// .dsfdfd(context);
												// }
												throwable.printStackTrace();
												whenExit();
												// AppManager.getInstance(context)
												// .exit();
												System.exit(0);
											}
										})
								// .setNegativeButton(R.string.goon,
								// new OnClickListener() {
								// @Override
								// public void onClick(
								// DialogInterface dialog,
								// int which) {
								// // System.out
								// // .println("GGGGGGGGGGGGGGGGGGGGG");
								// // LogUtil.info(
								// // CrashReportManager.this,
								// // ex);
								// throwable.printStackTrace();
								// Thread.currentThread()
								// .destroy();
								// // AppManager.getInstance(context)
								// // .exit();
								// // System.exit(0);
								// }
								// })
								.create().show();
						// }
						Looper.loop();
					}
				}.start();
			}

			private void whenExit() {
				if (!ListUtil.isEmpty(contexts)) {
					for (Context context : contexts) {
						if (context != null) {
							((Activity) context).finish();
						}
					}
				}
			}

			// private boolean h

			private boolean handleException(Throwable ex) {
				// System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB");
				if (ex == null) {
					// System.out.println("11111111111111111");
					return true;
				}
				// new Handler(Looper.getMainLooper()).post(new Runnable() {
				// @Override
				// public void run() {
				// new AlertDialog.Builder(mContext).setTitle("提示")
				// .setMessage("程序崩溃了...").setNeutralButton("我知道了", null)
				// .create().show();
				// }
				// // });
				// System.out.println("122222222222222222");
				// System.out.println("");
				return true;
			}

		};
		Thread.setDefaultUncaughtExceptionHandler(this.uncaughtExceptionHandler);
	}
}