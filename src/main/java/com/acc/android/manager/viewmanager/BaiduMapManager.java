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
package com.acc.android.manager.viewmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.acc.android.manager.MLocationManager;
import com.acc.android.model.BaiduMapData;
import com.acc.android.model.MGeoData;
import com.acc.android.util.ResourceUtil;
import com.acc.android.util.asynctask.BasicAsyncTask;
import com.acc.android.util.view.MMapView;
import com.acc.java.model.GeoData;
import com.acc.java.model.GeoDataWithoutAddress;
import com.acc.java.model.MGeoPoint;
import com.acc.java.util.ListUtil;
import com.acc.java.util.callback.LocationCallback;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
//import com.augurit.android.hfss.R;

public class BaiduMapManager {
	private MMapView mMapView;
	private MKSearch mkSearch;
	// private Button mClearBtn;
	// private Button mResetBtn;
	private MapController mapController;
	private MOverlay myOverlay;
	private PopupOverlay popupOverlay;
	// private ArrayList<OverlayItem> overlayItems;
	// private OverlayItem mCurItem = null;
	// private Long taskId;
	// private TaskType taskType;
	// private TaskData taskData;
	// private List<Task> tasks;
	private List<GeoPoint> geoPoints;
	// private String taskTittle;
	private View popupView;
	// private TextView descriptionTextView;
	// private TextView stateTextView;
	// private TextView createTimeTextView;
	private int currentPopupIndex;
	private int popupHeight;
	// private Drawable doneDrawale;
	// private Drawable noDoneYetDrawable;
	private Drawable defaultDrawable;
	private boolean isUsePopup = false;
	private boolean isEnable = true;

	private MapEventLisener mapEventListener;

	public interface MapEventLisener {
		void onClick(GeoPoint geoPoint);

		void onLongClick(GeoPoint geoPoint);

		void onGetAddress(String address);

		void onGetGeo(GeoPoint geoPoint);
	}

	public void setMapEventListener(MapEventLisener mapEventListener) {
		this.mapEventListener = mapEventListener;
	}

	public void setUsePopup(boolean isUsePopup) {
		this.isUsePopup = isUsePopup;
	}

	public void setDefaultDrawable(Drawable defaultDrawable) {
		this.defaultDrawable = defaultDrawable;
	}

	// private TextView submitButton;
	// private TextView backButton;

	// private List<TaskPeople> taskPeoples;
	// private String[] taskPeopleNames;
	// private int chooseTaskPeopleIndex;

	public BaiduMapManager(Activity activity, BMapManager bMapManager,
			Integer defaultZoom, GeoPoint defaultCenterGeoPoint) {
		this.initMapView(activity, bMapManager, defaultZoom,
				defaultCenterGeoPoint);
	}

	// public void regist(Activity activity) {
	// try {
	// this.relocationButton = (Button) activity
	// .findViewById(R.id.button_location);
	// this.relocationButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// LocationViewManager.this.relocation();
	// }
	// });
	// this.gpsNoteTextView = (TextView)
	// activity.findViewById(R.id.gps_note);
	// this.gpsAccuracyTextView = (TextView) activity
	// .findViewById(R.id.gps_accuracy);
	// this.gpsAddressEditText = (EditText) activity
	// .findViewById(R.id.base_info_location);
	// // } catch (Exception exception) {
	// // exception.printStackTrace();
	// // }
	// // GPSCallback gpsCallback = new GPSCallback() {
	// //
	// // @Override
	// // public void receive(String address) {
	// // }
	// //
	// // @Override
	// // public void receive(GeoData geoData) {
	// // }
	// //
	// // @Override
	// // public void receive(GeoDataWithoutAddress geoPoint) {
	// // }
	// // };
	// this.mLocationManager.rigist(this);
	// this.mLocationManager.start();
	// this.relocation();
	// }

	// private// private TextView popupText;
	// private View viewCache;
	// private View popupInfo;
	// private View popupLeft;
	// private View popupRight;
	// private View button;
	// private final MapView.LayoutParams layoutParam = null;
	// double mLon1 = 116.400244;
	// double mLat1 = 39.963175;
	// double mLon2 = 116.369199;
	// double mLat2 = 39.942821;
	// double mLon3 = 116.425541;
	// double mLat3 = 39.939723;
	// double mLon4 = 116.401394;
	// double mLat4 = 39.906965;
	// ground overlay
	// private GroundOverlay mGroundOverlay;
	// private Ground mGround;
	// private final double mLon5 = 116.380338;
	// private final double mLat5 = 39.92235;
	// private final double mLon6 = 116.414977;
	// private final double mLat6 = 39.947246;
	// private void initBundleData() {
	// Bundle bundle = this.getIntent().getExtras();
	// // this.taskId = bundle.getLong(BundleKeyConstant.TASK_ID);
	// // this.taskType = TaskUtil.getTaskType(this);
	// this.taskData = TaskUtil.getTaskData(this);
	// this.taskTittle = TaskUtil.getTaskTittle(this.taskData.getTaskType(),
	// this);
	// // bundle.putLong(BundleKeyConstant.TASK_ID, taskId);
	// // bundle.putInt(BundleKeyConstant.TASK_TYPE,
	// // TaskListActivity.this.taskType.ordinal());
	// }

	// private void getTaskPeopleFromNet() {
	// // listItem.clear();
	// new GetDataFromNetAsyncTask<List<TaskPeople>, String>(this,
	// "浠诲姟杞彂浜�"
	// // + "鍒楄〃"
	// ,
	// new GetDataFromNetAsyncTaskListener<List<TaskPeople>, String>() {
	// @Override
	// public List<TaskPeople> getResult(String... strings) {
	// // return NetworkHelper.getInstance(MyTask.this)
	// // .getHttpOpera(CcProblemHttpOpera.class)
	// // .getSuperviseCcProblem();
	// // TaskRequest taskRequest = new TaskRequest();
	// // taskRequest.setUserName(CurrentUser.getInstance()
	// // .getCurrentUser().getLoginName());
	// // taskRequest
	// // .setTaskType(TaskBaiduMapActivity.this.taskType);
	// // Task parentTask = new Task();
	// // parentTask.setTaskId(TaskBaiduMapActivity.this.taskData
	// // .getTaskId());
	// return NetworkHelper
	// .getInstance(BaiduMapManager.this)
	// .getHttpOpera(TaskOperator.class)
	// .getTaskPeoples(
	// CurrentUser.getInstance()
	// .getCurrentUser().getUserId());
	// // .getByLoginNameOfRecheck(
	// // CurrentUser.getInstance()
	// // .getCurrentUser()
	// // .getLoginName()
	// // )
	// // ;
	// }
	//
	// @Override
	// public void onSuccess(List<TaskPeople> taskPeoples) {
	// BaiduMapManager.this.taskPeoples = taskPeoples;
	// if (!ListUtil
	// .isEmpty(BaiduMapManager.this.taskPeoples)) {
	// BaiduMapManager.this.taskPeopleNames = new
	// String[BaiduMapManager.this.taskPeoples
	// .size()];
	// for (int i = 0; i < BaiduMapManager.this.taskPeoples
	// .size(); i++) {
	// // TaskPeople taskPeople :
	// // TaskBaiduMapActivity.this.taskPeoples)
	// BaiduMapManager.this.taskPeopleNames[i] =
	// BaiduMapManager.this.taskPeoples
	// .get(i).getUserName();
	// }
	// }
	// // TaskBaiduMapActivity.this.updateMapView(tasks);
	// // if (TaskBaiduMapActivity.this.taskData.getTaskType()
	// // == TaskType.DELIVERY) {
	// // TaskBaiduMapActivity.this.getTaskPeopleFromNet();
	// // }
	// // TaskListActivity.this.updateTaskListView(tasks);
	// }
	//
	// @Override
	// public void onFail() {
	// }
	// }).execute();
	// }

	// private void getTasksFromNet() {
	// new GetDataFromNetAsyncTask<List<Task>, String>(this, this.taskTittle
	// // + "鍒楄〃"
	// , new GetDataFromNetAsyncTaskListener<List<Task>, String>() {
	// @Override
	// public List<Task> getResult(String... strings) {
	// // return NetworkHelper.getInstance(MyTask.this)
	// // .getHttpOpera(CcProblemHttpOpera.class)
	// // .getSuperviseCcProblem();
	// // TaskRequest taskRequest = new TaskRequest();
	// // taskRequest.setUserName(CurrentUser.getInstance()
	// // .getCurrentUser().getLoginName());
	// // taskRequest
	// // .setTaskType(TaskBaiduMapActivity.this.taskType);
	// Task parentTask = new Task();
	// parentTask.setTaskId(BaiduMapManager.this.taskData
	// .getTaskId());
	// return NetworkHelper
	// .getInstance(BaiduMapManager.this)
	// .getHttpOpera(TaskOperator.class)
	// .getChildWithLocation(parentTask)
	// // .getByLoginNameOfRecheck(
	// // CurrentUser.getInstance()
	// // .getCurrentUser()
	// // .getLoginName()
	// // )
	// ;
	// }
	//
	// @Override
	// public void onSuccess(List<Task> tasks) {
	// // tasks.remove(0);
	// BaiduMapManager.this.updateMapView(tasks);
	// if (BaiduMapManager.this.taskData.getTaskType() == TaskType.DELIVERY) {
	// BaiduMapManager.this.getTaskPeopleFromNet();
	// }
	// // TaskListActivity.this.updateTaskListView(tasks);
	// }
	//
	// @Override
	// public void onFail() {
	// }
	// }).execute();
	// }

	// private void initTaskTittle() {
	// switch (taskType) {
	// case INSPECT_RECHECT:
	// default:
	// this.taskTittle = this.getString(R.string.item_recheck);
	// break;
	// case MAINTAINCE:
	// this.taskTittle = this.getString(R.string.item_maintaince);
	// break;
	// case DELIVERY:
	// this.taskTittle = this.getString(R.string.item_task_delivery);
	// break;
	// case MAINTAINCE_RECHECK:
	// this.taskTittle = this.getString(R.string.item_maintaince_recheck);
	// break;
	// }
	// }

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // super.onCreate(savedInstanceState);
	// // BaiduMapApplication baiduMapApplication = (BaiduMapApplication) this
	// // .getApplication();
	// // this.setContentView(R.layout.task_baidu_map);
	// // this.initBundleData();
	// // this.setTitle(this.taskTittle + "鍦板浘");
	// this.initMapView();
	// this.initActionButton();
	// // this.mapView.getProjection().
	// // initOverlay();
	// // GeoPoint geoPoint = new GeoPoint((int) (39.933859 * 1E6),
	// // (int) (116.400191 * 1E6));
	// // // mapController.setCenter(geoPoint);
	// // mapController.animateTo(geoPoint);
	// this.getTasksFromNet();
	// }

	// private void testToPixels() {
	// Point point = new Point();
	// this.mapView.getProjection().toPixels(this.mapView.getMapCenter(),
	// point);
	// LogUtil.systemOut(point);
	// LogUtil.systemOut(this.mapView.getMapCenter());
	// GeoPoint geoPoint = this.mapView.getMapCenter();
	// geoPoint.setLatitudeE6(geoPoint.getLatitudeE6() + 400000);
	// this.mapView.getProjection().toPixels(geoPoint, point);
	// LogUtil.systemOut(point);
	// LogUtil.systemOut(geoPoint);
	// }

	// private void initActionButton() {
	// this.backButton = (TextView) this.findViewById(R.id.footer_btn_back);
	// this.submitButton = (TextView) this
	// .findViewById(R.id.footer_btn_upbook);
	// this.submitButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// if (false) {
	// BaiduMapManager.this.testToPixels();
	// return;
	// }
	// if (BaiduMapManager.this.taskData.getTaskType() == TaskType.DELIVERY) {
	// BaiduMapManager.this.showTaskPeopleChooseDialog();
	// } else {
	// BaiduMapManager.this.submit(true);
	// }
	// // if (TaskBaiduMapActivity.this.taskData.getTaskType() ==
	// // TaskType.MAINTAINCE
	// // || TaskBaiduMapActivity.this.taskData.getTaskType() ==
	// // TaskType.INSPECT_RECHECT
	// // || TaskBaiduMapActivity.this.taskData.getTaskType() ==
	// // TaskType.MAINTAINCE_RECHECK) {
	// //
	// // }
	// }
	// });
	// if (BaiduMapManager.this.taskData.getTaskType() == TaskType.DELIVERY) {
	// this.submitButton.setText("杞彂");
	// }
	// // if (TaskBaiduMapActivity.this.taskData.getTaskType() ==
	// // TaskType.INSPECT_RECHECT) {
	// // this.submitButton.setText("浠诲姟鎻愪氦");
	// // }
	// if (BaiduMapManager.this.taskData.getTaskType() ==
	// TaskType.MAINTAINCE_RECHECK) {
	// this.backButton.setVisibility(View.VISIBLE);
	// this.backButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// BaiduMapManager.this.showSentBackDialog();
	// }
	// });
	// }
	// }

	// private void showSentBackDialog() {
	// LayoutInflater inflater = (LayoutInflater) this
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// // View layout = inflater.inflate(R.layout.main, null);
	// View titleView = inflater.inflate(R.layout.title_dialog, null);//
	// View.inflate(context,
	// // R.layout.title_dialog,
	// // null);
	// TextView titleTextView = (TextView) titleView.findViewById(R.id.title);
	// titleTextView.setText(R.string.note_please_write_sent_back_message);
	//
	// // LayoutInflater inflater = (LayoutInflater) this
	// // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// // View layout = inflater.inflate(R.layout.main, null);
	// View contentView = inflater.inflate(R.layout.description, null);//
	// View.inflate(context,
	// final EditText descriptionEditText = (EditText) contentView
	// .findViewById(R.id.description);
	// // CheckBox notNoteAgain = (CheckBox) contentView
	// // .findViewById(R.id.not_note_again);
	// // notNoteAgain.setOnCheckedChangeListener(new OnCheckedChangeListener()
	// // {
	// // @Override
	// // public void onCheckedChanged(CompoundButton buttonView,
	// // boolean isChecked) {
	// // setting.edit()
	// // .putBoolean(MainMenu.this.isShowGPSNOTEKey, !isChecked)
	// // .commit();
	// // }
	// // });
	// // R.layout.title_dialog,
	// // null);
	// // TextView titleTextView = (TextView) titleView
	// // .findViewById(R.id.title);
	// // titleTextView.setText(R.string.gps_improve);
	// AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	// BaiduMapManager.this);
	// alertDialog
	// // .setTitle("閫�鍑烘彁绀�")
	// .setCustomTitle(titleView)
	// // .setMessage(R.string.exit_app)
	// .setView(contentView)
	// .setPositiveButton(R.string.sure,
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// String sentBackMessage = descriptionEditText
	// .getText().toString();
	// SubmitData submitData = new SubmitData();
	// submitData.setDescription(sentBackMessage);
	// submitData.setPass(false);
	// BaiduMapManager.this.submit(submitData);
	// // // // ********鍏抽棴浠诲姟鐩戝惉鏈嶅姟
	// // stopService(new Intent(MainMenu.this,
	// // TaskService.class));
	// // stopService(new Intent(MainMenu.this,
	// // GPSService.class));
	// // // stopService(new
	// // Intent(MainMenu.this,
	// // // ClockService.class));
	// // SqliteTemplate.closeDatabase();
	// // ClockIn.stopTimer();
	// // MainMenu.this.finish();
	// // MainMenu.this.stopUpdateLocation();
	// // System.exit(0);
	// // Intent intent = new Intent(
	// // Settings.ACTION_SECURITY_SETTINGS);
	// // startActivityForResult(intent, 0);
	// // Intent intent = new Intent();
	// // intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	// // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// // try {
	// // MainMenu.this.startActivity(intent);
	// // } catch (ActivityNotFoundException ex) {
	// // // The Android SDK doc says that the
	// // // location settings activity
	// // // may not be found. In that case
	// // // show
	// // // the general settings.
	// // // General settings activity
	// // intent.setAction(Settings.ACTION_SETTINGS);
	// // try {
	// // MainMenu.this.startActivity(intent);
	// // } catch (Exception e) {
	// // }
	// // }
	// }
	// })
	// .setNegativeButton(R.string.cancel,
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// }
	// })
	// // .setNeutralButton("鏈�灏忓寲",
	// // new DialogInterface.OnClickListener() {
	// //
	// // @Override
	// // public void onClick(DialogInterface dialog,
	// // int which) {
	// // Intent intent = new Intent();
	// // intent.setAction("android.intent.action.MAIN");
	// // intent.addCategory("android.intent.category.HOME");
	// // MainMenu.this.startActivity(intent);
	// // }
	// // })
	// .setCancelable(true).create().show();
	// }
	//
	// private void submit(SubmitData submitData) {
	// submitData.setTaskId(this.taskData.getTaskId());
	// new CommonUploadDataToNetAsyncTask<SubmitData>(this, "姝ｅ湪鎻愪氦浠诲姟涓�...",
	// "鎻愪氦浠诲姟鎴愬姛", "鎻愪氦浠诲姟澶辫触",
	// new CommonUploadDataToNetAsyncTaskListener<SubmitData>() {
	//
	// @Override
	// public Response doUpload(SubmitData... params) {
	// return NetworkHelper
	// .getInstance(BaiduMapManager.this)
	// .getHttpOpera(TaskOperator.class)
	// .submit(params[0]);
	// }
	//
	// @Override
	// public void onSuccess() {
	// BaiduMapManager.this.finish();
	// }
	//
	// @Override
	// public void onFail() {
	//
	// }
	// }).execute(submitData);
	// }

	// private void submit() {
	//
	// }

	// private void submit(boolean isForward
	// // , SubmitData submitData
	// ) {
	// SubmitData submitData = new SubmitData();
	// submitData.setTaskId(this.taskData.getTaskId());
	// submitData.setPass(isForward);
	// this.submit(submitData);
	// // new CommonUploadDataToNetAsyncTask<SubmitData>(this, "姝ｅ湪鎻愪氦浠诲姟涓�...",
	// // "鎻愪氦浠诲姟鎴愬姛", "鎻愪氦浠诲姟澶辫触",
	// // new CommonUploadDataToNetAsyncTaskListener<SubmitData>() {
	// //
	// // @Override
	// // public Response doUpload(SubmitData... params) {
	// // return NetworkHelper
	// // .getInstance(TaskBaiduMapActivity.this)
	// // .getHttpOpera(TaskOperator.class)
	// // .submit(params[0]);
	// // }
	// //
	// // @Override
	// // public void onSuccess() {
	// // TaskBaiduMapActivity.this.finish();
	// // }
	// //
	// // @Override
	// // public void onFail() {
	// //
	// // }
	// // }).execute(submitData);
	// }
	//
	// private void showTaskPeopleChooseDialog() {
	// Builder builder = new android.app.AlertDialog.Builder(this);
	// // builder.setIcon(R.drawable.header);
	// builder.setTitle("璇烽�夋嫨瑕佽浆鍙戝埌鐨勪汉");
	// // String[] names = TaskBaiduMapActivity.this.taskPeopleNames.to;
	// builder.setSingleChoiceItems(this.taskPeopleNames, 0,
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// BaiduMapManager.this.chooseTaskPeopleIndex = which;
	// }
	// });
	//
	// builder.setPositiveButton(" 纭� 瀹� ",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// BaiduMapManager.this.onSelectTaskPeople();
	// }
	// });
	// builder.create().show();
	// }

	// private void onSelectTaskPeople() {
	// BaiduMapManager.this.taskPeoples.get(this.chooseTaskPeopleIndex);
	// DeliveryData deliveryData = new DeliveryData();
	// deliveryData.setTaskId(this.taskData.getTaskId());
	// deliveryData.setUserId(this.taskPeoples.get(this.chooseTaskPeopleIndex)
	// .getUserCode());
	// new CommonUploadDataToNetAsyncTask<DeliveryData>(this, "姝ｅ湪杞彂浠诲姟涓�...",
	// "杞彂浠诲姟鎴愬姛", "杞彂浠诲姟澶辫触",
	// new CommonUploadDataToNetAsyncTaskListener<DeliveryData>() {
	//
	// @Override
	// public Response doUpload(DeliveryData... params) {
	// return NetworkHelper
	// .getInstance(BaiduMapManager.this)
	// .getHttpOpera(TaskOperator.class)
	// .delivery(params[0]);
	// }
	//
	// @Override
	// public void onSuccess() {
	// BaiduMapManager.this.finish();
	// }
	//
	// @Override
	// public void onFail() {
	//
	// }
	// }).execute(deliveryData);
	// }

	// public void setZoomEnable(View v) {
	// this.mapController.setZoomGesturesEnabled(((CheckBox) v).isChecked());
	// }
	//
	// public void setScrollEnable(View v) {
	// this.mapController.setScrollGesturesEnabled(((CheckBox) v).isChecked());
	// }
	//
	// public void setRotateEnable(View v) {
	// this.mapController.setRotationGesturesEnabled(((CheckBox) v)
	// .isChecked());
	// }
	//
	// public void setOverlookEnable(View v) {
	// this.mapController.setOverlookingGesturesEnabled(((CheckBox) v)
	// .isChecked());
	// }
	//
	// public void setBuiltInZoomControllEnable(View v) {
	// this.mMapView.setBuiltInZoomControls(((CheckBox) v).isChecked());
	// }
	//
	// public void setDoubleClickEnable(View v) {
	// this.mMapView.setDoubleClickZooming(((CheckBox) v).isChecked());
	// }

	public void disableMapAction() {
		this.isEnable = false;
		this.mapController.setOverlookingGesturesEnabled(false);
		this.mapController.setRotateWithTouchEventCenterEnabled(false);
		this.mapController.setRotationGesturesEnabled(false);
		this.mapController.setScrollGesturesEnabled(false);
		this.mapController.setZoomGesturesEnabled(false);
		this.mMapView.setClickable(false);
		// this.mMapView.setLongClickable(false);
		this.mMapView.setDoubleClickZooming(false);
	}

	public void enableMapAction() {
		this.isEnable = true;
		this.mapController.setOverlookingGesturesEnabled(true);
		this.mapController.setRotateWithTouchEventCenterEnabled(true);
		this.mapController.setRotationGesturesEnabled(true);
		this.mapController.setScrollGesturesEnabled(true);
		this.mapController.setZoomGesturesEnabled(true);
		this.mMapView.setDoubleClickZooming(true);
		this.mMapView.setClickable(true);
		// this.mMapView.setLongClickable(true);
	}

	public void reverseGeocode(GeoPoint geoPoint) {
		this.mkSearch.reverseGeocode(geoPoint);
	}

	public void geocode(String addr, String city) {
		this.mkSearch.geocode(addr, city);
	}

	private void initMapView(final Activity activity, BMapManager bMapManager,
			Integer defaultZoom, GeoPoint defaultCenterGeoPoint) {
		this.mMapView = (MMapView) activity.findViewById(ResourceUtil.getId(
				activity, "bmapView")
		// R.id.bmapView
				);
		this.mMapView.setVisibility(View.VISIBLE);
		this.mkSearch = new MKSearch();
		this.mkSearch.init(bMapManager, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			@Override
			public void onGetAddrResult(MKAddrInfo mkAddrInfo, int error) {
				if (error != 0) {
					// String str = String.format("閿欒鍙凤細%d", error);
					// Toast.makeText(GeoCoderDemo.this, str,
					// Toast.LENGTH_LONG)
					// .show();
					return;
				}
				// List<BaiduMapData> baiduMapDatas = new
				// ArrayList<BaiduMapData>();
				// BaiduMapData baiduMapData = new BaiduMapData();
				// MGeoPoint mGeoPoint = new MGeoPoint();
				// mGeoPoint.setLatitude(mkAddrInfo.geoPt.getLatitudeE6()
				// / 10E6);
				// mGeoPoint.setLongitude(mkAddrInfo.geoPt
				// .getLongitudeE6() / 10E6);
				// baiduMapData.setmGeoPoint(mGeoPoint);
				// baiduMapData.setDrawable(activity.getResources()
				// .getDrawable(R.drawable.search_location_32));
				// baiduMapDatas.add(baiduMapData);
				// 鍦板浘绉诲姩鍒拌鐐�
				// BaiduMapManager.this.mapController
				// .animateTo(mkAddrInfo.geoPt);
				if (mkAddrInfo.type == MKAddrInfo.MK_GEOCODE) {
					// 鍦扮悊缂栫爜锛氶�氳繃鍦板潃妫�绱㈠潗鏍囩偣
					// String strInfo = String.format("绾害锛�%f 缁忓害锛�%f",
					// res.geoPt.getLatitudeE6() / 1e6,
					// res.geoPt.getLongitudeE6() / 1e6);
					if (BaiduMapManager.this.mapEventListener != null) {
						BaiduMapManager.this.mapEventListener
								.onGetGeo(mkAddrInfo.geoPt);
					}
					// Toast.makeText(GeoCoderDemo.this, strInfo,
					// Toast.LENGTH_LONG).show();
				}
				if (mkAddrInfo.type == MKAddrInfo.MK_REVERSEGEOCODE) {
					if (BaiduMapManager.this.mapEventListener != null) {
						BaiduMapManager.this.mapEventListener
								.onGetAddress(mkAddrInfo.strAddr);
					}
					// 鍙嶅湴鐞嗙紪鐮侊細閫氳繃鍧愭爣鐐规绱㈣缁嗗湴鍧�鍙婂懆杈筽oi
					// String strInfo = res.strAddr;
					// Toast.makeText(GeoCoderDemo.this, strInfo,
					// Toast.LENGTH_LONG).show();

				}
				if (true) {
					return;
				}
				// 鐢熸垚ItemizedOverlay鍥惧眰鐢ㄦ潵鏍囨敞缁撴灉鐐�
				ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
						null, mMapView);
				// 鐢熸垚Item
				OverlayItem item = new OverlayItem(mkAddrInfo.geoPt, "kk", null);
				// 寰楀埌闇�瑕佹爣鍦ㄥ湴鍥句笂鐨勮祫婧�
				// Drawable marker = getResources().getDrawable(
				// R.drawable.icon_markf);
				// // 涓簃aker瀹氫箟浣嶇疆鍜岃竟鐣�
				// marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				// marker.getIntrinsicHeight());
				// 缁檌tem璁剧疆marker
				// item.setMarker(marker);
				// 鍦ㄥ浘灞備笂娣诲姞item
				itemOverlay.addItem(item);

				// 娓呴櫎鍦板浘鍏朵粬鍥惧眰
				mMapView.getOverlays().clear();
				// 娣诲姞涓�涓爣娉↖temizedOverlay鍥惧眰
				mMapView.getOverlays().add(itemOverlay);
				// 鎵ц鍒锋柊浣跨敓鏁�
				mMapView.refresh();
			}

			@Override
			public void onGetPoiResult(MKPoiResult res, int type, int error) {

			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				// TODO Auto-generated method stub

			}

		});
		// mClearBtn = (Button) findViewById(R.id.clear);
		// mResetBtn = (Button) findViewById(R.id.reset);
		this.mapController = mMapView.getController();
		this.mapController.enableClick(true);
		this.mMapView.setBuiltInZoomControls(true);
		if (defaultZoom != null) {
			this.mapController.setZoom(defaultZoom);
		}
		if (defaultCenterGeoPoint != null) {
			this.mapController.setCenter(defaultCenterGeoPoint);
		}
		// int defaultZoom, GeoPoint defaultCenterGeoPoint.setZoom(20);
		// mMapController.setScrollGesturesEnabled(false);
		// mMapController.setZoomGesturesEnabled(false);
		// mMapController.setOverlookingGesturesEnabled(false);
		// mMapController.setRotateWithTouchEventCenterEnabled(false);
		// mMapController.setCenter(new GeoPoint((int) (39 * 1e6),
		// (int) (116 * 1e6)));

		// mapController.setZoom(14);
		// if (false) {
		// mapView.setBuiltInZoomControls(true);
		// this.mapView.getZoomControls().setPadding(0, 0, 0, 100);
		// }
		/**
		 * 鍒涘缓鑷畾涔塷verlay
		 */
		this.myOverlay = new MOverlay(null, mMapView);

		/**
		 * 淇濆瓨鎵�鏈塱tem锛屼互渚縪verlay鍦╮eset鍚庨噸鏂版坊鍔�
		 */
		// overlayItems = new ArrayList<OverlayItem>();
		// overlayItems.addAll(myOverlay.getAllItem());

		// 鍒濆鍖� ground 鍥惧眰
		// mGroundOverlay = new GroundOverlay(mMapView);
		// GeoPoint leftBottom = new GeoPoint((int) (mLat5 * 1E6),
		// (int) (mLon5 * 1E6));
		// GeoPoint rightTop = new GeoPoint((int) (mLat6 * 1E6),
		// (int) (mLon6 * 1E6));
		// Drawable d = getResources().getDrawable(R.drawable.ground_overlay);
		// Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		// mGround = new Ground(bitmap, leftBottom, rightTop);
		this.mMapView.getOverlays().add(myOverlay);
		this.mMapView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				if (BaiduMapManager.this.isEnable
						&& BaiduMapManager.this.mapEventListener != null) {
					BaiduMapManager.this.mapEventListener
							.onLongClick(BaiduMapManager.this
									.getCurrentTouchGeoPoint());
				}
				return true;
			}
		});
		this.mMapView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (BaiduMapManager.this.mapEventListener != null) {
					BaiduMapManager.this.mapEventListener
							.onClick(BaiduMapManager.this
									.getCurrentTouchGeoPoint());
				}
			}
		});
		// this.mapView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// }
		// })
		// this.mapView.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		// })
		if (!this.isUsePopup) {
			return;
		}
		// this.popupView = activity.getLayoutInflater().inflate(
		// R.layout.list_item_task_map_pop, null);
		// this.descriptionTextView = (TextView) this.popupView
		// .findViewById(R.id.book_name);
		// this.stateTextView = (TextView) this.popupView
		// .findViewById(R.id.state_text);
		// this.createTimeTextView = (TextView) this.popupView
		// .findViewById(R.id.time_create);
		// this.popupHeight = activity.getResources()
		// .getDrawable(R.drawable.task_done).getIntrinsicHeight();
		// this.descriptionTextView.setSelected(true);
		// R.id.type_text, R.id.state_text, R.id.time_create
		// mMapView.getOverlays().add(mGroundOverlay);
		// mGroundOverlay.addGround(mGround);
		// mResetBtn.setEnabled(false);
		// mClearBtn.setEnabled(true);
		// viewCache = getLayoutInflater()
		// .inflate(R.layout.custom_text_view, null);
		// popupInfo = viewCache.findViewById(R.id.popinfo);
		// popupLeft = viewCache.findViewById(R.id.popleft);
		// popupRight = viewCache.findViewById(R.id.popright);
		// popupText = (TextView) viewCache.findViewById(R.id.textcache);
		//
		// button = getLayoutInflater().inflate(R.layout.list_item_task_map_pop,
		// null);
		// // new Button(this)
		// ;
		this.popupView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TaskBaiduMapActivity.this.finish();
				// Task currentTask = BaiduMapManager.this.tasks
				// .get(BaiduMapManager.this.currentPopupIndex);
				// TaskUtil.startActivityForResult(currentTask.getTaskId(),
				// BaiduMapManager.this.taskData.getTaskType(),
				// StartActivityForResultConstant.TASK_REQUEST,
				// BaiduMapManager.this, TaskDetailActivity.class);
				// ToastManager.getInstance(TaskBaiduMapActivity.this).shortToast(
				// currentTask.getDescription() + currentTask.getTaskId());
			}
		});
		// button.setBackgroundResource(R.drawable.popup);
		// PopupClickListener popListener = new PopupClickListener() {
		// @Override
		// public void onClickedPopup(int index) {
		// if (index == 0) {
		// // 鏇存柊item浣嶇疆
		// popupOverlay.hidePop();
		// GeoPoint p = new GeoPoint(mCurItem.getPoint()
		// .getLatitudeE6() + 5000, mCurItem.getPoint()
		// .getLongitudeE6() + 5000);
		// mCurItem.setGeoPoint(p);
		// myOverlay.updateItem(mCurItem);
		// mapView.refresh();
		// } else if (index == 2) {
		// // 鏇存柊鍥炬爣
		// mCurItem.setMarker(getResources().getDrawable(
		// R.drawable.nav_turn_via_1));
		// myOverlay.updateItem(mCurItem);
		// mapView.refresh();
		// }
		// }
		// };
		this.popupOverlay = new PopupOverlay(mMapView, null);
		// this.doneDrawale = activity.getResources().getDrawable(
		// R.drawable.task_done);
		// this.noDoneYetDrawable = activity.getResources().getDrawable(
		// R.drawable.task_remain);
	}

	private GeoPoint getCurrentTouchGeoPoint() {
		return this.mMapView.getProjection().fromPixels(
				this.mMapView.getCurrentTouchX(),
				this.mMapView.getCurrentTouchY());
	}

	// public void initOverlay() {
	// /**
	// * 鍒涘缓鑷畾涔塷verlay
	// */
	// myOverlay = new MyOverlay(null, mapView);
	// myOverlay.removeAll();
	// /**
	// * 鍑嗗overlay 鏁版嵁
	// */
	// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
	// OverlayItem item1 = new OverlayItem(p1, "瑕嗙洊鐗�1", "");
	// /**
	// * 璁剧疆overlay鍥炬爣锛屽涓嶈缃紝鍒欎娇鐢ㄥ垱寤篒temizedOverlay鏃剁殑榛樿鍥炬爣.
	// */
	// item1.setMarker(getResources().getDrawable(R.drawable.icon_marka));
	//
	// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
	// OverlayItem item2 = new OverlayItem(p2, "瑕嗙洊鐗�2", "");
	// item2.setMarker(getResources().getDrawable(R.drawable.icon_marka));
	//
	// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
	// OverlayItem item3 = new OverlayItem(p3, "瑕嗙洊鐗�3", "");
	// item3.setMarker(getResources().getDrawable(R.drawable.icon_marka));
	//
	// GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 * 1E6));
	// OverlayItem item4 = new OverlayItem(p4, "瑕嗙洊鐗�4", "");
	// item4.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
	// /**
	// * 灏唅tem 娣诲姞鍒皁verlay涓� 娉ㄦ剰锛� 鍚屼竴涓猧tme鍙兘add涓�娆�
	// */
	// myOverlay.addItem(item1);
	// myOverlay.addItem(item2);
	// myOverlay.addItem(item3);
	// myOverlay.addItem(item4);
	// /**
	// * 淇濆瓨鎵�鏈塱tem锛屼互渚縪verlay鍦╮eset鍚庨噸鏂版坊鍔�
	// */
	// overlayItems = new ArrayList<OverlayItem>();
	// overlayItems.addAll(myOverlay.getAllItem());
	//
	// // 鍒濆鍖� ground 鍥惧眰
	// // mGroundOverlay = new GroundOverlay(mMapView);
	// // GeoPoint leftBottom = new GeoPoint((int) (mLat5 * 1E6),
	// // (int) (mLon5 * 1E6));
	// // GeoPoint rightTop = new GeoPoint((int) (mLat6 * 1E6),
	// // (int) (mLon6 * 1E6));
	// // Drawable d = getResources().getDrawable(R.drawable.ground_overlay);
	// // Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
	// // mGround = new Ground(bitmap, leftBottom, rightTop);
	// mapView.getOverlays().add(myOverlay);
	// // mMapView.getOverlays().add(mGroundOverlay);
	// // mGroundOverlay.addGround(mGround);
	// /**
	// * 鍒锋柊鍦板浘
	// */
	// mapView.refresh();
	// // mResetBtn.setEnabled(false);
	// // mClearBtn.setEnabled(true);
	// viewCache = getLayoutInflater()
	// .inflate(R.layout.custom_text_view, null);
	// popupInfo = viewCache.findViewById(R.id.popinfo);
	// popupLeft = viewCache.findViewById(R.id.popleft);
	// popupRight = viewCache.findViewById(R.id.popright);
	// popupText = (TextView) viewCache.findViewById(R.id.textcache);
	//
	// button = getLayoutInflater().inflate(R.layout.list_item_task_map_pop,
	// null);
	// // new Button(this)
	// ;
	// button.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// ToastManager.getInstance(TaskBaiduMapActivity.this).shortToast(
	// "hhhhhhhhhhh");
	// }
	// });
	// // button.setBackgroundResource(R.drawable.popup);
	//
	// PopupClickListener popListener = new PopupClickListener() {
	// @Override
	// public void onClickedPopup(int index) {
	// if (index == 0) {
	// // 鏇存柊item浣嶇疆
	// popupOverlay.hidePop();
	// GeoPoint p = new GeoPoint(mCurItem.getPoint()
	// .getLatitudeE6() + 5000, mCurItem.getPoint()
	// .getLongitudeE6() + 5000);
	// mCurItem.setGeoPoint(p);
	// myOverlay.updateItem(mCurItem);
	// mapView.refresh();
	// } else if (index == 2) {
	// // 鏇存柊鍥炬爣
	// mCurItem.setMarker(getResources().getDrawable(
	// R.drawable.nav_turn_via_1));
	// myOverlay.updateItem(mCurItem);
	// mapView.refresh();
	// }
	// }
	// };
	// popupOverlay = new PopupOverlay(mapView, popListener);
	//
	// }

	// public void clearOverlay(View view) {
	// mOverlay.removeAll();
	// if (popupOverlay != null) {
	// popupOverlay.hidePop();
	// }
	// mapView.removeView(button);
	// mapView.refresh();
	// mResetBtn.setEnabled(true);
	// mClearBtn.setEnabled(false);
	// }

	// public void resetOverlay(View view) {
	// // 閲嶆柊add overlay
	// mOverlay.addItem(mItems);
	// mapView.refresh();
	// mResetBtn.setEnabled(false);
	// mClearBtn.setEnabled(true);
	// }

	// @Override
	public void onPause() {
		this.mMapView.onPause();
		// super.onPause();
	}

	// @Override
	public void onResume() {
		this.mMapView.onResume();
		// super.onResume();
	}

	// @Override
	public void onDestroy() {
		this.mMapView.destroy();
		this.mkSearch.destory();
		// super.onDestroy();
	}

	// @Override
	public void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	// @Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	public class MOverlay extends ItemizedOverlay {

		public MOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			if (!BaiduMapManager.this.isUsePopup) {
				return true;
			}
			// OverlayItem item = getItem(index);
			BaiduMapManager.this.currentPopupIndex = index;
			// mCurItem = item;
			// if (index == 3) {
			// // button.setText("杩欐槸涓�涓郴缁熸帶浠�");
			// GeoPoint pt = new GeoPoint((int) (mLat4 * 1E6),
			// (int) (mLon4 * 1E6));
			// // 寮瑰嚭鑷畾涔塚iew
			// int h = getResources().getDrawable(R.drawable.icon_marka)
			// .getIntrinsicHeight();
			// // int h = -boudn.bottom + boudn.top;
			// popupOverlay.showPopup(button, pt, h);
			// mapController.zoomToSpan(100000, 100000);
			// mapController.animateTo(pt);
			// } else {
			// popupText.setText(getItem(index).getTitle());
			// Bitmap[] bitMaps = { BitmapUtil.getBitmapFromView(popupLeft),
			// BitmapUtil.getBitmapFromView(popupInfo),
			// BitmapUtil.getBitmapFromView(popupRight) };
			// popupOverlay.showPopup(bitMaps, item.getPoint(), 64);
			// }
			// int h = getResources().getDrawable(R.drawable.icon_marka)
			// .getIntrinsicHeight();
			// int h = -boudn.bottom + boudn.top;
			// Task task = BaiduMapManager.this.tasks.get(index);
			// BaiduMapManager.this.descriptionTextView.setText(
			// // "浠诲姟鎻忚堪锛�"
			// // +
			// StringUtil.getNotNullString(task.getDescription(), "")
			// // "XXXXXXFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"
			// );
			// // TaskBaiduMapActivity.this.descriptionTextView
			// // .setWidth(TaskBaiduMapActivity.this.descriptionTextView
			// // .getMeasuredWidth());
			// BaiduMapManager.this.stateTextView.setText("浠诲姟鐘舵�侊細"
			// + TaskUtil.getTaskStatusString(task.getTaskStatus()));
			// BaiduMapManager.this.createTimeTextView.setText(TimeUtil
			// .dateToAppString(task.getTaskTime()));
			GeoPoint geoPoint = BaiduMapManager.this.geoPoints.get(index);
			// BaiduMapManager.this.adjustDescriptionTextViewWidth();
			BaiduMapManager.this.mapController.animateTo(geoPoint);
			popupOverlay.showPopup(BaiduMapManager.this.popupView, geoPoint,
					BaiduMapManager.this.popupHeight);
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			if (!BaiduMapManager.this.isUsePopup) {
				return true;
			}
			if (popupOverlay != null) {
				popupOverlay.hidePop();
				mMapView.removeView(BaiduMapManager.this.popupView);
			}
			return false;
		}
	}

	// public void showPopup() {
	//
	// }

	// private void adjustDescriptionTextViewWidth() {
	// System.out.println("VVVVVVVVVVFFFFFFFFFFF");
	// TextPaint paint = BaiduMapManager.this.descriptionTextView
	// .getPaint();
	// float textLength = paint
	// .measureText(BaiduMapManager.this.descriptionTextView
	// .getText().toString());
	// float frontWidth = paint.measureText("鎴戠殑浠诲姟锛�");
	// WindowManager windowManager = this.getWindowManager();
	// int windowWidth = windowManager.getDefaultDisplay().getWidth();
	// // System.out.println("XXXVVVV");
	// // System.out.println(windowWidth);
	// // if() {
	// //
	// // }
	// // System.out.println("wwwww:" + windowWidth);
	// BaiduMapManager.this.descriptionTextView.setWidth((int) (Math.min(
	// textLength + frontWidth, windowWidth) - frontWidth - 20));
	// this.findViewById(R.id.form_task_description).setLayoutParams(params);
	// }

	// private void addOverlayItem(List<Task> tasks) {
	//
	// }

	// private void animateTo(List<Task> tasks) {
	// if (ListUtil.isEmpty(tasks)) {
	// return;
	// }
	// List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
	// for (Task task : tasks) {
	// GeoPoint geoPoint = JsonManager.getInstance(this).getObject(
	// task.getRemark(), GeoPoint.class);
	// if (geoPoint != null) {
	// geoPoints.add(geoPoint);
	// }
	// }
	// this.animateToBestZoomAndCenter(geoPoints);
	// }

	private void animateToBestZoomAndCenter(
	// List<GeoPoint> geoPoints
	) {
		if (ListUtil.isEmpty(this.geoPoints)) {
			return;
		}
		// if(this.geoPoints.size() == 1) {
		// this.mapController.setZoom(this.mapController.);
		// this.mapController.animateTo(this.geoPoints.get(0));
		// }
		// }
		if (this.geoPoints.size() == 1) {
			// ToastManager.getInstance(this).shortToast("sdfsfsdfdssdf");
			if (false) {
				this.mapController.zoomToSpan(100, 100);
			} else {
				this.mapController.setZoom(18);
			}
			this.mapController.animateTo(this.geoPoints.get(0));
			// minLatitudeE6 = 0;
			// minLongitudeE6 = 0;
		} else {
			int minLatitudeE6 = Integer.MAX_VALUE;
			int maxLatitudeE6 = 0;
			int minLongitudeE6 = Integer.MAX_VALUE;
			int maxLongitudeE6 = 0;
			// if() {
			//
			// }
			for (GeoPoint geoPoint : this.geoPoints) {
				if (geoPoint.getLatitudeE6() < minLatitudeE6) {
					minLatitudeE6 = geoPoint.getLatitudeE6();
				}
				if (geoPoint.getLatitudeE6() > maxLatitudeE6) {
					maxLatitudeE6 = geoPoint.getLatitudeE6();
				}
				if (geoPoint.getLongitudeE6() < minLongitudeE6) {
					minLongitudeE6 = geoPoint.getLongitudeE6();
				}
				if (geoPoint.getLongitudeE6() > maxLongitudeE6) {
					maxLongitudeE6 = geoPoint.getLongitudeE6();
				}
			}
			int latitudeSpanE6 = maxLatitudeE6 - minLatitudeE6;
			int longitudeSpanE6 = maxLongitudeE6 - minLongitudeE6;
			GeoPoint centerGeoPoint = new GeoPoint(minLatitudeE6
					+ latitudeSpanE6 / 2, minLongitudeE6 + longitudeSpanE6 / 2);
			this.mapController.zoomToSpan(latitudeSpanE6, longitudeSpanE6);
			this.mapController.animateTo(centerGeoPoint);
		}
		// this.mapController.
	}

	// public GeoPoint getGeoPoint(int x, int y) {
	// return this.mapView.;
	// }

	public void animateTo(GeoPoint geoPoint) {
		this.mapController.animateTo(geoPoint);
	}

	public void animateTo(GeoPoint geoPoint, Drawable drawable) {
		// LogUtil.systemOut(geoPoint);
		if (geoPoint == null || drawable == null) {
			return;
		}
		// System.out.println("x:" + geoPoint.getLatitudeE6() + "y:"
		// + geoPoint.getLongitudeE6());
		this.myOverlay.removeAll();
		if (this.isUsePopup) {
			this.popupOverlay.hidePop();
		}
		OverlayItem overlayItem = new OverlayItem(geoPoint, null, null);
		overlayItem.setMarker(drawable != null ? drawable
				: this.defaultDrawable);
		this.myOverlay.addItem(overlayItem);
		// this.geoPoints.add(geoPoint);
		this.mapController.setZoom(100);
		this.animateTo(geoPoint);
		// LogUtil.systemOut("++++++--------");
		this.mMapView.refresh();
	}

	public void updateMapView(List<BaiduMapData> baiduMapDatas) {
		this.myOverlay.removeAll();
		if (this.isUsePopup) {
			this.popupOverlay.hidePop();
		}
		if (!ListUtil.isEmpty(baiduMapDatas)) {
			// this.tasks = new ArrayList<Task>();
			this.geoPoints = new ArrayList<GeoPoint>();
			for (BaiduMapData baiduMapData : baiduMapDatas) {
				MGeoPoint mGeoPoint = baiduMapData.getmGeoPoint();
				if (baiduMapData.getmGeoPoint() != null) {
					GeoPoint geoPoint = new GeoPoint(
							(int) (mGeoPoint.getLatitude() * 1E6),
							(int) (mGeoPoint.getLongitude() * 1E6));
					// LogUtil.systemOut(geoPoint);
					OverlayItem overlayItem = new OverlayItem(geoPoint, null,
							null);
					overlayItem
							.setMarker(baiduMapData.getDrawable() != null ? baiduMapData
									.getDrawable() : this.defaultDrawable);
					myOverlay.addItem(overlayItem);
					// this.tasks.add(task);
					this.geoPoints.add(geoPoint);
				}
			}
			// /**
			// * 鍑嗗overlay 鏁版嵁
			// */
			// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 *
			// 1E6));
			// OverlayItem item1 = new OverlayItem(p1, "瑕嗙洊鐗�1", "");
			// /**
			// * 璁剧疆overlay鍥炬爣锛屽涓嶈缃紝鍒欎娇鐢ㄥ垱寤篒temizedOverlay鏃剁殑榛樿鍥炬爣.
			// */
			// item1.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			//
			// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 *
			// 1E6));
			// OverlayItem item2 = new OverlayItem(p2, "瑕嗙洊鐗�2", "");
			// item2.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			//
			// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 *
			// 1E6));
			// OverlayItem item3 = new OverlayItem(p3, "瑕嗙洊鐗�3", "");
			// item3.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			//
			// GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 *
			// 1E6));
			// OverlayItem item4 = new OverlayItem(p4, "瑕嗙洊鐗�4", "");
			// item4.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
			// /**
			// * 灏唅tem 娣诲姞鍒皁verlay涓� 娉ㄦ剰锛� 鍚屼竴涓猧tme鍙兘add涓�娆�
			// */
			// myOverlay.addItem(item1);
			// myOverlay.addItem(item2);
			// myOverlay.addItem(item3);
			// myOverlay.addItem(item4);
			this.animateToBestZoomAndCenter();
		}

		this.mMapView.refresh();
		// this.mapController.animateTo(this.geoPoints.get(0));
	}

	public static void startNaviFromCurrent(final Activity activity,
			final GeoPoint endPoint) {
		// int lat = (int) (1222 * 1E6);
		// int lon = (int) (22 * 1E6);
		// GeoPoint pt1 = new GeoPoint(lat, lon);
		// lat = (int) (22 * 1E6);
		// lon = (int) (22 * 1E6);
		// GeoPoint pt2 = new GeoPoint(lat, lon);
		final GeoDataWithoutAddress geoDataWithoutAddress = new GeoDataWithoutAddress();
		final MLocationManager mLocationManager = MLocationManager
				.getInstance(activity);
		mLocationManager.setSureNeedGps(false);
		mLocationManager.rigist(new LocationCallback() {

			@Override
			public void receive(String address) {
			}

			@Override
			public void receive(GeoData geoData) {
				MGeoData mGeoData = (MGeoData) geoData;
				geoDataWithoutAddress.setGeoStatus(mGeoData.getBaiduGeoData()
						.getGeoDataWithoutAddress().getGeoStatus());
				geoDataWithoutAddress.setLongitude(mGeoData.getBaiduGeoData()
						.getGeoDataWithoutAddress().getLongitude());
				geoDataWithoutAddress.setLatitude(mGeoData.getBaiduGeoData()
						.getGeoDataWithoutAddress().getLatitude());
				mLocationManager.setSureNeedGps(true);
				mLocationManager.stop();
			}

			@Override
			public void receive(GeoDataWithoutAddress geoPoint) {
			}
		});
		mLocationManager.start();
		mLocationManager.request();
		new BasicAsyncTask<Boolean, Integer>(activity, true, true, "姝ｅ湪鑾峰彇褰撳墠浣嶇疆",
				"鑾峰彇褰撳墠浣嶇疆鎴愬姛", "鑾峰彇褰撳墠浣嶇疆澶辫触") {
			private GeoPoint geoPoint;

			@Override
			public void onSuccess(Boolean t) {
				GeoPoint geoPoint = new GeoPoint(
						(int) (geoDataWithoutAddress.getLatitude() * 1e6),
						(int) (geoDataWithoutAddress.getLongitude() * 1e6));
				startNavi(activity, geoPoint, endPoint);
			}

			@Override
			public void onFail() {
			}

			@Override
			public void onCancel() {
			}

			@Override
			public Boolean getResult(Integer... params) {
				while (geoDataWithoutAddress.getGeoStatus() == null) {
					try {
						Thread.currentThread().sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// while() {
				//
				// }
				return true;
			}
		}.execute();
	}

	public static void startNavi(final Activity activity, GeoPoint startPoint,
			GeoPoint endPoint) {
		NaviPara para = new NaviPara();
		para.startPoint = startPoint;
		para.startName = "浠庤繖閲屽紑濮�";
		para.endPoint = endPoint;
		para.endName = "鍒拌繖閲岀粨鏉�";
		try {
			BaiduMapNavigation.openBaiduMapNavi(para, activity);
		} catch (BaiduMapAppNotSupportNaviException baiduMapAppNotSupportNaviException) {
			baiduMapAppNotSupportNaviException.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(ResourceUtil.getStringId(activity,
					"note_install_baidu_or_not")
			// R.string.note_install_baidu_or_not
			);
			builder.setTitle(ResourceUtil.getStringId(activity, "hint")
			// R.string.hint
			);
			builder.setPositiveButton(
					ResourceUtil.getStringId(activity, "sure")
					// R.string.sure
					, new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							BaiduMapNavigation.GetLatestBaiduMapApp(activity);
						}
					})
					.setNegativeButton(
							ResourceUtil.getStringId(activity, "cancel")
							// R.string.cancel
							,
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create().show();
		}
	}

	// @Override
	// public void onActivityResult(int requestCode, int resultCode,
	// Intent intent) {
	// super.onActivityResult(requestCode, resultCode, intent);
	// if (requestCode == StartActivityForResultConstant.TASK_REQUEST) {
	// this.updateMapView(null);
	// // this.clearMap();
	// this.getTasksFromNet();
	// // this.updateMapView(null);
	// }
	// }

	// private void clearMap() {
	// this.myOverlay.removeAll();
	// this.popupOverlay.hidePop();
	// this.mapView.refresh();
	// }
	// @Override
	// public void processMessage(Message msg) {
	// }
}