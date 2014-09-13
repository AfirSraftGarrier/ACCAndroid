/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.acc.android.frame.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

////import com.augurit.android.hfss.R;

public class TimeUtil {

	private static final String TAG = "Utils";

	/**
	 * 保存图片的文件夹
	 */
	public static String IMAGR_PATH = "image";

	/**
	 * SD卡根路径
	 */
	public static String SDCARD_ROOT = "";

	/**
	 * 项目存储文件的根路径
	 */
	public static String AUGUR_ROOT = "";

	static {

		File sdDir = null;
		// 判断SD卡是否存在
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			// 获取根目路径
			sdDir = Environment.getExternalStorageDirectory();
		}
		SDCARD_ROOT = sdDir == null ? "" : sdDir.toString();
		AUGUR_ROOT = SDCARD_ROOT + "/AGHFPDA/DATA";
		IMAGR_PATH = getDate() + "/" + IMAGR_PATH;
	}

	public static int getCardType(String type) {
		int num = 0;
		if (type.equals("身份证")) {
			num = 0;
		} else if (type.equals("驾驶证")) {
			num = 1;
		} else if (type.equals("护照")) {
			num = 2;
		} else if (type.equals("军人证")) {
			num = 3;
		}
		return num;
	}

	public static String getDate() {
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String formatTime = format.format(now);
		return formatTime;
	}

	public static String getNowDateString() {
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
		String nowDateString = dateFormat.format(nowDate);
		return nowDateString;
	}

	public static Date getDate(int dateIndex) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, dateIndex);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getDate(Date date, int offsetIndex) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, offsetIndex);
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	public static Date AddDate(Date date, int d) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(date);
		beginDate.set(Calendar.DATE, beginDate.get(Calendar.DATE) + d);
		Date endDate;
		try {

			endDate = dft.parse(dft.format(beginDate.getTime()));

		} catch (ParseException e) {
			return new Date();
		}
		return endDate;

	}

	/**
	 * 
	 * @param 添加必填项标识
	 */
	public static void ItemRequired(TextView text) {
		text.append("*");
		String strs = text.getText().toString();
		SpannableStringBuilder style = new SpannableStringBuilder(strs);
		// style.setSpan(new
		// BackgroundColorSpan(Color.RED),strs.length()-1,strs.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		style.setSpan(new ForegroundColorSpan(Color.RED), strs.length() - 1,
				strs.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		text.setText(style);
	}

	public static void MessageBox(Context context, String tile, String msg) {
		new AlertDialog.Builder(context).setTitle(tile).setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public static void MessageBox(Context context, String title) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View layout = inflater.inflate(R.layout.main, null);
		View titleView = inflater.inflate(
		// R.layout.title_dialog
				ResourceUtil.getLayoutId(context, "title_dialog"), null);// View.inflate(context,
																			// R.layout.title_dialog,
																			// null);
		TextView titleTextView = (TextView) titleView.findViewById(
		// R.id.title
				ResourceUtil.getId(context, "title"));
		titleTextView.setText(title);
		new AlertDialog.Builder(context)
				// .setTitle(tile)
				// .setMessage(title)
				.setCustomTitle(titleView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	private static HashMap<String, String> _userLinkMapping = new HashMap<String, String>();

	public static String stringifyStream(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

		return sb.toString();
	}

	public static final DateFormat TWITTER_FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	// Wed Dec 15 02:53:36 +0000 2010
	public static final DateFormat TWITTER_DATE_FORMATTER = new SimpleDateFormat(
			"yyyyMMddHHmm");

	public static final DateFormat TWITTER_SEARCH_API_DATE_FORMATTER = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分");

	public static final DateFormat TWITTER_SEARCH_API_DATE_FORMATTER_EX = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final DateFormat TWITTER_SIGN_DATE_FORMATTER = new SimpleDateFormat(
			"yyyy年MM月dd日");

	public static final String dateToFormString(Date date) {
		try {
			// Log.d(TAG, String.format("in dateToDBString, dateString=%s",
			// date.toLocaleString()));
			if (date == null)
				return "";
			else
				return TWITTER_FORMATTER.format(date);
		} catch (Exception e) {
			Log.w(TAG, "Could not format  date ");
			return null;
		}
	}

	public static final Date parseFormDateTime(String dateString) {
		try {
			// Log.d(TAG, String.format("in parseDateTime, dateString=%s",
			// dateString));
			if (dateString == null || dateString == "")
				return new Date();
			else
				return TWITTER_FORMATTER.parse(dateString);
		} catch (ParseException e) {
			Log.w(TAG, "Could not parse Twitter date string: " + dateString);
			return new Date();
		}
	}

	public static final String dateToDBString(Date date) {
		try {
			// Log.d(TAG, String.format("in dateToDBString, dateString=%s",
			// date.toLocaleString()));
			if (date == null)
				return "";
			else
				return TWITTER_DATE_FORMATTER.format(date);
		} catch (Exception e) {
			Log.w(TAG, "Could not format  date ");
			return null;
		}
	}

	public static final Date parseDateTime(String dateString) {
		try {
			// Log.d(TAG, String.format("in parseDateTime, dateString=%s",
			// dateString));
			if (dateString == null || dateString == "")
				return new Date();
			else
				return TWITTER_DATE_FORMATTER.parse(dateString);
		} catch (ParseException e) {
			Log.w(TAG, "Could not parse Twitter date string: " + dateString);
			return new Date();
		}
	}

	// // Handle "yyyy-MM-dd'T'HH:mm:ss.SSS" from sqlite
	// public static final Date parseDateTimeFromSqlite(String dateString) {
	// try {
	// Log.d(TAG, String.format("in parseDateTime, dateString=%s", dateString));
	// return TwitterDatabase.DB_DATE_FORMATTER.parse(dateString);
	// } catch (ParseException e) {
	// Log.w(TAG, "Could not parse Twitter date string: " + dateString);
	// return null;
	// }
	// }

	public static final Date parseAppDateTime(String dateString) {
		try {
			return TWITTER_SEARCH_API_DATE_FORMATTER.parse(dateString);
		} catch (ParseException e) {
			// Log.w(TAG, "Could not parse Twitter search date string: " +
			// dateString);
			return null;
		}
	}

	public static final String dateToAppString(Date date) {
		try {
			// Log.d(TAG, String.format("in dateToDBString, dateString=%s",
			// date.toLocaleString()));
			if (date == null)
				return "";
			else
				return TWITTER_SEARCH_API_DATE_FORMATTER.format(date);
		} catch (Exception e) {
			// Log.w(TAG, "Could not format  date " );
			return null;
		}
	}

	public static final String dateToAppStringEx(Date date) {
		try {
			// Log.d(TAG, String.format("in dateToDBString, dateString=%s",
			// date.toLocaleString()));
			if (date == null)
				return "";
			else
				return TWITTER_SEARCH_API_DATE_FORMATTER_EX.format(date);
		} catch (Exception e) {
			// Log.w(TAG, "Could not format  date " );
			return null;
		}
	}

	public static final Date parseAppDateEx(String dateString) {
		try {
			return TWITTER_SEARCH_API_DATE_FORMATTER_EX.parse(dateString);
		} catch (ParseException e) {
			// Log.w(TAG, "Could not parse Twitter search date string: " +
			// dateString);
			return null;
		}
	}

	public static final Date parseSignDateTime(String dateString) {
		try {
			return TWITTER_SIGN_DATE_FORMATTER.parse(dateString);
		} catch (ParseException e) {
			// Log.w(TAG, "Could not parse Twitter search date string: " +
			// dateString);
			return null;
		}
	}

	public static final String dateToSignString(Date date) {
		try {
			// Log.d(TAG, String.format("in dateToDBString, dateString=%s",
			// date.toLocaleString()));
			if (date == null)
				return "";
			else
				return TWITTER_SIGN_DATE_FORMATTER.format(date);
		} catch (Exception e) {
			// Log.w(TAG, "Could not format  date " );
			return null;
		}
	}

	public static long getNowTime() {
		return Calendar.getInstance().getTime().getTime();
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 把Bitmap转Byte
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * @param 将字节数组转换为ImageView可调用的Bitmap对象
	 * @param bytes
	 * @return Bitmap
	 */
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null) {
			if (opts != null) {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			} else {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			}
		}
		return null;
	}

	// private static final Pattern NAME_MATCHER = Pattern.compile("@.+?\\s");
	// private static final Linkify.MatchFilter NAME_MATCHER_MATCH_FILTER = new
	// Linkify.MatchFilter() {
	// @Override
	// public final boolean acceptMatch(final CharSequence s, final int start,
	// final int end) {
	//
	// String name = s.subSequence(start+1, end).toString().trim();
	// boolean result = _userLinkMapping.containsKey(name);
	// return result;
	// }
	// };
	//
	// private static final Linkify.TransformFilter
	// NAME_MATCHER_TRANSFORM_FILTER = new Linkify.TransformFilter() {
	//
	// @Override
	// public String transformUrl(Matcher match, String url) {
	// // TODO Auto-generated method stub
	// String name = url.subSequence(1, url.length()).toString().trim();
	// return _userLinkMapping.get(name);
	// }
	// };
	//
	// private static final String TWITTA_USER_URL = "twitta://users/";
	//
	// public static void linkifyUsers(TextView view) {
	// Linkify.addLinks(view, NAME_MATCHER, TWITTA_USER_URL,
	// NAME_MATCHER_MATCH_FILTER, NAME_MATCHER_TRANSFORM_FILTER);
	// }
	//
	// private static final Pattern TAG_MATCHER = Pattern.compile("#\\w+#");
	//
	// private static final Linkify.TransformFilter TAG_MATCHER_TRANSFORM_FILTER
	// =
	// new Linkify.TransformFilter() {
	// @Override
	// public final String transformUrl(Matcher match, String url) {
	// String result = url.substring(1, url.length()-1);
	// return "%23" + result + "%23";
	// }
	// };
	//
	// private static final String TWITTA_SEARCH_URL = "twitta://search/";
	//
	// public static void linkifyTags(TextView view) {
	// Linkify.addLinks(view, TAG_MATCHER, TWITTA_SEARCH_URL,
	// null, TAG_MATCHER_TRANSFORM_FILTER);
	// }
	//
	// public static boolean isTrue(Bundle bundle, String key) {
	// return bundle != null && bundle.containsKey(key) &&
	// bundle.getBoolean(key);
	// }
	//
	// private static Pattern USER_LINK =
	// Pattern.compile("@<a href=\"http:\\/\\/fanfou\\.com\\/(.*?)\" class=\"former\">(.*?)<\\/a>");
	// public static String preprocessText(String text){
	// //处理HTML格式返回的用户链接
	// Matcher m = USER_LINK.matcher(text);
	// while(m.find()){
	// _userLinkMapping.put(m.group(2), m.group(1));
	// Log.d(TAG, String.format("Found mapping! %s=%s", m.group(2),
	// m.group(1)));
	// }
	//
	// //将User Link的连接去掉
	// StringBuffer sb = new StringBuffer();
	// m = USER_LINK.matcher(text);
	// while(m.find()){
	// m.appendReplacement(sb, "@$2");
	// }
	// m.appendTail(sb);
	// return sb.toString();
	// }
	//
	// public static String getSimpleTweetText(String text){
	// return text.replaceAll("<.*?>", "")
	// .replace("&lt;", "<")
	// .replace("&gt;", ">")
	// .replace("&nbsp;", " ")
	// .replace("&amp;", "&")
	// .replace("&quot;", "\"");
	// }
	//
	// public static void setSimpleTweetText(TextView textView, String text){
	// String processedText = getSimpleTweetText(text);
	// textView.setText(processedText);
	// }
	//
	// public static void setTweetText(TextView textView, String text) {
	// String processedText = preprocessText(text);
	// textView.setText(Html.fromHtml(processedText), BufferType.SPANNABLE);
	// Linkify.addLinks(textView, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
	// Utils.linkifyUsers(textView);
	// Utils.linkifyTags(textView);
	// _userLinkMapping.clear();
	// }
	//
	//
	// public static Bitmap drawableToBitmap(Drawable drawable) {
	// Bitmap bitmap = Bitmap
	// .createBitmap(
	// drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight(),
	// drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
	// : Bitmap.Config.RGB_565);
	// Canvas canvas = new Canvas(bitmap);
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// drawable.draw(canvas);
	// return bitmap;
	// }
	//
	// private static Pattern PHOTO_PAGE_LINK =
	// Pattern.compile("http://fanfou.com(/photo/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|])");
	// private static Pattern PHOTO_SRC_LINK =
	// Pattern.compile("src=\"(http:\\/\\/photo\\.fanfou\\.com\\/.*?)\"");
	// /**
	// * 获得消息中的照片页面链接
	// * @param text 消息文本
	// * @param size 照片尺寸
	// * @return 照片页面的链接，若不存在，则返回null
	// */
	// // public static String getPhotoPageLink(String text, String size){
	// // Matcher m = PHOTO_PAGE_LINK.matcher(text);
	// // if(m.find()){
	// // String THUMBNAIL=TwitterApplication.mContext
	// // .getString(R.string.pref_photo_preview_type_thumbnail);
	// // String MIDDLE=TwitterApplication.mContext
	// // .getString(R.string.pref_photo_preview_type_middle);
	// // String ORIGINAL=TwitterApplication.mContext
	// // .getString(R.string.pref_photo_preview_type_original);
	// // if (size.equals(THUMBNAIL) || size.equals(MIDDLE)){
	// // return "http://m.fanfou.com" + m.group(1);
	// // }else if (size.endsWith(ORIGINAL)){
	// // return m.group(0);
	// // }else{
	// // return null;
	// // }
	// // }else{
	// // return null;
	// // }
	// // }
	//
	// /**
	// * 获得照片页面中的照片链接
	// * @param pageHtml 照片页面文本
	// * @return 照片链接，若不存在，则返回null
	// */
	// public static String getPhotoURL(String pageHtml){
	// Matcher m = PHOTO_SRC_LINK.matcher(pageHtml);
	// if(m.find()){
	// return m.group(1);
	// }else{
	// return null;
	// }
	// }
	/**
	 * 获取相对时间(将给点的时间变换成相对于系统当前时间的差值)，格式为“XX分钟前”
	 * 
	 * @return
	 */
	public static String getRelativeTime(String date) {
		Log.i("TimeUtil", "date=" + date);
		String time = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt1 = sdf.parse(date);

			Calendar cl = Calendar.getInstance();
			int year2 = cl.get(Calendar.YEAR);
			int month2 = cl.get(Calendar.MONTH);
			int day2 = cl.get(Calendar.DAY_OF_MONTH);
			int hour2 = cl.get(Calendar.HOUR_OF_DAY);
			int minute2 = cl.get(Calendar.MINUTE);
			int second2 = cl.get(Calendar.SECOND);

			cl.setTime(dt1);
			int year1 = cl.get(Calendar.YEAR);
			int month1 = cl.get(Calendar.MONTH);
			int day1 = cl.get(Calendar.DAY_OF_MONTH);
			int hour1 = cl.get(Calendar.HOUR_OF_DAY);
			int minute1 = cl.get(Calendar.MINUTE);
			int second1 = cl.get(Calendar.SECOND);

			if (year1 == year2) {
				if (month1 == month2) {
					if (day1 == day2) {
						if (hour1 == hour2) {
							if (minute1 == minute2) {
								time = "刚才";
							} else {
								if (minute2 - minute1 < 0) {
									time = "刚才";
								} else {
									time = (minute2 - minute1) + "分钟前";
								}

							}
						} else if (hour2 - hour1 > 3) {
							time = formatTime(hour1, minute1);
						} else if (hour2 - hour1 == 1) {
							if (minute2 - minute1 > 0) {
								time = "1小时前";
							} else {
								time = (60 + minute2 - minute1) + "分钟前";
							}
						} else {
							time = (hour2 - hour1) + "小时前";
						}
					} else if (day2 - day1 == 1) { // 昨天
						if (hour1 > 12) {
							time = (month1 + 1) + "月" + day1 + "日  下午";
						} else {
							time = (month1 + 1) + "月" + day1 + "日  上午";
						}
					} else {
						time = (month1 + 1) + "月" + day1 + "日";
					}
				} else {
					time = (month1 + 1) + "月" + day1 + "日";
				}
			} else {
				time = year1 + "年" + month1 + "月" + day1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	private static String formatTime(int hour, int minute) {
		String time = "";
		if (hour < 10) {
			time += "0" + hour + ":";
		} else {
			time += hour + ":";
		}

		if (minute < 10) {
			time += "0" + minute;
		} else {
			time += minute;
		}
		System.out.println("format(hour, minute)=" + time);
		return time;
	}
}
