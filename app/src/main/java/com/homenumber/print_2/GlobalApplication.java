package com.homenumber.print_2;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.homenumber.print_2.basesuperclass.GlideApp;


/**
 * Activity에서 공통적으로 적용되는 상위 MultiDexApplication
 * @author 나비이쁜이
 * @since 2019.08.13
 */
public class GlobalApplication extends MultiDexApplication {

    /**
     * Context
     */
    public Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    /**
     * Application onTrimMemory()
     * 시스템 리소스가 부족할 때 어플리케이션이 추가로 메모리를 해제하는 기회를 줍니다
     * 모든 백그라운드 프로세스가 종료되었는데도 메모리가 부족하면 호출됩니다
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        // Glide 메모리 정리
        GlideApp.get(this).trimMemory(level);
    }

    /**
     * Application onLowMemory()
     * 시스템 리소스가 부족할 때 어플리케이션이 추가로 메모리를 해제하는 기회를 줍니다
     * 모든 백그라운드 프로세스가 종료되었는데도 메모리가 부족하면 호출됩니다
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // Glide 메모리 정리
        GlideApp.get(this).clearMemory();
    }
}
