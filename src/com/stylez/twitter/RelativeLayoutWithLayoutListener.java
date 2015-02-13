package com.stylez.twitter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RelativeLayoutWithLayoutListener extends RelativeLayout {

	public interface LayoutListener {
	    public void onLayout();
	}

	private com.stylez.twitter.LayoutListener mListener;

	public RelativeLayoutWithLayoutListener(Context context) {
	    super(context);
	}

	public RelativeLayoutWithLayoutListener(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	}

	public RelativeLayoutWithLayoutListener(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}

	public void setLayoutListener(com.stylez.twitter.LayoutListener layoutListener) {
	    mListener = layoutListener;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    super.onLayout(changed, l, t, r, b);
	    if (mListener != null) {
	        mListener.onLayout();
	    }
	}

}