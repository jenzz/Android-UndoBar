package com.jensdriller.libs.undobar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

class MaxWidthRelativeLayout extends RelativeLayout {

	public MaxWidthRelativeLayout(Context context) {
		super(context);
		init(null);
	}

	public MaxWidthRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public MaxWidthRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private int mMaxWidth;

	private void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, new int[] { android.R.attr.maxWidth });
			mMaxWidth = a.getDimensionPixelSize(0, LayoutParams.MATCH_PARENT);
			a.recycle();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		if (mMaxWidth > 0 && mMaxWidth < measuredWidth) {
			int measureMode = MeasureSpec.getMode(widthMeasureSpec);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
