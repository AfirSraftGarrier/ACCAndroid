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
package com.acc.android.util.widget.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.acc.android.manager.BitmapProviderManager;
import com.acc.android.model.ImageData;
import com.acc.android.util.ACCImageUtil;
import com.acc.android.util.IntentUtil;
import com.acc.android.util.ACCImageUtil.OnDeletedPhotoListener;
import com.acc.android.util.constant.ACCALibConstant;
import com.acc.android.util.view.AutoImageView;
import com.acc.android.util.view.AutoImageView.OnSingleTapListener;
import com.acc.java.model.ACCFile;
import com.acc.java.util.ListUtil;
import com.acc.java.util.callback.ACCFileCallback;

public class ImageAdapter extends BaseAdapter {
	// int mGalleryItemBackground;
	// private final static boolean isUseNew = true;
	private final Context context;
	// private final List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	// private List<Cphoto> cPhotos = new ArrayList<Cphoto>();
	// private List<Long> sysFileIds = new ArrayList<Long>();
	// private final List<Long> toBeDeleteSysFileIds = new ArrayList<Long>();
	// private List<ACCFile> accFiles;
	private ImageData imageData;
	private final Gallery gallery;
	// private FileDownloadManager fileDownloadManager;
	private boolean useGalleryDeleteAction;
	private final BitmapProviderManager bitmapProviderManager;
	private final boolean isBig;
	private final OnSingleTapListener onSingleTapListener;
	private ACCFileCallback accFileCallback;
	private LocalFileAction localFileAction;

	public interface LocalFileAction {
		void onAdd(String localPath);

		void onDelete(String localPath);
	}

	public void setLocalFileAction(LocalFileAction localFileAction) {
		this.localFileAction = localFileAction;
	}

	public void disableGalleryDeleteAction() {
		this.useGalleryDeleteAction = false;
	}

	// public boolean isBig() {
	// return this.isBig;
	// }

	public void setACCFileCallback(ACCFileCallback accFileCallback) {
		this.accFileCallback = accFileCallback;
	}

	public List<Long> getToBeDeleteFileIds() {
		return this.imageData.getToBeDeleteFileIds();
	}

	public void selectIndex(int index) {
		this.imageData.setSelectIndex(index);
	}

	public ImageAdapter(final Context context,
			// List<Cphoto> cPhotos,
			Gallery gallery, BitmapProviderManager bitmapProviderManager,
			ACCFileCallback accFileCallback,
			// boolean useOldShowImage,
			boolean useGalleryDeleteAction, boolean isBig) {
		this.context = context;
		this.gallery = gallery;
		// this.cPhotos = new ArrayList<Cphoto>();
		this.isBig = isBig;
		// if (fileDownloadManager == null) {
		// Handler fileDownloadListener = new Handler() {
		// @Override
		// public void handleMessage(final Message msg) {
		// ImageAdapter.this.notifyDataSetChanged();
		// }
		// };
		// // ;
		// fileDownloadManager = new FileDownloadManager(context,
		// fileDownloadListener);
		// }
		this.accFileCallback = accFileCallback;
		this.bitmapProviderManager = bitmapProviderManager;
		// this.gallery.setAdapter(this);
		this.gallery.setVisibility(View.GONE);
		// this.fileDownloadManager = fileDownloadManager;
		this.initGalleryAciton(
		// useOldShowImage,
		useGalleryDeleteAction);
		this.onSingleTapListener = new OnSingleTapListener() {

			@Override
			public void onSingleTap() {
				Intent intent = new Intent();
				intent.putExtras(IntentUtil.getBundle(
						ACCALibConstant.KEY_BUNDLE_ACC_FILE_S,
						ImageAdapter.this.getTransImageData()));
				Activity contextActivity = (Activity) context;
				contextActivity.setResult(Activity.RESULT_OK, intent);
				contextActivity.finish();
			}
		};
		this.initImageData();
	}

	private void initImageData() {
		this.imageData = new ImageData();
		this.imageData.setAccFiles(new ArrayList<ACCFile>());
		this.imageData.setLocalPaths(new ArrayList<String>());
		this.imageData.setFileIds(new ArrayList<Long>());
		this.imageData.setToBeDeleteFileIds(new ArrayList<Long>());
	}

	// public ImageAdapter(Context context,
	// // List<Cphoto> cPhotos,
	// Gallery gallery, FileDownloadManager fileDownloadManager,
	// // boolean useOldShowImage,
	// boolean useGalleryDeleteAction) {
	// this.context = context;
	// this.gallery = gallery;
	// this.cPhotos = new ArrayList<Cphoto>();
	// if (fileDownloadManager == null) {
	// Handler fileDownloadListener = new Handler() {
	// @Override
	// public void handleMessage(final Message msg) {
	// ImageAdapter.this.notifyDataSetChanged();
	// }
	// };
	// // ;
	// fileDownloadManager = new FileDownloadManager(context,
	// fileDownloadListener);
	// }
	// gallery.setAdapter(this);
	// gallery.setVisibility(View.GONE);
	// this.fileDownloadManager = fileDownloadManager;
	// this.initGalleryAciton(
	// // useOldShowImage,
	// useGalleryDeleteAction);
	// }

	// public ImageAdapter(Context context,
	// // List<Cphoto> cPhotos,
	// Gallery gallery) {
	// this.context = context;
	// this.gallery = gallery;
	// this.cPhotos = new ArrayList<Cphoto>();
	// Handler fileDownloadListener = new Handler() {
	// @Override
	// public void handleMessage(final Message msg) {
	// ImageAdapter.this.notifyDataSetChanged();
	// }
	// };
	// gallery.setAdapter(this);
	// gallery.setVisibility(View.GONE);
	// // ;
	// this.fileDownloadManager = new FileDownloadManager(context,
	// fileDownloadListener);
	// this.initGalleryAciton(true, useGalleryDeleteAction);
	// this(context,
	// // cPhotos,
	// gallery, null,
	// // true,
	// true);
	// }

	// public ImageAdapter(Context context,
	// // List<Cphoto> cPhotos,
	// Gallery gallery, FileDownloadManager fileDownloadManager) {
	// // this.context = context;
	// // this.gallery = gallery;
	// // this.cPhotos = cPhotos;
	// // this.fileDownloadManager = fileDownloadManager;
	// this(context,
	// // cPhotos,
	// gallery, fileDownloadManager
	// // , true
	// , true);
	// }

	// public void onDestroy() {
	// if (!ListUtil.isEmpty(bitmaps)) {
	// for (Bitmap bitmap : this.bitmaps) {
	// if (bitmap == null || bitmap.isRecycled()) {
	// continue;
	// }
	// bitmap.recycle();
	// }
	// }
	// }

	// private List<ACCFile> getACCFiles() {
	// return null;
	// }

	private void initGalleryAciton(
	// final boolean useOldShowImage,
			boolean useGalleryDeleteAction) {
		// if (true) {
		// return;
		// }
		// if (!(TaskDetailActivity.this.taskData.getTaskType() ==
		// TaskType.MAINTAINCE || TaskDetailActivity.this.taskData
		// .getTaskType() == TaskType.INSPECT_RECHECT)) {
		// return;
		// }
		this.useGalleryDeleteAction = useGalleryDeleteAction;
		// if (useGalleryDeleteAction) {
		this.gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// Bundle bundle = new Bundle();
				// bundle.putString("FilePath",
				// pList.get(position).getLocalPath());
				// intent.putExtras(bundle);
				// intent.setClass(ProblemUpload.this,
				// PhotoView.class);
				// startActivity(intent);
				// return true;
				if (ImageAdapter.this.useGalleryDeleteAction) {
					ACCImageUtil.deleteImage(context, null,
							new OnDeletedPhotoListener() {

								@Override
								public void onDeletedPhoto() {
									// if (false) {
									// beforePhotos.remove(position);
									// beforeImageAdapter.notifyDataSetChanged();
									// if (beforePhotos.size() == 0)
									// {
									// beforeGallery.setVisibility(View.GONE);
									// }
									// } else {
									ImageAdapter.this.delete(position);
									// }
								}
							});
				}
				return true;
			}
		});
		// ;
		// }
	}

	public ImageData getTransImageData() {
		return this.imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
		// this.imageData.setSysFileIds(imageData.getSysFileIds());
		// this.imageData.setcPhotos(imageData.getcPhotos());
		this.notifyDataSetChanged();
		if (this.imageData.getSelectIndex() != null) {
			this.gallery.setSelection(this.imageData.getSelectIndex());
		}
		// if() {
		//
		// }
	}

	// private String getMySysFileUrl(Long afsSysFileId) {
	// String imageUploadUrl = WebServiceApi.getInstance(context)
	// .getAPI_MYIMAGEREAD();
	// imageUploadUrl += "?sysFileId=" + afsSysFileId;
	// return imageUploadUrl;
	// }

	// private String getPhotoPath(int position) {
	// // position--;
	// if (position >= this.sysFileIds.size()) {
	// return this.cPhotos.get(position - this.sysFileIds.size())
	// .getLocalPath();
	// } else {
	// return FileUtil
	// .getFileName(Long.toString((sysFileIds.get(position) - 1)));
	// }
	// // return null;
	// }

	// public ImageAdapterNew(Context context, Gallery gallery) {
	// this.context = context;
	// this.gallery = gallery;
	// See res/values/attrs.xml for the <declare-styleable> that defines
	// Gallery1.
	// TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
	// mGalleryItemBackground = a.getResourceId(
	// R.styleable.Gallery1_android_galleryItemBackground, 0);
	// a.recycle();
	// }

	// public void addLocalPhoto(String photoPathString) {
	// Cphoto cphoto = new Cphoto();
	// cphoto.setLocalPath(photoPathString);
	// cphoto.setPhotoname(FileUtil.getFileRealName(photoPathString));
	// this.addLocalPath(cphoto);
	// }

	public void addLocalPath(String localPath) {
		this.imageData.getLocalPaths().add(
				Math.max(this.gallery.getSelectedItemPosition()
						- this.imageData.getFileIds().size(), 0), localPath);
		this.notifyDataSetChanged();
		if (this.gallery.getSelectedItemPosition()
				- this.imageData.getFileIds().size() < 0) {
			this.gallery.setSelection(this.imageData.getFileIds().size(), true);
		}
		if (this.localFileAction != null) {
			this.localFileAction.onAdd(localPath);
		}
	}

	public void delete(int index) {
		if (index < this.imageData.getFileIds().size()) {
			this.imageData.getToBeDeleteFileIds().add(
					this.imageData.getFileIds().get(index) - 1);
			this.imageData.getFileIds().remove(index);
		} else {
			int deleteIndex = index - this.imageData.getFileIds().size();
			if (this.localFileAction != null) {
				this.localFileAction.onAdd(this.imageData.getLocalPaths().get(
						deleteIndex));
			}
			this.imageData.getLocalPaths().remove(deleteIndex);
		}
		this.notifyDataSetChanged();
		// this.photos.remove(cphoto);
	}

	@Override
	public int getCount() {
		// return this.imageData.getFileIds().size()
		// + this.imageData.getLocalPaths().size();
		return this.imageData.getAccFiles().size();
		// return bitmaps.size();
	}

	public boolean isNeedUpdate() {
		return !((this.imageData.getToBeDeleteFileIds().size() + this.imageData
				.getLocalPaths().size()) == 0);
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// private Bitmap getBitmapFromFileDownloadManager(Long afsSysFileId) {
	// return this.fileDownloadManager.getAfsSysFileBitmap(afsSysFileId);
	// }

	// public void updateBig(List<Long> sysFileIds) {
	// if (ListUtil.isEmpty(sysFileIds)) {
	// return;
	// }
	// List<Long> afsSysFileIds = new ArrayList<Long>();
	// for (Long sysFileId : sysFileIds) {
	// // Long afsSysFileId = sysFileLink.getSysFileId();
	// long smallId = sysFileId;
	// afsSysFileIds.add(smallId);
	// this.fileDownloadManager.afsSysFileDownloadBitmap(smallId);
	// }
	// this.setIds(afsSysFileIds);
	// // this.notifyDataSetChanged();
	// }

	// public void update(List<Long> sysFileIds) {
	// if (ListUtil.isEmpty(sysFileIds)) {
	// return;
	// }
	// List<Long> afsSysFileIds = new ArrayList<Long>();
	// for (Long sysFileId : sysFileIds) {
	// // Long afsSysFileId = sysFileLink.getSysFileId();
	// long smallId = sysFileId + 1;
	// afsSysFileIds.add(smallId);
	// this.fileDownloadManager.afsSysFileDownloadBitmap(smallId);
	// }
	// this.setIds(sysFileIds);
	// this.notifyDataSetChanged();
	// }

	// private void setIds(List<Long> ids) {
	// this.imageData.setFileIds(ids);
	// this.notifyDataSetChanged();
	// }

	//
	// private String getAfsSysFileUrl(Long afsSysFileId) {
	// String imageUploadUrl = WebServiceApi.getInstance(context)
	// .getAPI_IMAGEREAD();
	// imageUploadUrl += "?sysFileId=" + afsSysFileId;
	// return imageUploadUrl;
	// }

	//
	private ACCFile getACCFile(Long fileId) {
		if (!ListUtil.isEmpty(this.imageData.getAccFiles())) {
			for (ACCFile accFile : this.imageData.getAccFiles()) {
				if (accFile.getKey().equals(
						this.accFileCallback.getKey(fileId, this.isBig)
								.toString())) {
					return accFile;
				}
			}
		}
		return this.accFileCallback.getACCFile(fileId, this.isBig);
		//
		// if (!this.isBig) {
		// key += 1;
		// }
		// ACCFile accFile = new ACCFile();
		// accFile.setKey(key.toString());
		// accFile.setLocalPath(FileUtil.getFileName(key.toString()));
		// accFile.setNetUrl(this.getAfsSysFileUrl(key));
		// if (this.isBig) {
		// Long smallId = key + 1;
		// accFile.setTempPath(FileUtil.getFileName(smallId.toString()));
		// }
		// // if (this.isBig) {
		// // id += 1;
		// // accFile.setTempPath(FileUtil.getFileName(id.toString()));
		// // }
		// return accFile;
	}

	@Override
	public void notifyDataSetChanged() {
		// LogUtil.systemOut("notifyDataSetChanged");
		// LogUtil.systemOut(sysFileIds);
		// bitmaps.clear();
		// if (!ListUtil.isEmpty(sysFileIds)) {
		// for (Long afsSysFileId : sysFileIds) {
		// bitmaps.add(this.getBitmapFromFileDownloadManager(afsSysFileId));
		// }
		// }
		// if (!ListUtil.isEmpty(this.getPhotos())) {
		// for (Cphoto photo : this.getPhotos()) {
		// bitmaps.add(toBitmap(photo.getLocalPath()));
		// }
		// }
		// this.accFiles = new ArrayList<ACCFile>();
		if (this.accFileCallback == null) {
			return;
		}
		List<ACCFile> accFiles = new ArrayList<ACCFile>();
		if (!ListUtil.isEmpty(this.imageData.getFileIds())) {
			for (Long fileId : this.imageData.getFileIds()) {
				accFiles.add(this.getACCFile(fileId));
				// if (accFile != null) {
				// accFiles.add(accFile);
				// } else {
				//
				// }
				// bitmaps.add(this.getBitmapFromFileDownloadManager(afsSysFileId));
			}
		}
		if (!ListUtil.isEmpty(this.imageData.getLocalPaths())) {
			for (String localPath : this.imageData.getLocalPaths()) {
				ACCFile accFile = new ACCFile();
				accFile.setKey(localPath);
				accFile.setLocalPath(localPath);
				accFiles.add(accFile);
				// if (this.isBig) {
				// id += 1;
				// accFile.setTempPath(FileUtil.getFileName(id.toString()));
				// }
				// accFile.setNetUrl(this.getAfsSysFileUrl(id));
				// bitmaps.add(toBitmap(photo.getLocalPath()));
			}
		}
		this.imageData.setAccFiles(accFiles);
		if (this.getCount() == 0) {
			gallery.setVisibility(View.GONE);
		} else {
			gallery.setVisibility(View.VISIBLE);
		}
		// 鎷嶇涓�寮犵収鐗囪嚜鍔ㄥ畾浣�
		// if (locationEditText.getText().toString().equals("")) {
		// if (MainMenu.currentAddressString == null
		// || "".equals(MainMenu.currentAddressString)) {
		// Toast.makeText(ProblemUploadHistoryDetail.this,
		// "杩樻湭鑾峰彇鍒癎PS瀹氫綅淇℃伅锛�", Toast.LENGTH_LONG).show();
		// } else {
		// locationEditText.setText(MainMenu.currentAddressString);
		// }
		// }
		super.notifyDataSetChanged();
	}

	// private String getBitmapKey(int position) {
	// return null;
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// if (false) {
		// if (this.accFileCallback == null) {
		// return null;
		// }
		ACCFile accFile = this.imageData.getAccFiles().get(position);
		ImageView imageView = !this.isBig ? new ImageView(this.context)
				: new AutoImageView(this.context, position == 0,
						position == this.getCount() - 1,
						this.onSingleTapListener);
		Bitmap bitmap = this.bitmapProviderManager.getBitmap(accFile);
		// if (bitmap == null || bitmap.isRecycled()) {
		// bitmap = BitmapManager.getInstance(context).getBlankBitmap();
		// }
		// LogUtil.systemOut("bitmap == null:");
		// LogUtil.systemOut(bitmap == null);
		// if (true) {
		if (this.isBig) {
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		}
		// this.isBig ?
		// :autoImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new Gallery.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// imageView.setBackgroundResource(R.drawable.login_input);
		// }
		// System.out.println(position);
		imageView.setImageBitmap(bitmap);
		if (this.isBig && accFile.getProgress() != null) {
			((AutoImageView) imageView).setProgress(accFile.getProgress());
		}
		// }
		return imageView;
	}

	public void setFileIds(List<Long> fileIds) {
		// if (!ListUtil.isEmpty(afsSysFileIds)) {
		// for (Long sysFileId : afsSysFileIds) {
		// // Long afsSysFileId = sysFileLink.getSysFileId();
		// // afsSysFileIds.add(sysFileId);
		// this.fileDownloadManager.afsSysFileDownloadBitmap(sysFileId);
		// }
		// }
		this.imageData.setFileIds(fileIds);
		this.notifyDataSetChanged();
	}

	//
	// private List<Long> getSysFileIds(List<SysFileLink> sysFileLinks) {
	// List<Long> sysFileIds = null;
	// if (!ListUtil.isEmpty(sysFileLinks)) {
	// sysFileIds = new ArrayList<Long>();
	// for (SysFileLink sysFileLink : sysFileLinks) {
	// sysFileIds.add(sysFileLink.getSysFileId());
	// }
	// }
	// return sysFileIds;
	// }

	//
	// public void setSysFileLinks(List<SysFileLink> sysFileLinks) {
	// this.setFileIds(this.getSysFileIds(sysFileLinks));
	// }

	public List<Long> getFileIds() {
		return this.imageData.getFileIds();
	}

	// private Bitmap toBitmap(String filepath) {
	// if (!isFilePathExists(filepath)) {
	// return null;
	// }
	// // new File(filepath).;
	// return BitmapUtil.getSmallBitmap(filepath);
	// // return BitmapUtil.getBitmap(filepath);
	// // BitmapFactory.Options options = new BitmapFactory.Options();
	// // options.inSampleSize = 8; // 缂╁皬鍥剧墖
	// // try {
	// // return BitmapFactory.decodeFile(filepath, options);
	// // } catch (Exception exception) {
	// // exception.printStackTrace();
	// // return null;
	// // }
	// }

	// private boolean isFilePathExists(String filepath) {
	// File file = new File(filepath);
	// if (!file.exists()) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	// public List<Cphoto> getPhotos() {
	// return this.imageData.getcPhotos();
	// }

	// public void setPhotos(List<Cphoto> photos) {
	// this.cPhotos = photos;
	// this.notifyDataSetChanged();
	// }

	public List<String> getLocalPaths() {
		// List<String> photoPaths = new ArrayList<String>();
		// List<Cphoto> cPhotos = null;
		// switch (problemPhotoStatus) {
		// case BEFORE:
		// cPhotos = this.beforePhotos;
		// break;
		// case ING:
		// cPhotos = this.ingPhotos;
		// break;
		// case AFTER:
		// cPhotos = this.afterPhotos;
		// break;
		// }
		// if (!ListUtil.isEmpty(this.imageData.getcPhotos())) {
		// for (Cphoto cphoto : this.imageData.getcPhotos()) {
		// photoPaths.add(cphoto.getLocalPath());
		// }
		// }
		return this.imageData.getLocalPaths();
	}

	public void setLocalPaths(List<String> localPaths) {
		this.imageData.setLocalPaths(localPaths);
		this.notifyDataSetChanged();
	}

	// public List<Cphoto> getPhotos() {
	// return photos;
	// }
}