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
package com.acc.android.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.acc.android.util.ResourceUtil;
import com.acc.android.util.TimeUtil;

//import com.augurit.android.hfss.R;

public class PullToRefreshView extends LinearLayout {
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;

	private int lastMotionY;

	private final boolean lock = false;

	private final boolean lockHead = false;
	private boolean lockFooter = true;

	public void disableFooter() {
		this.lockFooter = true;
	}

	public void enableFooter() {
		this.lockFooter = false;
	}

	private View headerView;

	private View footerView;

	private AdapterView<?> adapterView;

	private ScrollView scrollView;

	private int headerViewHeight;

	private int footerViewHeight;

	private ImageView headerImageView;

	private ImageView footerImageView;

	private TextView headerTextView;

	private TextView footerTextView;

	private TextView headerUpdateTextView;

	private ProgressBar headerProgressBar;

	private ProgressBar footerProgressBar;

	private LayoutInflater layoutInflater;

	private int headerState;

	private int footerState;

	private int pullState;

	private RotateAnimation flipAnimation;

	private RotateAnimation reverseFlipAnimation;

	private OnFooterRefreshListener onFooterRefreshListener;

	private OnHeaderRefreshListener onHeaderRefreshListener;

	private final Context context;

	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public PullToRefreshView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		flipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		flipAnimation.setInterpolator(new LinearInterpolator());
		flipAnimation.setDuration(250);
		flipAnimation.setFillAfter(true);
		reverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseFlipAnimation.setInterpolator(new LinearInterpolator());
		reverseFlipAnimation.setDuration(250);
		reverseFlipAnimation.setFillAfter(true);
		layoutInflater = LayoutInflater.from(getContext());
		addHeaderView();
	}

	private void addHeaderView() {
		headerView = layoutInflater.inflate(
				ResourceUtil.getLayoutId(context, "refresh_header")
				// R.layout.refresh_header
				, this, false);
		headerImageView = (ImageView) headerView.findViewById(ResourceUtil
				.getId(context, "pull_to_refresh_image")
		// R.id.pull_to_refresh_image
				);
		headerTextView = (TextView) headerView.findViewById(ResourceUtil.getId(
				context, "pull_to_refresh_text")
		// R.id.pull_to_refresh_text
				);
		headerUpdateTextView = (TextView) headerView.findViewById(ResourceUtil
				.getId(context, "pull_to_refresh_updated_at")
		// R.id.pull_to_refresh_updated_at
				);
		headerProgressBar = (ProgressBar) headerView.findViewById(ResourceUtil
				.getId(context, "pull_to_refresh_progress")
		// R.id.pull_to_refresh_progress
				);
		// this.refreshUpdateTime();
		measureView(headerView);
		headerViewHeight = headerView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				headerViewHeight);
		params.topMargin = -(headerViewHeight);
		addView(headerView, params);
	}

	public void refreshUpdateTime() {
		this.headerUpdateTextView.setText(this.getCurrentUpdateTimeString());
	}

	private String getCurrentUpdateTimeString() {
		return "更新于：" + TimeUtil.getNowDateString();
	}

	private void addFooterView() {
		footerView = layoutInflater.inflate(
				ResourceUtil.getLayoutId(context, "refresh_footer"), this,
				false);
		footerImageView = (ImageView) footerView.findViewById(ResourceUtil
				.getId(context, "pull_to_load_image"));
		footerTextView = (TextView) footerView.findViewById(ResourceUtil.getId(
				context, "pull_to_load_text"));
		footerProgressBar = (ProgressBar) footerView.findViewById(ResourceUtil
				.getId(context, "pull_to_load_progress"));
		measureView(footerView);
		footerViewHeight = footerView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				footerViewHeight);
		addView(footerView, params);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		addFooterView();
		initContentAdapterView();
	}

	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				adapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				scrollView = (ScrollView) view;
			}
		}
		if (adapterView == null && scrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - lastMotionY;
			if (isRefreshViewScroll(deltaY)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (lock) {
			return true;
		}
		int y = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - lastMotionY;
			if (pullState == PULL_DOWN_STATE) {
				headerPrepareToRefresh(deltaY);
			} else if (pullState == PULL_UP_STATE) {
				if (this.lockFooter) {
					return true;
				}
				footerPrepareToRefresh(deltaY);
			}
			lastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (pullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					headerRefreshing();
				} else {
					setHeaderTopMargin(-headerViewHeight);
				}
			} else if (pullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= headerViewHeight + footerViewHeight) {
					footerRefreshing();
				} else {
					setHeaderTopMargin(-headerViewHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private boolean isRefreshViewScroll(int deltaY) {
		if (headerState == REFRESHING || footerState == REFRESHING) {
			return false;
		}
		if (adapterView != null) {
			if (deltaY > 0) {

				View child = adapterView.getChildAt(0);
				if (child == null) {
					return false;
				}
				if (adapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					pullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = adapterView.getPaddingTop();
				if (adapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 8) {
					pullState = PULL_DOWN_STATE;
					return true;
				}
			} else if (deltaY < 0) {
				View lastChild = adapterView.getChildAt(adapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					return false;
				}
				if (lastChild.getBottom() <= getHeight()
						&& adapterView.getLastVisiblePosition() == adapterView
								.getCount() - 1) {
					pullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		if (scrollView != null) {
			View child = scrollView.getChildAt(0);
			if (deltaY > 0 && scrollView.getScrollY() == 0) {
				pullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ scrollView.getScrollY()) {
				pullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		if (newTopMargin >= 0 && headerState != RELEASE_TO_REFRESH) {
			headerTextView.setText(ResourceUtil.getStringId(context,
					"pull_to_refresh_release_label"));
			headerUpdateTextView.setVisibility(View.VISIBLE);
			headerImageView.clearAnimation();
			headerImageView.startAnimation(flipAnimation);
			headerState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -headerViewHeight) {
			headerImageView.clearAnimation();
			headerImageView.startAnimation(flipAnimation);
			headerTextView.setText(ResourceUtil.getStringId(context,
					"pull_to_refresh_pull_label"));
			headerState = PULL_TO_REFRESH;
		}
	}

	private void footerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		if (Math.abs(newTopMargin) >= (headerViewHeight + footerViewHeight)
				&& footerState != RELEASE_TO_REFRESH) {
			footerTextView.setText(ResourceUtil.getStringId(context,
					"pull_to_refresh_footer_release_label"));
			footerImageView.clearAnimation();
			footerImageView.startAnimation(flipAnimation);
			footerState = RELEASE_TO_REFRESH;
		} else if (Math.abs(newTopMargin) < (headerViewHeight + footerViewHeight)) {
			footerImageView.clearAnimation();
			footerImageView.startAnimation(flipAnimation);
			footerTextView.setText(ResourceUtil.getStringId(context,
					"pull_to_refresh_footer_pull_label"));
			footerState = PULL_TO_REFRESH;
		}
	}

	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) headerView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		if (deltaY > 0 && pullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= headerViewHeight) {
			return params.topMargin;
		}
		if (deltaY < 0 && pullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= headerViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		headerView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	private void headerRefreshing() {
		headerState = REFRESHING;
		setHeaderTopMargin(0);
		headerImageView.setVisibility(View.GONE);
		headerImageView.clearAnimation();
		headerImageView.setImageDrawable(null);
		headerProgressBar.setVisibility(View.VISIBLE);
		headerTextView.setText(ResourceUtil.getStringId(context,
				"pull_to_refresh_refreshing_label"));
		if (onHeaderRefreshListener != null) {
			onHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	private void footerRefreshing() {
		footerState = REFRESHING;
		int top = headerViewHeight + footerViewHeight;
		setHeaderTopMargin(-top);
		footerImageView.setVisibility(View.GONE);
		footerImageView.clearAnimation();
		footerImageView.setImageDrawable(null);
		footerProgressBar.setVisibility(View.VISIBLE);
		footerTextView.setText(ResourceUtil.getStringId(context,
				"pull_to_refresh_footer_refreshing_label"));
		if (onFooterRefreshListener != null) {
			onFooterRefreshListener.onFooterRefresh(this);
		}
	}

	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) headerView.getLayoutParams();
		params.topMargin = topMargin;
		headerView.setLayoutParams(params);
		invalidate();
	}

	public void onHeaderRefreshComplete() {
		setHeaderTopMargin(-headerViewHeight);
		headerImageView.setVisibility(View.VISIBLE);
		headerImageView.setImageResource(ResourceUtil.getDrawableId(context,
				"ic_pulltorefresh_arrow"));
		headerTextView.setText(ResourceUtil.getStringId(context,
				"pull_to_refresh_pull_label"));
		headerProgressBar.setVisibility(View.GONE);
		headerState = PULL_TO_REFRESH;
	}

	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onHeaderRefreshComplete();
	}

	public void onFooterRefreshComplete() {
		setHeaderTopMargin(-headerViewHeight);
		footerImageView.setVisibility(View.VISIBLE);
		footerImageView.setImageResource(ResourceUtil.getDrawableId(context,
				"ic_pulltorefresh_arrow_up"));
		footerTextView.setText(ResourceUtil.getStringId(context,
				"pull_to_refresh_footer_pull_label"));
		footerProgressBar.setVisibility(View.GONE);
		footerState = PULL_TO_REFRESH;
	}

	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			headerUpdateTextView.setVisibility(View.VISIBLE);
			headerUpdateTextView.setText(lastUpdated);
		} else {
			headerUpdateTextView.setVisibility(View.GONE);
		}
	}

	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) headerView.getLayoutParams();
		return params.topMargin;
	}

	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		onHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener) {
		onFooterRefreshListener = footerRefreshListener;
	}

	public interface OnFooterRefreshListener {
		public void onFooterRefresh(PullToRefreshView view);
	}

	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(PullToRefreshView view);
	}
}