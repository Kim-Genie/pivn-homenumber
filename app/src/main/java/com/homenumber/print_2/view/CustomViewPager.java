package com.homenumber.print_2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import static android.view.MotionEvent.ACTION_MASK;

/**
 * ViewPager의 슬라이딩을 얼리고 녹일수 있는 CustomViewPager
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class CustomViewPager extends ViewPager {

    /**
     * variable
     **************************************************************************************************************************************/
    private boolean melted;
    private static final int OFF_SET = 10;
    private float preX;

    /**
     * 생성자
     **************************************************************************************************************************************/
    public CustomViewPager(Context context) {
        super(context);
        freeze();
    }

    /**
     * 생성자
     **************************************************************************************************************************************/
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        freeze();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (melted)
                return super.onTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        performClick();
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(melted) {
            final int action = event.getAction() & ACTION_MASK;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    preX = event.getX();
                    break;

                case MotionEvent.ACTION_MOVE:
                    float x = event.getX();
                    return !(x - OFF_SET <= preX && preX <= x + OFF_SET);

            }

            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    /**
     * ViewPager를 얼립니다.
     **************************************************************************************************************************************/
    public void freeze() {
        melted = false;
    }

    /**
     * ViewPager를 녹입니다.
     **************************************************************************************************************************************/
    public void melt() {
        melted = true;
    }

}