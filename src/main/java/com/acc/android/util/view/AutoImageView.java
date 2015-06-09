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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.acc.android.util.MotionEventUtil;
import com.acc.android.util.WindowUtil;

public class AutoImageView extends ImageView {
	private float density;
	private Paint progressBarPaint;
	private float progressBarWidth;
	private Float currentProgress;
	private Path currentProgressPath;
	private Matrix oldMatrix;
	// private MotionEvent oldMotionEvent;
	private Matrix currentMatrix;
	private boolean isFirstTime;
	private final static int MODE_DRAG = 0;
	private final static int MODE_ZOOM = 1;
	private final static int MODE_NONE = 2;
	// private final static float ZOOM_NONE = 1;
	private final static float ZOOM_MAX = 3;
	private final static float ZOOM_MIN = 0.5f;
	private final static float TIME_ZOOM = 200f;
	private final static float NOTICE_DISTANCE = 10;
	private int screenWidth;
	private int screenHeight;
	private int statusBarHight;
	private int currentMode;
	private PointF startPointF;
	private RectF startRectF;
	// private MotionEvent previouseMotionEvent;
	// private float previouseMotionY;
	private PointF middlePointF;
	private float touchDist;
	private float initScale;
	private RectF initRectF;
	private boolean isZooming;
	private Bitmap bitmap = null;
	// private boolean isLockSingleTap;
	private boolean isFirst;
	private boolean isLast;
	private GestureDetector gestureDetector;
	private Activity contextActivity;
	private OnSingleTapListener onSingleTapListener;

	// public void updateIsFistOrLast(boolean isFirst, boolean isLast) {
	// this.isFirst = isFirst;
	// this.isLast = isLast;
	// }

	public interface OnSingleTapListener {
		void onSingleTap();
	}

	public AutoImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoImageView(Context context, boolean isFirst, boolean isLast,
			OnSingleTapListener onSingleTapListener) {
		super(context);
		this.onSingleTapListener = onSingleTapListener;
		this.isFirst = isFirst;
		this.isLast = isLast;
		this.initData((Activity) context);
	}

	private void initData(Activity contextActivity) {
		this.contextActivity = contextActivity;
		this.oldMatrix = new Matrix();
		this.currentMatrix = new Matrix();
		this.startPointF = new PointF();
		this.middlePointF = new PointF();
		this.isFirstTime = true;
		// this.isFirst = false;
		// this.isLast = true;
		// this.previouseMotionY = 0;
		this.gestureDetector = new MGestureDetector();
		// this.setScaleType(ImageView.ScaleType.MATRIX);
		// new GestureDetector.SimpleOnGestureListener() {
		// // @Override
		// // public boolean onDoubleTap(MotionEvent e) {
		// // // TODO Auto-generated method stub
		// // return super.onDoubleTap(e);
		// // }
		// @Override
		// public boolean onScroll(MotionEvent motionEventFirst,
		// MotionEvent motionEventSecond, float distanceX,
		// float distanceY) {
		// // if(MotionEvent.ACTION_UP) {
		// //
		// // }
		// // TODO Auto-generated method stub
		// System.out.println("scroll");
		// return super.onScroll(motionEventFirst,
		// motionEventSecond, distanceX, distanceY);
		// }
		//
		// @Override
		// public boolean onFling(MotionEvent motionEventFirst,
		// MotionEvent motionEventSecond, float velocityX,
		// float velocityY) {
		// System.out.println("fling");
		// // TODO Auto-generated method stub
		// return super.onFling(motionEventFirst,
		// motionEventSecond, velocityX, velocityY);
		// }
		//
		// @Override
		// public boolean onDoubleTap(MotionEvent motionEvent) {
		// System.out.println("double");
		// return super.onDoubleTap(motionEvent);
		// }
		//
		// @Override
		// public boolean onDoubleTapEvent(MotionEvent motionEvent) {
		// // TODO Auto-generated method stub
		// System.out.println("onDoubleTapEvent");
		// return super.onDoubleTapEvent(motionEvent);
		// }
		//
		// @Override
		// public boolean onDown(MotionEvent motionEvent) {
		// switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
		// case MotionEvent.ACTION_DOWN:
		// AutoImageView.this.oldMatrix.set(this.currentMatrix);
		// this.startPointF.set(event.getX(), event.getY());
		// this.currentMode = MODE_DRAG;
		// this.startRectF = this.getCurrentRectF();
		// // this.oldMotionEvent = event;
		// break;
		// // case MotionEvent.ACTION_UP:
		// // case MotionEvent.ACTION_POINTER_UP:
		// // this.currentMode = MODE_NONE;
		// // this.onUp();
		// // ScaleAnimation scaleAnimation = new ScaleAnimation(
		// // this.currentMatrix.MSCALE_X, 1,
		// // this.currentMatrix.MSCALE_Y, 1);
		// // scaleAnimation = new ScaleAnimation(this.currentMatrix.MSCALE_X,
		// // 1,
		// // this.currentMatrix.MSCALE_Y, 1, Animation.RELATIVE_TO_SELF,
		// // 0, Animation.RELATIVE_TO_SELF, 0);
		// // scaleAnimation.setDuration(1500);
		// // this.startAnimation(scaleAnimation);
		// // return true;
		// // break;
		// case MotionEvent.ACTION_POINTER_DOWN:
		// this.touchDist = MotionEventUtil.getDistance(event);
		// if (this.touchDist > NOTICE_DISTANCE) {
		// MotionEventUtil.resolveMiddlePoint(this.middlePointF, event);
		// currentMode = MODE_ZOOM;
		// }
		// break;
		// }
		// // TODO Auto-generated method stub
		// return super.onDown(motionEvent);
		// }
		//
		// @Override
		// public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
		// // System.out.println("single");
		// AutoImageView.this.contextActivity.finish();
		// return super.onSingleTapConfirmed(motionEvent);
		// }
		//
		// @Override
		// public boolean onSingleTapUp(MotionEvent motionEvent) {
		// System.out.println("VVVVVVVVVVVVVVVVVVVVVVV");
		// AutoImageView.this.currentMode = MODE_NONE;
		// AutoImageView.this.onUp();
		// return super.onSingleTapUp(motionEvent);
		// }
		// });
		// this.gestureDetector = new GestureDetector(this.getContext()new
		// OnGestureListener() {
		//
		// @Override
		// public boolean onSingleTapUp(MotionEvent motionevent) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public void onShowPress(MotionEvent motionevent) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public boolean onScroll(MotionEvent motionevent,
		// MotionEvent motionevent1, float f, float f1) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public void onLongPress(MotionEvent motionevent) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public boolean onFling(MotionEvent motionevent,
		// MotionEvent motionevent1, float f, float f1) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean onDown(MotionEvent motionevent) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		// });
		this.screenWidth = WindowUtil.getWindowWidth(contextActivity);
		this.screenHeight = WindowUtil.getWindowHeight(contextActivity);
		this.statusBarHight = WindowUtil.getStatusBarHeight(contextActivity);
		;
	}

	// private float getStatusBarHeight() {
	// Rect frame = new Rect();
	// contextActivity.getWindow().getDecorView()
	// .getWindowVisibleDisplayFrame(frame);
	// this.statusBarHight = frame.top;
	// }

	private void makeSureInitProgressPaint() {
		if (this.progressBarPaint != null) {
			return;
		}
		this.density = getContext().getResources().getDisplayMetrics().density;
		this.progressBarPaint = new Paint();
		this.progressBarPaint.setAntiAlias(true);
		this.progressBarPaint.setColor(Color.GREEN);
		this.progressBarPaint.setStyle(Style.STROKE);
		this.progressBarWidth = 4 * this.density;
		this.progressBarPaint.setStrokeWidth(this.progressBarWidth);
	}

	private void onDoubleClick() {
		// System.out.println(this.getCurrentScale());
		// System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
		// System.out.println(this.initScale);
		// this.isLockSingleTap = true;
		if (this.getCurrentScale() > this.initScale) {
			// System.out.println("11111");
			this.zoomTo(this.initScale, this.getWidth() / 2,
					this.getHeight() / 2, TIME_ZOOM);
		} else {
			// System.out.println("22222");
			this.zoomTo(ZOOM_MAX * this.initScale, this.getWidth() / 2,
					this.getHeight() / 2, TIME_ZOOM);
		}
		// if (imageView.getScale() > imageView.getScaleRate()) {
		// imageView.zoomTo(imageView.getScaleRate(), Main.screenWidth / 2,
		// Main.screenHeight / 2, 200f);
		// // imageView.layoutToCenter();
		// } else {
		// imageView.zoomTo(1.0f, Main.screenWidth / 2, Main.screenHeight / 2,
		// 200f);
		// }
	}

	public void setProgress(Float progress) {
		progress = Math.max(progress, 0);
		progress = Math.min(progress, 1);
		this.currentProgress = progress;
	}

	private void updateProgress(float progress) {
		this.makeSureInitProgressPaint();
		this.currentProgress = progress;
		int width = this.getDrawable().getIntrinsicWidth();
		int height = this.getDrawable().getIntrinsicHeight();
		float[] values = new float[10];
		this.getImageMatrix().getValues(values);
		float tranceX = values[2];
		float tranceY = values[5];
		float scaleX = values[0];
		float scaleY = values[4];
		float halfProgressBarWidth = this.progressBarWidth / 2;
		width *= scaleX;
		height *= scaleY;
		float top = tranceY + halfProgressBarWidth;
		float left = tranceX + halfProgressBarWidth;
		float right = left + width - this.progressBarWidth;
		float bottom = top + height - this.progressBarWidth;
		width -= this.progressBarWidth;
		height -= -this.progressBarWidth;
		int halfWidth = width / 2;
		float progressLength = 2 * (width + height) * this.currentProgress;
		currentProgressPath = new Path();
		currentProgressPath.moveTo(left + halfWidth, top);
		if (progressLength < halfWidth) {
			currentProgressPath.lineTo(left + halfWidth + progressLength, top);
		} else {
			currentProgressPath.lineTo(right, top);
			if (progressLength < halfWidth + height) {
				currentProgressPath.lineTo(right, top + progressLength
						- halfWidth);
			} else {
				currentProgressPath.lineTo(right, bottom);
				if (progressLength < width + halfWidth + height) {
					currentProgressPath.lineTo(right
							- (progressLength - halfWidth - height), bottom);
				} else {
					currentProgressPath.lineTo(left, bottom);
					if (progressLength < width + halfWidth + height * 2) {
						currentProgressPath
								.lineTo(left,
										bottom
												- (progressLength - width
														- halfWidth - height));
					} else {
						currentProgressPath.lineTo(left, top);
						if (progressLength < width * 2 + height * 2) {
							currentProgressPath.lineTo(
									left
											+ (progressLength - width
													- halfWidth - 2 * height),
									top);
						} else {
							currentProgressPath.lineTo(left + halfWidth, top);
						}
					}
				}
			}
		}
		this.invalidate();
	}

	private RectF getCurrentRectF() {
		int width = this.getDrawable().getIntrinsicWidth();
		int height = this.getDrawable().getIntrinsicHeight();
		float[] values = this.getValues(this.getImageMatrix());
		float tranceX = values[2];
		float tranceY = values[5];
		float scaleX = values[0];
		float scaleY = values[4];
		width *= scaleX;
		height *= scaleY;
		float top = tranceY;
		float left = tranceX;
		float right = left + width;
		float bottom = top + height;
		RectF rectF = new RectF();
		rectF.left = left;
		rectF.right = right;
		rectF.top = top;
		rectF.bottom = bottom;
		return rectF;
	}

	// private boolean isEnableGalleryMove(Direction direction) {
	// if (true) {
	// return false;
	// }
	// int width = this.getDrawable().getIntrinsicWidth();
	// int height = this.getDrawable().getIntrinsicHeight();
	// float[] values = this.getValues(this.getImageMatrix());
	// float tranceX = values[2];
	// float tranceY = values[5];
	// float scaleX = values[0];
	// float scaleY = values[4];
	// width *= scaleX;
	// height *= scaleY;
	// float top = tranceY;
	// float left = tranceX;
	// float right = left + width;
	// float bottom = top + height;
	// RectF df;
	// System.out.println(direction);
	// if (direction == Direction.STOP) {
	// return true;
	// }
	// if (direction == Direction.STOP) {
	// System.out.println("000000000000000000000");
	// return false;
	// }
	// RectF rectF = this.getCurrentRectF();
	// if (
	// // tranceY != 0
	// // &&
	// rectF.top > 0 && rectF.bottom < this.getHeight()) {
	// return false;
	// }
	// if (rectF.left > this.initRectF.left
	// && (direction == Direction.RIGHT
	// || direction == Direction.RIGHTDOWN || direction == Direction.UPRIGHT)) {
	// return false;
	// }
	// if (rectF.right < this.initRectF.right
	// && (direction == Direction.LEFT
	// || direction == Direction.LEFTUP || direction == Direction.DOWNLEFT)) {
	// return false;
	// }
	// if (false) {
	// if (tranceX != 0 && left > 0 && right < this.screenWidth) {
	// return false;
	// }
	// if (left > 0
	// && (direction == Direction.RIGHT
	// || direction == Direction.RIGHTDOWN || direction ==
	// Direction.UPRIGHT)) {
	// // System.out.println("1111111111111111111");
	// return false;
	// }
	// if (right < this.screenWidth
	// && (direction == Direction.LEFT
	// || direction == Direction.LEFTUP || direction ==
	// Direction.DOWNLEFT)) {
	// // System.out.println("222222222222222222");
	// return false;
	// }
	// if (bottom < 0
	// && (direction == Direction.UP
	// || direction == Direction.UPRIGHT || direction ==
	// Direction.LEFTUP)) {
	// // System.out.println("33333333333333");
	// return false;
	// }
	// if (top > this.screenHeight
	// && (direction == Direction.DOWN
	// || direction == Direction.DOWNLEFT || direction ==
	// Direction.RIGHTDOWN)) {
	// // System.out.println("444444444444444444");
	// return false;
	// }
	// }
	// return true;
	// }

	// private void calculateInitScale(Bitmap bitmap) {
	// // System.out.println(bitmap.getWidth());
	// // System.out.println(this.getWidth());
	// // System.out.println(this.getDrawable().getBounds().);
	// float scaleWidth = bitmap.getWidth() / (float) this.screenWidth;
	// float scaleHeight = bitmap.getHeight() / (float) this.screenHeight;
	// this.currentMatrix = this.getImageMatrix();
	// this.initScale = this.getCurrentScale();
	// }

	private void calculateInitScale() {
		// Drawable d = null;
		// d.getIntrinsicHeight();
		float scaleWidth = this.screenWidth / (float) this.bitmap.getWidth();
		float scaleHeight = this.screenHeight / (float) this.bitmap.getHeight();
		this.initScale = Math.min(scaleWidth, scaleHeight);
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		// System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVV");
		// System.out.println(this);
		super.setImageBitmap(bitmap);
		// this.setImageMatrix(new Matrix());
		this.setScaleType(ImageView.ScaleType.MATRIX);
		this.bitmap = bitmap;
		this.isFirstTime = true;
		// this.currentMatrix = new Matrix();
		this.calculateInitScale();
		// this.calculateInitScale(bitmap);
		// this.currentMatrix.postScale(this.initScale, this.initScale);
		// this.setImageMatrix(this.currentMatrix);
		// this.zoomTo(this.initScale, this.screenWidth / 2f,
		// this.screenHeight / 2f);
		this.initLayout();
		// this.zoomTo(this.initScale, this.screenWidth / 2f,
		// this.screenHeight / 2f, TIME_ZOOM);
	}

	public void initLayout() {
		this.postScale(this.initScale, this.initScale);
		// System.out.println("XXXXXXXXXXXXXXXXXXXX");
		// System.out.println(this.initScale);
		float width = this.bitmap.getWidth() * this.initScale;
		float height = this.bitmap.getHeight() * this.initScale;
		float fillWidth = this.screenWidth - width;
		float fillHeight = this.screenHeight - this.statusBarHight - height;
		float tranWidth = 0f;
		float tranHeight = 0f;
		if (fillWidth > 0)
			tranWidth = fillWidth / 2f;
		if (fillHeight > 0)
			tranHeight = fillHeight / 2f;
		this.postTranslate(tranWidth, tranHeight);
		// this.zoomTo(this.initScale, this.getWidth() / 2f, this.getHeight() /
		// 2f,
		// TIME_ZOOM);
		// this.currentMatrix.postScale(1, 1, this.screenWidth / 2f,
		// this.screenHeight / 2f);
		// setImageMatrix(getImageViewMatrix());
	}

	private float getValue(Matrix matrix, int whichValue) {
		return this.getValues(matrix)[whichValue];
	}

	private float[] getValues(Matrix matrix) {
		float[] values = new float[10];
		matrix.getValues(values);
		return values;
	}

	private float getCurrentScale() {
		return this.getValue(this.currentMatrix, Matrix.MSCALE_X);
	}

	private void zoomTo(final float scale, final float centerX,
			final float centerY, final float duration) {
		this.isZooming = true;
		final float increment = (scale - this.getCurrentScale()) / duration;
		final float oldScale = this.getCurrentScale();
		final long startTime = System.currentTimeMillis();
		this.getHandler().post(new Runnable() {
			@Override
			public void run() {
				long nowTime = System.currentTimeMillis();
				float currentMs = Math.min(duration, nowTime - startTime);
				float target = oldScale + (increment * currentMs);
				// System.out.println("XXXXXXXXX");
				// System.out.println(target);
				// System.out.println(duration);
				AutoImageView.this.zoomTo(target, centerX, centerY);
				if (currentMs < duration) {
					AutoImageView.this.getHandler().post(this);
				} else {
					AutoImageView.this.isZooming = false;
				}
			}
		});
	}

	private void zoomTo(float scale, float centerX, float centerY) {
		if (scale > ZOOM_MAX * this.initScale) {
			scale = ZOOM_MAX * this.initScale;
		} else if (scale < ZOOM_MIN * this.initScale) {
			scale = ZOOM_MIN * this.initScale;
		}
		float oldScale = this.getCurrentScale();
		float deltaScale = scale / oldScale;
		this.currentMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		this.setImageMatrix(this.currentMatrix);
		this.center(true, true);
	}

	protected void center(boolean horizontal, boolean vertical) {
		if (this.bitmap == null) {
			return;
		}
		Matrix matrix = this.getImageMatrix();
		RectF rect = new RectF(0, 0, this.bitmap.getWidth(),
				this.bitmap.getHeight());
		matrix.mapRect(rect);
		float height = rect.height();
		float width = rect.width();
		float deltaX = 0, deltaY = 0;
		if (vertical) {
			int viewHeight = getHeight();
			if (height < viewHeight) {
				deltaY = (viewHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < viewHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}
		if (horizontal) {
			int viewWidth = getWidth();
			if (width < viewWidth) {
				deltaX = (viewWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < viewWidth) {
				deltaX = viewWidth - rect.right;
			}
		}
		this.postTranslate(deltaX, deltaY);
		// this.setImageMatrix(this.currentMatrix);
	}

	private void postTranslate(float dx, float dy) {
		this.currentMatrix.postTranslate(dx, dy);
		this.setImageMatrix(this.currentMatrix);
	}

	private void postScale(float scaleX, float scaleY) {
		this.currentMatrix.postScale(scaleX, scaleY);
		this.setImageMatrix(this.currentMatrix);
	}

	// private void animateToImageBitmap(Bitmap bitmap) {
	// }

	public void clearProgress() {
		this.currentProgressPath = null;
		this.progressBarPaint = null;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (this.currentProgress != null) {
			this.updateProgress(this.currentProgress);
			// if (this.currentProgressPath != null) {
			canvas.drawPath(this.currentProgressPath, this.progressBarPaint);
			// }
		}
	}

	private void initWhenFirstTouchNewBitmap() {
		// System.out.println("BBBBBBBBBBBBBBBBBBBB");
		// System.out.println(this);
		// this.setScaleType(ImageView.ScaleType.MATRIX);
		this.currentMatrix.set(this.getImageMatrix());
		// this.initScale = this.getCurrentScale();
		this.isFirstTime = false;
		this.initRectF = this.getCurrentRectF();
	}

	private PointF getTranslatePointF(
	// float fromX, float fromY,
			float previousX, float previousY, float toX, float toY) {
		PointF pointF = new PointF();
		RectF currentRectF = this.getCurrentRectF();
		// RectF startRectF = this.startRectF;
		// if() {
		//
		// }
		// float fromX = 0;
		// Direction direction = DirectionUtil.getDirection(fromX, fromY, toX,
		// toY);
		if (currentRectF.top >= 0 && currentRectF.bottom <= this.getHeight()) {
			pointF.y = 0;
		} else {
			float tempTranceY = toY - this.startPointF.y;
			// float previouseY = 0;
			// if (this.previouseMotionEvent == null) {
			// // fromX = this.startPointF.x;
			// previouseY = this.startPointF.y;
			// } else {
			// // fromX = this.previouseMotionEvent.getX();
			// previouseY = this.previouseMotionEvent.getY();
			// }
			// System.out.println(fromY);
			// System.out.println(toY);
			// System.out.println(fromY <= toY);
			if (
			// (
			currentRectF.top
			// + tempTranceY
					>= this.getHeight() / 4
					&& toY > previousY
			// && fromY < toY
			// && tempTranceY > 0
			// )
			// || (currentRectF.bottom + tempTranceY < this.getHeight() * 3 / 4
			// && tempTranceY < 0)
			) {
				// if() {
				//
				// }
				tempTranceY = this.getHeight() / 4 - this.startRectF.top;
				// tempTranceY -= 5;
				// tempTranceY = previousY - fromY;
			} else if (currentRectF.bottom
			// + tempTranceY
					<= this.getHeight() * 3 / 4
					&& toY < previousY
			// && this.previouseMotionY - toY > 0
			// && fromY > toY
			// && tempTranceY < 0
			) {
				tempTranceY = this.getHeight() * 3 / 4 - this.startRectF.bottom
				// - currentRectF.bottom
				;
				// tempTranceY += 5;
			}
			pointF.y = tempTranceY;
		}
		pointF.x = toX - this.startPointF.x;
		// System.out.println("xxxxxxxxxxxx");
		// System.out.println(this.isFirst);
		// System.out.println(this.isLast);
		// if (false) {
		if (currentRectF.left >= this.initRectF.left && toX > previousX
				&& !(this.isFirst && currentRectF.left < this.getWidth())) {
			pointF.x = this.initRectF.left - this.startRectF.left;
		}
		if (currentRectF.right <= this.initRectF.right && toX < previousX
				&& !(this.isLast && currentRectF.right > 0)) {
			pointF.x = this.initRectF.right - this.startRectF.right;
		}
		// }
		return pointF;
	}

	private void onDoubleUp() {
		AutoImageView.this.currentMode = MODE_NONE;
		if (this.getCurrentScale() < this.initScale) {
			this.zoomTo(this.initScale, this.getWidth() / 2,
					this.getHeight() / 2, TIME_ZOOM);
		} else {
			RectF currentRectF = this.getCurrentRectF();
			if (currentRectF.bottom - currentRectF.top < this.getHeight()) {
				this.zoomTo(this.getCurrentScale(), this.getWidth() / 2,
						this.getHeight() / 2, TIME_ZOOM);
			}
			// else if(currentRectF.bottom - currentRectF.top <
			// this.getHeight()) {
			// this.zoomTo(this.getCurrentScale(), this.getWidth() / 2,
			// this.getHeight() / 2, TIME_ZOOM);
			// }
		}
	}

	private void onSingleTapUp() {
		// if (this.isLockSingleTap) {
		// this.isLockSingleTap = false;
		// return;
		// }
		if (this.isZooming) {
			return;
		}
		RectF currentRectF = this.getCurrentRectF();
		AutoImageView.this.currentMode = MODE_NONE;
		float tranWidth = 0;
		// float tranHeight = 0;
		if (this.isFirst && currentRectF.left > this.initRectF.left) {
			tranWidth = this.initRectF.left - currentRectF.left;
		}
		if (this.isLast && currentRectF.right < this.initRectF.right) {
			tranWidth = this.initRectF.right - currentRectF.right;
		}
		// else {
		// RectF currentRectF = this.getCurrentRectF();
		// if (currentRectF.bottom - currentRectF.top < this.getHeight()) {
		// this.zoomTo(this.getCurrentScale(), this.getWidth() / 2,
		// this.getHeight() / 2, TIME_ZOOM);
		// }
		// // else if(currentRectF.bottom - currentRectF.top <
		// // this.getHeight()) {
		// // this.zoomTo(this.getCurrentScale(), this.getWidth() / 2,
		// // this.getHeight() / 2, TIME_ZOOM);
		// // }
		// }
		if (tranWidth != 0) {
			this.postTranslate(tranWidth, 0);
		}
	}

	// private void onMove() {
	// RectF currentRectF = this.getCurrentRectF();
	// if() {
	//
	// }
	// pointF.x = toX - fromX;
	// if (currentRectF.top >= 0 && currentRectF.bottom <= this.getHeight())
	// {
	// pointF.y = 0;
	// } else {
	// float tempTranceY = toY - fromY;
	// if (currentRectF.top + tempTranceY > this.getHeight() / 4
	// && tempTranceY > 0) {
	// tempTranceY -= 1;
	// }
	// if (currentRectF.bottom + tempTranceY < this.getHeight() * 3 / 4
	// && tempTranceY < 0) {
	// tempTranceY += 1;
	// }
	// pointF.y = tempTranceY;
	// }
	// if (currentRectF.top > 0 && currentRectF.bottom < this.getHeight()) {
	// // this.zoomTo(this.getCurrentScale(), this.getWidth() / 2,
	// // this.getHeight() / 2, TIME_ZOOM);
	// }
	// }

	// public void onGalleryScroll(MotionEvent motionEventFirst,MotionEvent
	// motionEventSecond, float distanceX, float distanceY, Gallery gallery) {
	// RectF rectF = AutoImageView.this.getCurrentRectF();
	// boolean isNeedGalleryScroll = false;
	// if (rectF.width() < this.getWidth())
	// {
	// isNeedGalleryScroll = true;
	// } else {
	// if (distanceX > 0)
	// {
	// if (rectF.left > 0) {
	// super.onScroll(e1, e2, distanceX, distanceY);
	// } else if (right < Main.screenWidth) {
	// super.onScroll(e1, e2, distanceX, distanceY);
	// } else {
	// imageView.postTranslate(-distanceX, -distanceY);
	// }
	// } else if (distanceX < 0)// 向右滑动
	// {
	// if (r.right < Main.screenWidth) {
	// super.onScroll(e1, e2, distanceX, distanceY);
	// } else if (left > 0) {
	// super.onScroll(e1, e2, distanceX, distanceY);
	// } else {
	// imageView.postTranslate(-distanceX, -distanceY);
	// }
	// }
	//
	// }
	// gallery.onScroll(motionEventFirst, motionEventSecond, distanceX,
	// distanceY);
	// }

	public boolean isNeedNextMotionEvent(MotionEvent motionEventFirst,
			MotionEvent motionEventSecond) {
		RectF rectF = AutoImageView.this.getCurrentRectF();
		if (rectF == null || AutoImageView.this.initRectF == null) {
			return false;
		}
		// if (
		// // tranceY != 0
		// // &&
		// rectF.top > 0 && rectF.bottom < this.getHeight()) {
		// return false;
		// }
		if (rectF.left >= AutoImageView.this.initRectF.left
				&& (motionEventSecond.getX() > motionEventFirst.getX())) {
			return false;
		}
		if (rectF.right <= AutoImageView.this.initRectF.right
				&& (motionEventSecond.getX() < motionEventFirst.getX())) {
			return false;
		}
		return true;
	}

	// @Override
	// @Override
	public void onMTouchEvent(MotionEvent event) {
		if (this.currentProgressPath != null || this.bitmap == null) {
			return;
		}
		// if (this.isFirstTime) {
		// this.initWhenFirstTouchNewBitmap();
		// }
		// this.gestureDetector.onTouchEvent(event);
		// System.out.println("111111111111111111xxxxxxxxxxxxxxxxxxxxxx");
		// boolean isEnableGalleryMove = true;
		// Direction direction = null;
		// System.out.println("xxxxxxxxx55555555555555xxxxxxxxxxxxx");
		// switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// case MotionEvent.ACTION_DOWN:
		// this.oldMatrix.set(this.currentMatrix);
		// this.startPointF.set(event.getX(), event.getY());
		// this.currentMode = MODE_DRAG;
		// this.startRectF = this.getCurrentRectF();
		// // this.oldMotionEvent = event;
		// break;
		// // case MotionEvent.ACTION_UP:
		// // case MotionEvent.ACTION_POINTER_UP:
		// // this.currentMode = MODE_NONE;
		// // this.onUp();
		// // ScaleAnimation scaleAnimation = new ScaleAnimation(
		// // this.currentMatrix.MSCALE_X, 1,
		// // this.currentMatrix.MSCALE_Y, 1);
		// // scaleAnimation = new ScaleAnimation(this.currentMatrix.MSCALE_X,
		// // 1,11
		// // this.currentMatrix.MSCALE_Y, 1, Animation.RELATIVE_TO_SELF,
		// // 0, Animation.RELATIVE_TO_SELF, 0);
		// // scaleAnimation.setDuration(1500);
		// // this.startAnimation(scaleAnimation);
		// // return true;
		// // break;
		// case MotionEvent.ACTION_POINTER_DOWN:
		// System.out.println("GGGGGGGGGGGGGGG11111111111111111");
		// this.touchDist = MotionEventUtil.getDistance(event);
		// if (this.touchDist > NOTICE_DISTANCE) {
		// MotionEventUtil.resolveMiddlePoint(this.middlePointF, event);
		// currentMode = MODE_ZOOM;
		// }
		// System.out.println("VBBBBBBBBBBBBBBBBBBBB");
		// break;
		// case MotionEvent.ACTION_MOVE:
		// // this.onMove();
		// if (currentMode == MODE_DRAG) {
		// // float finalX = 0;
		// // float finalY = 0;
		// // if (event.getY() > this.initRectF.left
		// // || rectF.right < this.initRectF.right) {
		// //
		// // }
		// this.currentMatrix.set(this.oldMatrix);
		// PointF translatePointF = this.getTranslatePointF(
		// // startPointF.x,
		// // startPointF.y,
		// // this.oldMotionEvent.getX(),
		// // this.oldMotionEvent.getY(),
		// event.getX(), event.getY());
		// this.currentMatrix.postTranslate(translatePointF.x,
		// translatePointF.y);
		// // this.previouseMotionY = event.getY();
		// // this.previouseMotionEvent = event;
		// } else if (currentMode == MODE_ZOOM) {
		// float newDist = MotionEventUtil.getDistance(event);
		// float currentScale = this.getCurrentScale();
		// if (newDist > NOTICE_DISTANCE
		// && !(currentScale >= ZOOM_MAX * this.initScale && newDist >
		// this.touchDist)
		// && !(currentScale <= ZOOM_MIN * this.initScale && newDist <
		// this.touchDist)) {
		// float scale = newDist / this.touchDist;
		// this.currentMatrix.set(this.oldMatrix);
		// this.currentMatrix.postScale(scale, scale, middlePointF.x,
		// this.getHeight() / 2);
		// }
		// }
		// // this.oldMotionEvent = event;
		// break;
		// }
		// boolean isNeedNextMotionEvent = this.gestureDetector
		// .onTouchEvent(event);
		// this.setScaleType(ScaleType.MATRIX);
		// this.setImageMatrix(this.currentMatrix);
		// boolean dsfd =
		this.gestureDetector.onTouchEvent(event);
		// System.out.println("+++++");
		// System.out.println(dsfd);
		// return dsfd;
		// return false;
		// ;
		// true
		// currentMode == MODE_ZOOM
		// || this.isEnableGalleryMove(DirectionUtil.getDirection(
		// startPointF.x, startPointF.y, event.getX(),
		// event.getY()))
		// true
		// ;
	}

	public class MGestureDetector extends GestureDetector {
		private final MGestureListener mGestureListener;

		public MGestureDetector() {
			// mGestureListener = new MGestureListener();
			this(new MGestureListener());
		}

		public MGestureDetector(MGestureListener mGestureListener) {
			super(mGestureListener);
			this.mGestureListener = mGestureListener;
		}

		@Override
		public boolean onTouchEvent(MotionEvent motionEvent) {
			// System.out.println("CCCCCCCCCCCCCCCCCCCCCCC" + this);
			if (AutoImageView.this.isFirstTime) {
				AutoImageView.this.initWhenFirstTouchNewBitmap();
			}
			// if (true) {
			// return true;
			// }
			boolean isNeedNextMotionEvent = false;
			// System.out.println("GGGGGGGGGGGGGGbbbbbbbbbbbbbbbbG");
			switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
			// case MotionEvent.ACTION_DOWN:
			// System.out.println("ACTION_DOWN");
			// AutoImageView.this.oldMatrix
			// .set(AutoImageView.this.currentMatrix);
			// AutoImageView.this.startPointF.set(motionEvent.getX(),
			// motionEvent.getY());
			// AutoImageView.this.currentMode = MODE_DRAG;
			// AutoImageView.this.startRectF = AutoImageView.this
			// .getCurrentRectF();
			// // this.oldMotionEvent = event;
			// break;
			case MotionEvent.ACTION_POINTER_UP:
				// this.currentMode = MODE_NONE;
				// this.onUp();
				// ScaleAnimation scaleAnimation = new ScaleAnimation(
				// this.currentMatrix.MSCALE_X, 1,
				// this.currentMatrix.MSCALE_Y, 1);
				// scaleAnimation = new
				// ScaleAnimation(this.currentMatrix.MSCALE_X,
				// 1,
				// this.currentMatrix.MSCALE_Y, 1, Animation.RELATIVE_TO_SELF,
				// 0, Animation.RELATIVE_TO_SELF, 0);
				// scaleAnimation.setDuration(1500);
				// this.startAnimation(scaleAnimation);
				// return true;
				// break;
				// System.out.println("1111111111111111111");
				isNeedNextMotionEvent = this.mGestureListener
						.onDoubleUp(motionEvent);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// System.out.println("GGGGGGGGGGGGGGG");
				isNeedNextMotionEvent = this.mGestureListener
						.onDoubleDown(motionEvent);
				break;
			case MotionEvent.ACTION_UP:
				if (!(AutoImageView.this.currentMode == MODE_ZOOM)) {
					this.mGestureListener.onSingleTapUp(motionEvent);
				}
				// break;
			default:
				isNeedNextMotionEvent = super.onTouchEvent(motionEvent);
				// break;
			}
			// AutoImageView.this.setScaleType(ScaleType.MATRIX);
			AutoImageView.this.setImageMatrix(AutoImageView.this.currentMatrix);
			return isNeedNextMotionEvent;
		}
	}

	public class MGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		public boolean onDoubleUp(MotionEvent motionEvent) {
			// System.out.println("onDoubleUp");
			AutoImageView.this.onDoubleUp();
			return true;
		}

		public boolean onDoubleDown(MotionEvent motionEvent) {
			// System.out.println("onDoubleDown");
			// System.out.println("VBBBBBBBBBBBBBBBBBBBB");
			AutoImageView.this.touchDist = MotionEventUtil
					.getDistance(motionEvent);
			if (AutoImageView.this.touchDist > NOTICE_DISTANCE) {
				MotionEventUtil.resolveMiddlePoint(
						AutoImageView.this.middlePointF, motionEvent);
				currentMode = MODE_ZOOM;
			}
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent motionEventFirst,
				MotionEvent motionEventSecond, float distanceX, float distanceY) {
			// if (motionEventFirst == null) {
			// return false;
			// }
			// if(MotionEvent.ACTION_UP) {
			//
			// }
			// TODO Auto-generated method stub
			// System.out.println("scroll");
			// boolean isNeedNextMotionEvent = true;
			if (motionEventFirst == null) {
				return false;
			}
			if (currentMode == MODE_DRAG) {
				// float finalX = 0;
				// float finalY = 0;
				// if (event.getY() > this.initRectF.left
				// || rectF.right < this.initRectF.right) {
				//
				// }
				// System.out.println("1111111111XXXXXXXXXXXXXXXXXX");
				// isNeedNextMotionEvent = AutoImageView.this
				// .isNeedNextMotionEvent(motionEventFirst,
				// motionEventSecond);
				// if (!isNeedNextMotionEvent) {
				// // System.out.println("XXXXXXXXXXXXXXXXXX");
				// return isNeedNextMotionEvent;
				// }
				AutoImageView.this.currentMatrix
						.set(AutoImageView.this.oldMatrix);
				PointF translatePointF = AutoImageView.this.getTranslatePointF(
						// startPointF.x,
						// startPointF.y,
						motionEventFirst.getX(), motionEventFirst.getY(),
						motionEventSecond.getX(), motionEventSecond.getY());
				AutoImageView.this.currentMatrix.postTranslate(
						translatePointF.x, translatePointF.y);
				// this.previouseMotionY = event.getY();
				// this.previouseMotionEvent = event;
			} else if (currentMode == MODE_ZOOM) {
				float newDist = MotionEventUtil.getDistance(motionEventSecond);
				float currentScale = AutoImageView.this.getCurrentScale();
				if (newDist > NOTICE_DISTANCE
						&& !(currentScale >= ZOOM_MAX
								* AutoImageView.this.initScale && newDist > AutoImageView.this.touchDist)
						&& !(currentScale <= ZOOM_MIN
								* AutoImageView.this.initScale && newDist < AutoImageView.this.touchDist)) {
					float scale = newDist / AutoImageView.this.touchDist;
					AutoImageView.this.currentMatrix
							.set(AutoImageView.this.oldMatrix);
					AutoImageView.this.currentMatrix.postScale(scale, scale,
							middlePointF.x, AutoImageView.this.getHeight() / 2);
				}
			}
			return true;
		}

		@Override
		public boolean onFling(MotionEvent motionEventFirst,
				MotionEvent motionEventSecond, float velocityX, float velocityY) {
			// System.out.println("onFling");
			// System.out.println("fling");
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent motionEvent) {
			// System.out.println("double");
			// System.out.println("onDoubleTap");
			AutoImageView.this.onDoubleClick();
			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent motionEvent) {
			// TODO Auto-generated method stub
			// System.out.println("onDoubleTapEvent");
			// System.out.println("onDoubleTapEvent");
			return true;
		}

		@Override
		public boolean onDown(MotionEvent motionEvent) {
			// System.out.println("onDown");
			// System.out.println("XXXXXXXXXXXXXX");
			// switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
			// case MotionEvent.ACTION_DOWN:
			// System.out.println("ACTION_DOWN");
			AutoImageView.this.oldMatrix.set(AutoImageView.this.currentMatrix);
			AutoImageView.this.startPointF.set(motionEvent.getX(),
					motionEvent.getY());
			AutoImageView.this.currentMode = MODE_DRAG;
			AutoImageView.this.startRectF = AutoImageView.this
					.getCurrentRectF();
			// this.oldMotionEvent = event;
			// break;
			// case MotionEvent.ACTION_UP:
			// case MotionEvent.ACTION_POINTER_UP:
			// this.currentMode = MODE_NONE;
			// this.onUp();
			// ScaleAnimation scaleAnimation = new ScaleAnimation(
			// this.currentMatrix.MSCALE_X, 1,
			// this.currentMatrix.MSCALE_Y, 1);
			// scaleAnimation = new ScaleAnimation(this.currentMatrix.MSCALE_X,
			// 1,
			// this.currentMatrix.MSCALE_Y, 1, Animation.RELATIVE_TO_SELF,
			// 0, Animation.RELATIVE_TO_SELF, 0);
			// scaleAnimation.setDuration(1500);
			// this.startAnimation(scaleAnimation);
			// return true;
			// break;
			// case MotionEvent.ACTION_POINTER_DOWN:
			// System.out.println("ACTION_POINTER_DOWN");
			// AutoImageView.this.touchDist = MotionEventUtil
			// .getDistance(motionEvent);
			// if (AutoImageView.this.touchDist > NOTICE_DISTANCE) {
			// MotionEventUtil.resolveMiddlePoint(
			// AutoImageView.this.middlePointF, motionEvent);
			// currentMode = MODE_ZOOM;
			// }
			// break;
			// }
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
			// System.out.println("single");
			// System.out.println("onSingleTapConfirmed");
			if (AutoImageView.this.onSingleTapListener != null) {
				AutoImageView.this.onSingleTapListener.onSingleTap();
			}
			return true;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent motionEvent) {
			// System.out.println("onSingleTapUp");
			// System.out.println("VVVVVVVVVVVVVVVVVVVVVVV");
			AutoImageView.this.onSingleTapUp();
			return true;
		}
	}
}