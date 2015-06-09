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
package com.acc.android.manager.viewmanager;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import com.acc.android.util.ResourceUtil;
import com.acc.android.util.TimeUtil;
import com.acc.android.util.view.WheelView;
import com.acc.android.util.view.WheelView.OnWheelChangedListener;
import com.acc.android.util.widget.adapter.NumericWheelAdapter;

//import com.augurit.android.hfss.R;

public class TimeViewManager {
	private static TimeViewManager instance;

	public interface OnValidateListener {
		void onRight();

		void onWrong();
	}

	private TimeViewManager() {
	}

	public static TimeViewManager getInstance(Context context) {
		if (instance == null) {
			instance = new TimeViewManager();
		}
		return instance;
	}

	public static boolean validateTimeEditText(EditText editText) {
		return validateTimeEditText(editText, null);
	}

	public static boolean validateTimeEditText(EditText editText,
			OnValidateListener onValidateListener) {
		if (TimeUtil.parseAppDateEx(editText.getText().toString()) == null) {
			if (onValidateListener != null) {
				onValidateListener.onWrong();
			} else {
				editText.requestFocus();
				editText.setError("时间格式不对");
			}
			return false;
		} else {
			if (onValidateListener != null) {
				onValidateListener.onRight();
			}
			return true;
		}
	}

	public static Date getDate(EditText editText) {
		return TimeUtil.parseAppDateEx(editText.getText().toString());
	}

	public void rigistTimeAction(final EditText editText,
			final Context context, Date date,
			final OnValidateListener onValidateListener) {
		editText.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				TimeViewManager.this.validateTimeEditText(editText,
						onValidateListener);
				return false;
			}
		});
		// date = date == null ? new Date() : date;
		editText.setText(TimeUtil.dateToAppStringEx(date));
		// editText.setEnabled(false);
		editText.setOnClickListener(new TimeOnClickListener(editText, context,
				onValidateListener));
	}

	public void rigistTimeAction(final EditText editText,
			final Context context, Date date) {
		// editText.setOnKeyListener(new OnKeyListener() {
		// @Override
		// public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TimeViewManager.this.validateTimeEditText(editText);
		// return false;
		// }
		// });
		// // date = date == null ? new Date() : date;
		// editText.setText(TimeUtil.dateToAppStringEx(date));
		// // editText.setEnabled(false);
		// editText.setOnClickListener(new TimeOnClickListener(editText,
		// context));
		this.rigistTimeAction(editText, context, date, null);
	}

	class TimeOnClickListener implements OnClickListener {
		private WheelView yearWheelView;
		private WheelView monthWheelView;
		private WheelView dayWheelView;
		private final EditText editText;
		private final Context context;
		private View wheelViewContainerView;
		private Builder builder;
		private final OnValidateListener onValidateListener;

		private void initWheelView() {
			wheelViewContainerView = LayoutInflater.from(context).inflate(
					ResourceUtil.getLayoutId(context, "time_dialog")
					// R.layout.time_dialog
					, null);
			yearWheelView = (WheelView) wheelViewContainerView
					.findViewById(ResourceUtil.getId(context,
							"track_datetime_year")
					// R.id.track_datetime_year
					);
			yearWheelView.setAdapter(new NumericWheelAdapter(2008, 2018));
			yearWheelView.setCyclic(true);
			// yearWheelView.TEXT_SIZE = 20;

			dayWheelView = (WheelView) wheelViewContainerView
					.findViewById(ResourceUtil.getId(context,
							"track_datetime_day")
					// R.id.track_datetime_day
					);
			dayWheelView.setCyclic(true);

			monthWheelView = (WheelView) wheelViewContainerView
					.findViewById(ResourceUtil.getId(context,
							"track_datetime_month")
					// R.id.track_datetime_month
					);
			monthWheelView.setAdapter(new NumericWheelAdapter(1, 12));
			monthWheelView.setCyclic(true);
			monthWheelView.addChangingListener(new OnWheelChangedListener() {
				@Override
				public void onChanged(WheelView wheel, int oldValue,
						int newValue) {
					// dayWheelView.setAdapter(new NumericWheelAdapter(1,
					// getDateNumOfYearAndMonth(
					// yearWheelView.getCurentItemValue(),
					// monthWheelView.getCurentItemValue())));
					// dayWheelView.invalidate();
					// dayWheelView.getAdapter().
					TimeOnClickListener.this.setDayWheelViewData();
				}
			});
			TimeOnClickListener.this.setDayWheelViewData();
		}

		private void setDayWheelViewData() {
			dayWheelView.setAdapter(new NumericWheelAdapter(1,
					getDateNumOfYearAndMonth(
							yearWheelView.getCurentItemValue(),
							monthWheelView.getCurentItemValue())));
			dayWheelView.setCurrentItem(0);
			// dayWheelView.invalidate();
		}

		public String getRightTimeString(String timeString) {
			Date date = TimeUtil.parseAppDateEx(editText.getText().toString());
			if (date != null) {
				return TimeUtil.dateToAppStringEx(date);
			} else {
				return "";
			}
		}

		private void initWheelViewData() {
			int year = 2013;
			int month = 5;
			int day = 1;
			Date date = TimeUtil.parseAppDateEx(editText.getText().toString());
			if (date != null) {
				String[] timeStrings = editText.getText().toString().split("-");
				try {
					year = Integer.parseInt(timeStrings[0]);
					month = Integer.parseInt(timeStrings[1]);
					day = Integer.parseInt(timeStrings[2]);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				// LogUtil.systemOut("year", year);
				// LogUtil.systemOut("month", month);
				// LogUtil.systemOut("day", day);
			} else {
				Calendar c = Calendar.getInstance();
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				// LogUtil.systemOut("year1", year);
				// LogUtil.systemOut("month1", month);
				// LogUtil.systemOut("day1", day);
			}
			yearWheelView.setCurrentItemValue(year);
			monthWheelView.setCurrentItemValue(month);
			dayWheelView.setCurrentItemValue(day);
		}

		private void initBuilder() {
			builder = new AlertDialog.Builder(context);
			// builder.setTitle("选择受理日期");
			builder.setView(wheelViewContainerView);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// int currentYear = yearWheelView.getCurrentItem()
							// + 2008;
							// int monthOfYear = monthWheelView.getCurrentItem()
							// + 1;
							// int dayOfMonth = dayWheelView.getCurrentItem() +
							// 1;
							editText.setText(yearWheelView.getCurentItemValue()
									+ "-"
									+ getTimeString(monthWheelView
											.getCurentItemValue())
									+ "-"
									+ getTimeString(dayWheelView
											.getCurentItemValue()));
							editText.setError(null);
							if (TimeOnClickListener.this.onValidateListener != null) {
								TimeOnClickListener.this.onValidateListener
										.onRight();
							}
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		}

		private String getTimeString(int time) {
			if (time < 10) {
				return "0" + time;
			} else {
				return "" + time;
			}
		}

		public TimeOnClickListener(EditText editText, Context context,
				OnValidateListener onValidateListener) {
			this.editText = editText;
			this.context = context;
			this.onValidateListener = onValidateListener;
		}

		private void init() {
			this.initWheelView();
			this.initWheelViewData();
			this.initBuilder();
		}

		public boolean isLeapYear(int year) {
			boolean isLeapYear = false;
			if (year % 100 == 0 && year % 400 == 0) {
				isLeapYear = true;
			} else if (year % 4 == 0) {
				isLeapYear = true;
			}
			return isLeapYear;
		}

		public int getDateNumOfYearAndMonth(int year, int month) {
			switch (month) {
			case 2:
				return this.isLeapYear(year) ? 29 : 28;
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			default:
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			}
		}

		@Override
		public void onClick(View v) {
			// int year = 2013;
			// int month = 5;
			// int day = 1;
			// Date date =
			// TimeUtil.parseAppDateEx(editText.getText().toString());
			// if (date != null) {
			// String[] timeStrings = editText.getText().toString().split("-");
			// try {
			// year = Integer.parseInt(timeStrings[0]);
			// month = Integer.parseInt(timeStrings[1]);
			// day = Integer.parseInt(timeStrings[2]);
			// } catch (Exception exception) {
			// exception.printStackTrace();
			// }
			// // LogUtil.systemOut("year", year);
			// // LogUtil.systemOut("month", month);
			// // LogUtil.systemOut("day", day);
			// } else {
			// Calendar c = Calendar.getInstance();
			// year = c.get(Calendar.YEAR);
			// month = c.get(Calendar.MONTH);
			// day = c.get(Calendar.DAY_OF_MONTH);
			// // LogUtil.systemOut("year1", year);
			// // LogUtil.systemOut("month1", month);
			// // LogUtil.systemOut("day1", day);
			// }
			// View mView = LayoutInflater.from(context).inflate(
			// R.layout.time_dialog, null);
			// yearWheelView = (WheelView) mView
			// .findViewById(R.id.track_datetime_year);
			// yearWheelView.setAdapter(new NumericWheelAdapter(2008, 2018));
			// yearWheelView.setCyclic(true);
			// yearWheelView.setCurrentItem(year - 2008);
			// // yearWheelView.TEXT_SIZE = 20;
			//
			// dayWheelView = (WheelView) mView
			// .findViewById(R.id.track_datetime_day);
			// dayWheelView.setAdapter(new NumericWheelAdapter(1, this
			// .getDateNumOfYearAndMonth(year, month)));
			// dayWheelView.setCyclic(true);
			// dayWheelView.setCurrentItem(day - 1);
			//
			// monthWheelView = (WheelView) mView
			// .findViewById(R.id.track_datetime_month);
			// monthWheelView.setAdapter(new NumericWheelAdapter(1, 12));
			// monthWheelView.setCyclic(true);
			// monthWheelView.setCurrentItem(month - 1);
			// monthWheelView.addChangingListener(new OnWheelChangedListener() {
			// @Override
			// public void onChanged(WheelView wheel, int oldValue,
			// int newValue) {
			// // dayWheelView.setAdapter(new NumericWheelAdapter(1,
			// // getDateNumOfYearAndMonth(year, month)));
			// }
			// });
			// monthWheelView.TEXT_SIZE = 20;
			// dayWheelView.TEXT_SIZE = 20;

			// Builder builder = new AlertDialog.Builder(context);
			// // builder.setTitle("选择受理日期");
			// builder.setView(wheelViewContainerView);
			// builder.setPositiveButton("确定",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// int currentYear = yearWheelView.getCurrentItem() + 2008;
			// int monthOfYear = monthWheelView.getCurrentItem() + 1;
			// int dayOfMonth = dayWheelView.getCurrentItem() + 1;
			// editText.setText(currentYear + "-"
			// + getTimeString(monthOfYear) + "-"
			// + getTimeString(dayOfMonth));
			// editText.setError(null);
			// }
			// });
			// builder.setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// }
			// });
			this.init();
			builder.show();
		}
	}
}