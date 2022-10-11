package com.homenumber.print_2.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast를 편하게 사용하기 위한 Class
 * @author 나비이쁜이
 * @since 2019.03.21
 */
public class ToastUtil {

    /**
     * ToastUtil
	 ************************************************************************************************************************************************/
	private static Toast toast;

	/**
	 * Short Toast Message
	 ************************************************************************************************************************************************/
//	public static void showToastAsShort(Context context, String message) {
//		clearToast();
//		toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//		toast.show();
//	}

	/**
	 * Short Toast Message
	 ************************************************************************************************************************************************/
	public static void showToastAsShort(Context context, String message) {
		clearToast();
		toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL ,0,-700);
		toast.show();
	}
	
	/**
	 * Long Toast Message
	 ************************************************************************************************************************전화번호************************/
	public static void showToastAsLong(Context context, String message) {
		clearToast();
		toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL  ,0,-700);
		toast.show();
	}
	
	/**
	 * Toast 객체 초기화
	 ************************************************************************************************************************************************/
	private static void clearToast() {
		if(toast != null) {
			toast.cancel();
			toast = null;
		}
	}
}
