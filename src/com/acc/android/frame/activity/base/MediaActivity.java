package com.acc.android.frame.activity.base;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.Toast;

public abstract class MediaActivity extends Activity {
	protected static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
	protected static final int REQUEST_CODE_VIDEO_CAPTURE = 0;
	private static final String DEFAULT_VEDIO_TYPE = "video/3gp";

	public void startCapture(String filePath) {
		// FileUtil.getFileName(fileName);
		File mImageFile = new File(filePath);
		Uri bitmapDataUri = Uri.fromFile(mImageFile);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, bitmapDataUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
	}

	public void startVedio() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		startActivityForResult(intent, REQUEST_CODE_VIDEO_CAPTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_IMAGE_CAPTURE:
				this.onImageCaptureResult(intent);
				break;
			case REQUEST_CODE_VIDEO_CAPTURE:
				this.onVedioCaptureResult(intent);
				break;
			}
		}
	}

	private String getVideoPath(Intent intent) {
		String resultString = null;
		Uri uri = intent.getData();
		Cursor cursor = this.getContentResolver().query(uri, null, null, null,
				null);
		if (cursor.moveToNext()) {
			resultString = cursor.getString(cursor.getColumnIndex("_data"));
		}
		Toast.makeText(this, resultString, Toast.LENGTH_LONG).show();
		return resultString;
	}

	public void onImageCaptureResult(Intent intent) {

	}

	public void onVidelCaptureResult(String vedioPath) {

	}

	public String onVedioCaptureResult(Intent intent) {
		// this.onVidelCaptureResult(this.getVideoPath(intent));
		return this.getVideoPath(intent);
	}

	public Bitmap getVideoThumbnail(String videoPath) {
		ContentResolver cr = this.getContentResolver();
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		String whereClause = MediaStore.Video.Media.DATA + " = '" + videoPath
				+ "'";
		Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.Media._ID }, whereClause, null,
				null);
		if (cursor == null || cursor.getCount() == 0) {
			if (cursor != null)
				cursor.close();
			return null;
		}
		cursor.moveToFirst();
		String videoId = cursor.getString(cursor
				.getColumnIndex(MediaStore.Video.Media._ID));
		cursor.close();
		if (videoId == null) {
			return null;
		}
		long videoIdLong = Long.parseLong(videoId);
		bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
				Images.Thumbnails.MICRO_KIND, options);
		return bitmap;
	}

	public void playVedio(String vedioPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri data = Uri.fromFile(new File(vedioPath));
		intent.setDataAndType(data, DEFAULT_VEDIO_TYPE);
		startActivity(intent);
	}

	public String getFileName(String filepath) {
		String[] nameStrings = filepath.split(File.separator);
		return nameStrings[nameStrings.length - 1];
	}
}
