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
package com.acc.android.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acc.android.manager.BitmapProviderManager;
import com.acc.android.model.ImageData;
import com.acc.android.util.CompressPictureUtil;
import com.acc.android.util.FileUtil;
import com.acc.android.util.ResourceUtil;
import com.acc.android.util.CompressPictureUtil.OnCompressPictureOverListener;
import com.acc.android.util.interf.MediaActivityInterface;
import com.acc.android.util.widget.adapter.ImageAdapter;
import com.acc.android.util.widget.adapter.ImageAdapter.LocalFileAction;
import com.acc.java.util.StringUtil;
import com.acc.java.util.callback.ACCFileCallback;
import com.acc.java.util.callback.ACCImageGroupViewCallback;
import com.acc.java.util.callback.FileDownloadCallback;
import com.acc.java.util.listener.FileDownloadListener;
import com.acc.java.util.listener.RequestListener.RequestFailReason;

public class ImageGroupView extends RelativeLayout
		implements
		com.acc.android.util.interf.MediaActivityInterface.OnActivityResultListener,
		android.view.View.OnClickListener, FileDownloadListener {
	private final Button photoButton;
	private final Button localPictureButton;
	private final Button disableButton;
	private final Gallery imageGallery;
	private final LinearLayout indexLinearLayout;
	private final ImageAdapter imageAdapter;
	private final Context context;
	// private final IndexAdapter indexAdapter;
	private MediaActivityInterface mediaActivityInterface;
	private OnCompressPictureOverListener onCompressPictureOverListener;
	private final boolean isBigGallery;
	private boolean isGoneAllAction;
	private final BitmapProviderManager bitmapProviderManager;
	private String accImageGroupViewCallbackClassName;

	// FileDownloadListener fileDownloadListener;

	// private BitmapProviderManager bitmapProviderManager;

	public void setImageData(ImageData imageData) {
		this.imageAdapter.setImageData(imageData);
	}

	// private ViewGroup parentDisallowInterceptViewGroup;

	// public void setParentDisallowInterceptView(
	// ViewGroup parentDisallowInterceptView) {
	// this.parentDisallowInterceptViewGroup = parentDisallowInterceptView;
	// }

	// private Cphoto currentCphoto;
	// private OnActivityResultListener onActivityResultListener;

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// if (this.parentDisallowInterceptViewGroup != null) {
	// this.parentDisallowInterceptViewGroup
	// .requestDisallowInterceptTouchEvent(true);
	// }
	// return super.dispatchTouchEvent(ev);
	// }

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// if (this.parentDisallowInterceptViewGroup != null) {
	// this.parentDisallowInterceptViewGroup
	// .requestDisallowInterceptTouchEvent(true);
	// }
	// return super.onInterceptTouchEvent(ev);
	// }

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// if (this.parentDisallowInterceptViewGroup != null) {
	// this.parentDisallowInterceptViewGroup
	// .requestDisallowInterceptTouchEvent(true);
	// }
	// return super.onTouchEvent(event);
	// }

	public void goneAllActionButton() {
		this.isGoneAllAction = true;
		this.imageAdapter.disableGalleryDeleteAction();
		this.photoButton.setVisibility(View.GONE);
		this.localPictureButton.setVisibility(View.GONE);
		this.disableButton.setVisibility(View.GONE);
	}

	public void setPhotoString(String photoString) {
		this.photoButton.setText(photoString);
	}

	public void setLocalPictureString(String localPictureString) {
		this.localPictureButton.setText(localPictureString);
	}

	public void setDisableString(String disableString) {
		this.disableButton.setText(disableString);
	}

	public void disablePhoto(String disableString) {
		this.imageAdapter.disableGalleryDeleteAction();
		this.photoButton.setVisibility(View.GONE);
		this.localPictureButton.setVisibility(View.GONE);
		this.disableButton.setVisibility(View.VISIBLE);
		if (disableString != null) {
			this.disableButton.setText(disableString);
		}
	}

	public ImageAdapter getImageAdapter() {
		return this.imageAdapter;
	}

	public void onDestroy() {
		this.bitmapProviderManager.onDestroy();
		// this.mediaActivity.unRegistOnActivityResultListener();
	}

	// public List<String> getPhotoPaths() {
	// return this.imageAdapter.getPhotoPaths();
	// }

	private void setFileDownloadCallbackAndACCFileCallback(
			FileDownloadCallback fileDownloadCallback,
			ACCFileCallback accFileCallback) {
		this.bitmapProviderManager
				.setFileDownloadCallback(fileDownloadCallback);
		this.imageAdapter.setACCFileCallback(accFileCallback);
	}

	public void resolveImageGroupCallBack(
			ACCImageGroupViewCallback accImageGroupViewCallback) {
		// System.out.println("VVVVVVVVCCCCCCCCCCCCCCCCCC");
		this.setFileDownloadCallbackAndACCFileCallback(
				accImageGroupViewCallback.getFileDownloadCallback(),
				accImageGroupViewCallback.getAccFileCallback());
		this.accImageGroupViewCallbackClassName = accImageGroupViewCallback
				.getClass().getName();
		// System.out.println(this.accImageGroupViewCallbackClassName);
	}

	public ImageGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int imageGroupLayoutStyleable = 0
		// R.styleable.IMAGE_GROUP_layout
		;
		int imageGroupPhotoStringStyleable = 1
		// R.styleable.IMAGE_GROUP_photo_string
		;
		int imageGroupLocalStringStyleable = 2
		// R.styleable.IMAGE_GROUP_local_string
		;
		int imageGroupDisableStringStyleable = 3
		// R.styleable.IMAGE_GROUP_disable_string
		;
		int[] imageGroupArrayStyleable = new int[] {
				ResourceUtil.getAttrId(context, "layout"),
				ResourceUtil.getAttrId(context, "photo_string"),
				ResourceUtil.getAttrId(context, "local_string"),
				ResourceUtil.getAttrId(context, "disable_string") };
		int imageGroupLayout = ResourceUtil.getLayoutId(context, "image_group");
		int btPhotoId = ResourceUtil.getId(context, "bt_photo");
		int btLocalPictureId = ResourceUtil.getId(context, "bt_local_picture");
		int btDisableId = ResourceUtil.getId(context, "bt_disable");
		int glPictureId = ResourceUtil.getId(context, "gl_picture");
		int llIndexPictureId = ResourceUtil.getId(context, "ll_index_picture");
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				imageGroupArrayStyleable);
		int layoutId = typedArray.getResourceId(imageGroupLayoutStyleable,
				imageGroupLayout);
		LayoutInflater.from(context).inflate(layoutId, this, true);
		this.context = context;
		this.photoButton = (Button) findViewById(btPhotoId);
		this.localPictureButton = (Button) findViewById(btLocalPictureId);
		this.disableButton = (Button) findViewById(btDisableId);
		this.imageGallery = (Gallery) findViewById(glPictureId);
		this.isBigGallery = this.imageGallery instanceof ACCGallery;
		// if(this.isBigGallery instanceof ACCGallery) {
		//
		// }
		// this.fileDownloadListener = new FileDownloadListener() {
		//
		// @Override
		// public void onSuccess(String key, Object valueObject) {
		// // this
		// ImageGroupView.this.imageAdapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void onProgress(String key, long already, long total) {
		// ImageGroupView.this.imageAdapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void onFail(String key,
		// RequestFailReason fileDownloadFailReason) {
		// ImageGroupView.this.imageAdapter.notifyDataSetChanged();
		// }
		// };
		this.bitmapProviderManager = new BitmapProviderManager(context, this,
				null);
		this.indexLinearLayout = (LinearLayout) findViewById(llIndexPictureId);
		this.imageAdapter = new ImageAdapter(context, this.imageGallery,
				this.bitmapProviderManager, null, true, this.isBigGallery);
		this.imageGallery.setVisibility(View.GONE);
		this.imageGallery.setAdapter(this.imageAdapter);
		String disableString = typedArray
				.getString(imageGroupDisableStringStyleable);
		if (disableString != null) {
			this.disableButton.setText(disableString);
		}
		// this.indexAdapter = new IndexAdapter(context,
		// new ArrayList<IndexData>(),
		// new OnItemClickListener<IndexData>() {
		//
		// @Override
		// public void onItemClick(IndexData indexData) {
		// ImageGroupView.this.indexAdapter.getIndex(indexData);
		// }
		// });
		// DataSetObserver observer = new DataSetObserver() {
		// @Override
		// public void onChanged() {
		// super.onChanged();
		// ImageGroupView.this.updateIndexView();
		// // System.out.println("dfddfdfdfdfdf");
		// }
		// };
		this.imageAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				ImageGroupView.this.updateIndexView();
				// System.out.println("dfddfdfdfdfdf");
			}
		});
		this.imageGallery
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						ImageGroupView.this.imageAdapter.selectIndex(arg2);
						ImageGroupView.this.updateIndexView(arg2);
						// System.out.println(arg2);
						// System.out.println(arg3);
						// System.out
						// .println("XXXXXXXXXXXXXXXXXXXXXXXXX111111111");
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
		if (!this.isBigGallery) {
			this.imageGallery.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ImageGroupView.this.startBigImage();
					// TODO Auto-generated method stub
					// if (useOldShowImage) {
					// ImageUtil.showImage(context,
					// ImageAdapter.this.getPhotoPath(position));
					// } else {
					// String fileName =
					// getSysFileIds().get(position).toString();//
					// 缂╃暐鍥緄d
					// // 锛屾瘮鍘熷浘id澶�1
					// String bigFileName =
					// String.valueOf(Long.valueOf(fileName) -
					// 1);
					// // ImageUtil.showImage(ComplainPhotos.this,
					// // FileUtil.getFileName(fileName));
					// ImageUtil.showMyImage(context,
					// FileUtil.getFileName(fileName),
					// FileUtil.getFileName(bigFileName),
					// getMySysFileUrl(Long.valueOf(fileName) - 1));
					// }
				}
			});
		}
		// System.out.println(context);
		if (context instanceof MediaActivityInterface) {
			// System.out.println("VVVVVVVVVVVVVVV");
			this.mediaActivityInterface = (MediaActivityInterface) context;
			this.onCompressPictureOverListener = new OnCompressPictureOverListener() {

				@Override
				public void onCompressPictureOver(String filePath) {
					ImageGroupView.this.addPhoto(filePath);
				}
			};
			String photoString = typedArray
					.getString(imageGroupPhotoStringStyleable);
			String localString = typedArray
					.getString(imageGroupLocalStringStyleable);
			if (photoString != null) {
				this.photoButton.setText(photoString);
			}
			if (localString != null) {
				this.localPictureButton.setText(localString);
			}
			this.initButtonAction();
		} else {
			// System.out.println("xxxxxxxxxxxxxxxxxxx");
			this.disablePhoto(null);
		}
		this.isGoneAllAction = false;
		// this.imageGallery.
		// this.imageGallery.setSelection(1, true);
		// this.indexImageGallery.setAdapter(this.indexAdapter);
		// List<IndexData> ts = new ArrayList<IndexData>();
		// ts.add(new IndexData());
		// ts.add(new IndexData());
		// IndexData dfsdf = new IndexData();
		// dfsdf.setCurrent(true);
		// ts.add(dfsdf);
		// ts.add(new IndexData());
		// this.indexAdapter.add(ts);
		// this.indexAdapter.notifyDataSetChanged();
		// Boolean dt = null;
		// ViewGroup view;
		// view.set
		// ts.indexOf(dt);
	}

	private void regist() {
		this.mediaActivityInterface.registOnActivityResultListener(this);
	}

	private void initButtonAction() {
		this.photoButton.setOnClickListener(this);
		this.localPictureButton.setOnClickListener(this);
	}

	@Override
	public void onImageCaptureResult(String localPath) {
		// System.out.println(localPath);
		this.compressPhotoToAdd(localPath, localPath);
	}

	@Override
	public void onLocalImageResult(String localPath) {
		if (localPath == null) {
			return;
		}
		// this.addPhoto(localPath);
		// System.out.println("XCCCCC");
		// System.out.println(localPath);
		this.compressPhotoToAdd(localPath,
				FileUtil.getTimestampImageFilePath("l"));
	}

	@Override
	public void onVedioCaptureResult(String localPath) {
	}

	@Override
	public void onClick(View view) {
		if (view == this.photoButton) {
			this.photoButtonClick(view);
		} else if (view == localPictureButton) {
			this.localPictureButtonClick(view);
		}
		// switch (view.getId()) {
		// case ResourceUtil.getId(view.getContext(), "bt_photo"):
		// this.photoButtonClick(view);
		// break;
		// case ResourceUtil.getId(view.getContext(), "bt_local_picture"):
		// this.localPictureButtonClick(view);
		// break;
		// }
	}

	public interface GetPhotoPathCallback {
		String getNewPhotoPath();
	}

	private GetPhotoPathCallback getPhotoPathCallback;

	public void setGetPhotoPathCallback(
			GetPhotoPathCallback getPhotoPathCallback) {
		this.getPhotoPathCallback = getPhotoPathCallback;
	}

	private void photoButtonClick(View view) {
		if (!android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return;
		}
		ImageGroupView.this.regist();
		ImageGroupView.this.mediaActivityInterface.startPhotoCapture(this
				.getPhotoPath());
	}

	private String getPhotoPath() {
		if (getPhotoPathCallback != null) {
			return getPhotoPathCallback.getNewPhotoPath();
		}
		return FileUtil.getTimestampImageFilePath(null);
	}

	private void localPictureButtonClick(View view) {
		ImageGroupView.this.regist();
		ImageGroupView.this.mediaActivityInterface.startLocalImage();
	}

	public void setLocalFileAction(LocalFileAction localFileAction) {
		this.imageAdapter.setLocalFileAction(localFileAction);
	}

	private void startBigImage() {
		// System.out.println("LLLLLLLLLLLLLLLL");
		// System.out.println(ImageGroupView.this.mediaActivity == null);
		ImageGroupView.this.regist();
		// MediaActivityInterface mediaActivityInterface =
		// (MediaActivityInterface) context;
		// System.out.println(this.accImageGroupViewCallbackClassName);
		this.mediaActivityInterface.startBigImage(
				this.imageAdapter.getTransImageData(),
				this.accImageGroupViewCallbackClassName, this.isGoneAllAction);
	}

	public void compressPhotoToAdd(String filePath, String resultPath) {
		// System.out.println("XCVCX");
		// System.out.println(filePath);
		// System.out.println(resultPath);
		if (StringUtil.isEmpty(filePath)) {
			return;
		}
		CompressPictureUtil.startAsyAsyncTaskOrNot(this.context, filePath,
				resultPath, this.onCompressPictureOverListener);
	}

	public void addPhoto(String filePath) {
		// System.out.println("XXXXXXXXXXX:" + filePath);
		// Cphoto cphoto = new Cphoto();
		// cphoto.setLocalPath(filePath);
		// cphoto.setPhotoname(FileUtil.getFileRealName(filePath));
		this.imageAdapter.addLocalPath(filePath);
	}

	private int getShapeIndex(boolean isCurrent) {
		if ((isCurrent && !this.isBigGallery)
				|| (!isCurrent && this.isBigGallery)) {
			return ResourceUtil.getDrawableId(context, "shape_index_current");
		} else {
			return ResourceUtil.getDrawableId(context, "shape_index");
		}
	}

	private void updateIndexView() {
		if (this.imageAdapter.getCount() == 0) {
			this.indexLinearLayout.setVisibility(View.GONE);
			return;
		} else {
			this.indexLinearLayout.setVisibility(View.VISIBLE);
		}
		int selectPosition = this.imageGallery.getSelectedItemPosition();
		int cout = this.imageAdapter.getCount();
		selectPosition = Math.min(Math.max(0, selectPosition), cout - 1);
		this.indexLinearLayout.removeAllViews();
		for (int i = 0; i < cout; i++) {
			ImageView dotImageView = new ImageView(this.context);
			dotImageView.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			dotImageView.setPadding(5, 0, 5, 0);
			if (i == selectPosition) {
				dotImageView.setImageResource(this.getShapeIndex(true));
				dotImageView.setTag(true);
			} else {
				dotImageView.setImageResource(this.getShapeIndex(false));
			}
			final int index = i;
			dotImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ImageGroupView.this.imageGallery.setSelection(index, true);
				}
			});
			indexLinearLayout.addView(dotImageView);
		}
	}

	private void updateIndexView(int selectIndex) {
		int childCout = this.indexLinearLayout.getChildCount();
		for (int i = 0; i < childCout; i++) {
			ImageView dotImageView = (ImageView) this.indexLinearLayout
					.getChildAt(i);
			if (dotImageView.getTag() != null && selectIndex != i) {
				dotImageView.setTag(null);
				dotImageView.setImageResource(this.getShapeIndex(false));
			}
			if (dotImageView.getTag() == null && selectIndex == i) {
				dotImageView.setTag(true);
				dotImageView.setImageResource(this.getShapeIndex(true));
			}
		}
	}

	@Override
	public void onImageGroupResult(ImageData imageData) {
		this.imageAdapter.setImageData(imageData);
	}

	@Override
	public void onSuccess(String key, Object valueObject) {
		// this
		this.imageAdapter.notifyDataSetChanged();
	}

	@Override
	public void onProgress(String key, double already, double total) {
		this.imageAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFail(String key, RequestFailReason fileDownloadFailReason) {
		this.imageAdapter.notifyDataSetChanged();
	}
}