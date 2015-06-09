package com.acc.android.frame.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

import com.acc.android.frame.util.ExpendListViewUtil;

public class ACCExpandableListView extends ExpandableListView {
	// private boolean isFirst;
	// private int i;
	// private OnAttachedListener onAttachedListener;
	//
	// public interface OnAttachedListener {
	// void onAttached();
	// }
	//
	// public void setOnAttachedListener(OnAttachedListener onAttachedListener)
	// {
	// this.onAttachedListener = onAttachedListener;
	// }

	public ACCExpandableListView(Context context) {
		super(context);
		// this.isFirst = true;
		// this.i = 1;
	}

	public ACCExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.isFirst = true;
		// this.i = 1;
		// this.expandGroup(1);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// if (this.isFirst) {
		// this.isFirst = false;
		// i++;
		// System.out.println("VVVVVVVVV!!!111111111222222222222!!!!!!!!" + i);
		// ExpendListViewUtil.expendAll(this);
		// }
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(2000,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// this.attachViewToParent(child, index, params);
		// if (this.isFirst) {
		// i++;
		// System.out.println("VVVVxxxxxxxVVVVV!!!!!!!!!!!" + i);
		// this.isFirst = false;
		// System.out.println("VVVVVVVVVV123VVVV");
		ExpendListViewUtil.expendAll(this);
		// if (this.onAttachedListener != null) {
		// this.onAttachedListener.onAttached();
		// }
	}

	// @Override
	// protected void onVisibilityChanged(View changedView, int visibility) {
	// super.onVisibilityChanged(changedView, visibility);
	// System.out.println(1232323);
	// ExpendListViewUtil.expendAll(this);
	// }

	// @Override
	// protected void onDetachedFromWindow() {
	// super.onDetachedFromWindow();
	// System.out.println("CCCCCVVVVVVVVVVVVVVVVVVVVVVVVVVV");
	// }

	// }

	// @Override
	// protected void onFinishInflate() {
	// super.onFinishInflate();
	// if (this.isFirst) {
	// // i++;
	// System.out.println("VVVVxxxxxxxVVVVV!!!!!!!!!!!" + i);
	// this.isFirst = false;
	// ExpendListViewUtil.expendAll(this);
	// }
	// }
	//
	// @Override
	// protected void onDetachedFromWindow() {
	// super.onDetachedFromWindow();
	// System.out
	// .println("VVVVVVVVVVVVVVVVVVVVVVV!!!!!!!!!!!!!!!!!!!!!!!!!!!!1233333");
	// }
	//
	// @Override
	// protected void onVisibilityChanged(View changedView, int visibility) {
	// super.onVisibilityChanged(changedView, visibility);
	// System.out.println("CCCCCCCVVVVVVVVVVVVVVVVVVVVVVVVVBBBBBBBBBB");
	// }
	//
	// public void enabelFirst() {
	// // System.out.println("CCCCCCCCBBBBBBBBBBBBBBBBBBBBB");
	// i++;
	// System.out.println("VVVVVVVV3333333333222222!!!!!!!!" + i);
	// ExpendListViewUtil.expendAll(this);
	// this.isFirst = true;
	// }

	// @Override
	// public void onGlobalLayout() {
	// super.onGlobalLayout();
	// // if (isFirst) {
	// // System.out.println("VVVVVVVVV!!!!!!!!!!!");
	// // isFirst = false;
	// // ExpendListViewUtil.expendAll(this);
	// // }
	// }

	// @Override
	// protected void onAnimationEnd() {
	// super.onAnimationEnd();
	// if (isFirst) {
	// System.out.println("0000000000000000000");
	// isFirst = false;
	// ExpendListViewUtil.expendAll(this);
	// }
	// }
	//
	// @Override
	// protected void onFinishInflate() {
	// super.onFinishInflate();
	// if (isFirst) {
	// System.out.println("1111111111111111");
	// isFirst = false;
	// ExpendListViewUtil.expendAll(this);
	// }
	// }
}