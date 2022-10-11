package com.homenumber.print_2.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;
import android.view.View;

/**
 * CommonUtil
 * @author 나비이쁜이
 * @since 2019.08.13
 */
public class CommonUtil {

    /**
     * (공통) 현재 네트워크가 연결되어 있는지 확인
     ************************************************************************************************************************************************/
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 5. int 값의 color를 16진수 hex값 형태로 반환 (ButtonSelectorListener)
     */
    public static String getTextHexColor(int colorValue) {
        return String.format("%06X", (0xFFFFFF & colorValue));
    }

    /**
     * 6. 터치한 지점이 해당 뷰의 유효한 지점인지 체크 후 리턴 (전체 화면 내에서의 비교 || ButtonSelectorListener)
     */
    public static boolean isAvailableTouch(View v, MotionEvent event) {
        Rect r = new Rect();
        v.getGlobalVisibleRect(r);

        return 0.0f <= event.getX() && event.getX() <= (r.right - r.left) && 0.0f <= event.getY() && event.getY() <= (r.bottom - r.top);
    }

    /**
     * 현재 디바이스의 App 버전을 확인
     ************************************************************************************************************************************************/
    public static String getCurrentVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(); return null;
        } catch (Exception e) {
            e.printStackTrace(); return null;
        }
    }
}
