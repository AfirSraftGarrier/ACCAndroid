package com.acc.android.frame.manager.viewmanager;

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

//import com.augurit.android.hfss.R;






import com.acc.android.frame.manager.MLocationManager;
import com.acc.android.frame.model.BaiduMapData;
import com.acc.android.frame.model.GeoData;
import com.acc.android.frame.model.GeoDataWithoutAddress;
import com.acc.android.frame.model.MGeoData;
import com.acc.android.frame.model.MGeoPoint;
import com.acc.android.frame.util.ListUtil;
import com.acc.android.frame.util.ResourceUtil;
import com.acc.android.frame.util.asynctask.BasicAsyncTask;
import com.acc.android.frame.util.callback.LocationCallback;
import com.acc.android.frame.util.view.MMapView;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class BaiduMapManager {
	private MMapView mMapView;
	// private Button mClearBtn;
	// private Button mResetBtn;
	private MapController mapController;
	private MyOverlay myOverlay;
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

	private MapEventLisener mapEventListener;

	public interface MapEventLisener {
		void onClick(GeoPoint geoPoint);

		void onLongClick(GeoPoint geoPoint);
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

	public BaiduMapManager(Activity activity, Integer defaultZoom,
			GeoPoint defaultCenterGeoPoint) {
		this.initMapView(activity, defaultZoom, defaultCenterGeoPoint);
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
	// "任务转发人"
	// // + "列表"
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
	// // + "列表"
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
	// // this.setTitle(this.taskTittle + "地图");
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
	// this.submitButton.setText("转发");
	// }
	// // if (TaskBaiduMapActivity.this.taskData.getTaskType() ==
	// // TaskType.INSPECT_RECHECT) {
	// // this.submitButton.setText("任务提交");
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
	// // .setTitle("退出提示")
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
	// // // // ********关闭任务监听服务
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
	// // .setNeutralButton("最小化",
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
	// new CommonUploadDataToNetAsyncTask<SubmitData>(this, "正在提交任务中...",
	// "提交任务成功", "提交任务失败",
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
	// // new CommonUploadDataToNetAsyncTask<SubmitData>(this, "正在提交任务中...",
	// // "提交任务成功", "提交任务失败",
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
	// builder.setTitle("请选择要转发到的人");
	// // String[] names = TaskBaiduMapActivity.this.taskPeopleNames.to;
	// builder.setSingleChoiceItems(this.taskPeopleNames, 0,
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// BaiduMapManager.this.chooseTaskPeopleIndex = which;
	// }
	// });
	//
	// builder.setPositiveButton(" 确 定 ",
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
	// new CommonUploadDataToNetAsyncTask<DeliveryData>(this, "正在转发任务中...",
	// "转发任务成功", "转发任务失败",
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

	public void disableMapAction() {
		this.mapController.setOverlookingGesturesEnabled(false);
		this.mapController.setRotateWithTouchEventCenterEnabled(false);
		// this.mapController.setOverlookingGesturesEnabled(false);
		this.mapController.setRotationGesturesEnabled(false);
		this.mapController.setScrollGesturesEnabled(false);
		this.mapController.setZoomGesturesEnabled(false);
		// this.mapController.setZoomWithTouchEventCenterEnabled(false);
		// this.mMapView.setEnabled(false);
		this.mMapView.setClickable(false);
	}

	private void initMapView(Activity activity, Integer defaultZoom,
			GeoPoint defaultCenterGeoPoint) {
		mMapView = (MMapView) activity.findViewById(ResourceUtil.getId(
				activity, "bmapView")
		// R.id.bmapView
				);
		// mClearBtn = (Button) findViewById(R.id.clear);
		// mResetBtn = (Button) findViewById(R.id.reset);
		mapController = mMapView.getController();
		mapController.enableClick(true);
		if (defaultZoom != null) {
			mapController.setZoom(defaultZoom);
		}
		if (defaultCenterGeoPoint != null) {
			mapController.setCenter(defaultCenterGeoPoint);
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
		 * 创建自定义overlay
		 */
		myOverlay = new MyOverlay(null, mMapView);

		/**
		 * 保存所有item，以便overlay在reset后重新添加
		 */
		// overlayItems = new ArrayList<OverlayItem>();
		// overlayItems.addAll(myOverlay.getAllItem());

		// 初始化 ground 图层
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
				if (BaiduMapManager.this.mapEventListener != null) {
					BaiduMapManager.this.mapEventListener
							.onLongClick(BaiduMapManager.this
									.getCurrentTouchGeoPoint());
				}
				return false;
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
		// // 更新item位置
		// popupOverlay.hidePop();
		// GeoPoint p = new GeoPoint(mCurItem.getPoint()
		// .getLatitudeE6() + 5000, mCurItem.getPoint()
		// .getLongitudeE6() + 5000);
		// mCurItem.setGeoPoint(p);
		// myOverlay.updateItem(mCurItem);
		// mapView.refresh();
		// } else if (index == 2) {
		// // 更新图标
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
	// * 创建自定义overlay
	// */
	// myOverlay = new MyOverlay(null, mapView);
	// myOverlay.removeAll();
	// /**
	// * 准备overlay 数据
	// */
	// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
	// OverlayItem item1 = new OverlayItem(p1, "覆盖物1", "");
	// /**
	// * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
	// */
	// item1.setMarker(getResources().getDrawable(R.drawable.icon_marka));
	//
	// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
	// OverlayItem item2 = new OverlayItem(p2, "覆盖物2", "");
	// item2.setMarker(getResources().getDrawable(R.drawable.icon_marka));
	//
	// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
	// OverlayItem item3 = new OverlayItem(p3, "覆盖物3", "");
	// item3.setMarker(getResources().getDrawable(R.drawable.icon_marka));
	//
	// GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 * 1E6));
	// OverlayItem item4 = new OverlayItem(p4, "覆盖物4", "");
	// item4.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
	// /**
	// * 将item 添加到overlay中 注意： 同一个itme只能add一次
	// */
	// myOverlay.addItem(item1);
	// myOverlay.addItem(item2);
	// myOverlay.addItem(item3);
	// myOverlay.addItem(item4);
	// /**
	// * 保存所有item，以便overlay在reset后重新添加
	// */
	// overlayItems = new ArrayList<OverlayItem>();
	// overlayItems.addAll(myOverlay.getAllItem());
	//
	// // 初始化 ground 图层
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
	// * 刷新地图
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
	// // 更新item位置
	// popupOverlay.hidePop();
	// GeoPoint p = new GeoPoint(mCurItem.getPoint()
	// .getLatitudeE6() + 5000, mCurItem.getPoint()
	// .getLongitudeE6() + 5000);
	// mCurItem.setGeoPoint(p);
	// myOverlay.updateItem(mCurItem);
	// mapView.refresh();
	// } else if (index == 2) {
	// // 更新图标
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
	// // 重新add overlay
	// mOverlay.addItem(mItems);
	// mapView.refresh();
	// mResetBtn.setEnabled(false);
	// mClearBtn.setEnabled(true);
	// }

	// @Override
	public void onPause() {
		mMapView.onPause();
		// super.onPause();
	}

	// @Override
	public void onResume() {
		mMapView.onResume();
		// super.onResume();
	}

	// @Override
	public void onDestroy() {
		mMapView.destroy();
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

	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
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
			// // button.setText("这是一个系统控件");
			// GeoPoint pt = new GeoPoint((int) (mLat4 * 1E6),
			// (int) (mLon4 * 1E6));
			// // 弹出自定义View
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
			// // "任务描述："
			// // +
			// StringUtil.getNotNullString(task.getDescription(), "")
			// // "XXXXXXFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"
			// );
			// // TaskBaiduMapActivity.this.descriptionTextView
			// // .setWidth(TaskBaiduMapActivity.this.descriptionTextView
			// // .getMeasuredWidth());
			// BaiduMapManager.this.stateTextView.setText("任务状态："
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
	// float frontWidth = paint.measureText("我的任务：");
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
			// * 准备overlay 数据
			// */
			// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 *
			// 1E6));
			// OverlayItem item1 = new OverlayItem(p1, "覆盖物1", "");
			// /**
			// * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
			// */
			// item1.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			//
			// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 *
			// 1E6));
			// OverlayItem item2 = new OverlayItem(p2, "覆盖物2", "");
			// item2.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			//
			// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 *
			// 1E6));
			// OverlayItem item3 = new OverlayItem(p3, "覆盖物3", "");
			// item3.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			//
			// GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 *
			// 1E6));
			// OverlayItem item4 = new OverlayItem(p4, "覆盖物4", "");
			// item4.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
			// /**
			// * 将item 添加到overlay中 注意： 同一个itme只能add一次
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
		new BasicAsyncTask<Boolean, Integer>(activity, true, true, "正在获取当前位置",
				"获取当前位置成功", "获取当前位置失败") {
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
		para.startName = "从这里开始";
		para.endPoint = endPoint;
		para.endName = "到这里结束";
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