package com.homenumber.print_2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    private boolean isNeedIntercept = true;


    public CustomScrollView (Context context) {
        super(context);
    }

    public CustomScrollView (Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    public CustomScrollView ( Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        if (isNeedIntercept) {
            return ret;
        } else {
            return  false;
        }
    }

    public boolean isNeedIntercept() {
        return isNeedIntercept;
    }

    public void setNeedIntercept(boolean needIntercept) {
        isNeedIntercept = needIntercept;
    }

}
