package com.acc.android.frame.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView {

	public MarqueeTextView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet
		// , R.style.default_marquee_text
		);
		int[] attrs = new int[] { android.R.attr.textSize,
				android.R.attr.textColor };
		// InternelResouceUtil.getResouceStyleableId("TextView");
		TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
				attrs);
		this.setTextColor(typedArray.getColor(1, Color.BLACK));
		int textSize = typedArray.getDimensionPixelSize(0, 18);
		System.out.println("textSize:" + textSize);
		this.setTextSize(textSize);
		// if (false) {
		// int textColorHighlight = 0;
		// ColorStateList textColor = null;
		// ColorStateList textColorHint = null;
		// ColorStateList textColorLink = null;
		// int textSize = 15;
		// int typefaceIndex = -1;
		// int styleIndex = -1;
		//
		// /*
		// * Look the appearance up without checking first if it exists
		// * because almost every TextView has one and it greatly simplifies
		// * the logic to be able to parse the appearance first and then let
		// * specific tags for this View override it.
		// */
		// TypedArray appearance = null;
		// int TextView_textAppearance = InternelResouceUtil
		// .getResouceStyleableId("TextView_textAppearance");
		// int ap = a.getResourceId(TextView_textAppearance, -1);
		// if (ap != -1) {
		// int[] TextAppearance = InternelResouceUtil
		// .getResouceStyleableId("TextAppearance");
		// appearance = context.obtainStyledAttributes(ap, TextAppearance);
		// }
		// int TextAppearance_textColor = InternelResouceUtil
		// .getResouceStyleableId("TextAppearance_textColor");
		// int TextAppearance_textSize = InternelResouceUtil
		// .getResouceStyleableId("TextAppearance_textSize");
		// if (appearance != null) {
		// int n = appearance.getIndexCount();
		// for (int i = 0; i < n; i++) {
		// int attr = appearance.getIndex(i);
		// if (TextAppearance_textColor == attr) {
		// textColor = appearance.getColorStateList(attr);
		// } else if (TextAppearance_textSize == attr) {
		// textSize = appearance.getDimensionPixelSize(attr, 189);
		// }
		// // switch (attr) {
		// // case TextAppearance_textColor:
		// // textColor = appearance.getColorStateList(attr);
		// // break;
		// // case TextAppearance_textSize:
		// // textSize = appearance.getDimensionPixelSize(attr,
		// // textSize);
		// // break;
		// }
		// // }
		//
		// appearance.recycle();
		// }
		//
		// if (textColor == null) {
		// this.setTextColor(Color.RED);
		// }
		//
		// this.setTextSize(textSize);
		// }
		// if (textSize == null) {
		// this.setTextSize(textSize);
		// }

		// int[] attrsKeys = new int[] { android.R.attr.textSize,
		// android.R.attr.textColor };
		// Arrays.sort(attrsKeys);
		// TypedArray typeArray = context.obtainStyledAttributes(attrsKeys);
		// int textSize = typeArray.getInteger(0, 18);
		// int textColor = typeArray.getColor(1, Color.BLACK);
		// attrs.getAttributeIntValue(nameSpace, "textColor",
		// Color.RED);
		// String nameSpace = "http://schemas.android.com/apk/res/android";
		// int textSize = attrs.getAttributeIntValue(nameSpace, "textSize", 18);
		// int textColor = attrs.getAttributeIntValue(nameSpace, "textColor",
		// Color.RED);
		// int textSize = attrs.getAttributeIntValue(null, "textSize", 18);
		// int textColor = attrs.getAttributeIntValue(null, "textColor",
		// Color.BLACK);
		// this.getTextColor(context, context.o, Color.BLACK);
		// this.gettext
		// this.setTextSize(textSize);
		// this.setTextColor(textColor);
		this.setSingleLine(true);
		this.setMarqueeRepeatLimit(-1);
		this.setEllipsize(TruncateAt.MARQUEE);
		this.setSelected(true);
		// this.requestFocus();
		// this.setFocusable(true);
		// this.setFocusableInTouchMode(true);
	}

	// public MarqueeTextView(Context context, AttributeSet attrs, int defStyle)
	// {
	// super(context, attrs, defStyle);
	// }
	//
	// public MarqueeTextView(Context context) {
	// super(context);
	// }
	//

	// @Override
	// public boolean isFocused() {
	// return true;
	// }
}
