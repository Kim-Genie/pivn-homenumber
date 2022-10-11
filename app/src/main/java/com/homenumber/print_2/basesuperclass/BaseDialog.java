package com.homenumber.print_2.basesuperclass;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

/**
 * 하위 Dialog에서 확장하여 사용하는 Super Class.
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class BaseDialog extends Dialog {

    /**
     * 생성자
	 ************************************************************************************************************************************************/
	public BaseDialog(Context context, int style) {
		super(context, style);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		// 뒤로가기 버튼 막기
		setCancelable(false);
	}

    /**
     * Back Key 동작.
	 ************************************************************************************************************************************************/
	@Override
	public void onBackPressed() {
        // 화면에서 뒤로가기 버튼이 적용되지 않게 하기 위한 no super & boolean
        boolean isBackKeyEnabled = false;
        if(isBackKeyEnabled) {
			dismiss();
		}
	}
}
