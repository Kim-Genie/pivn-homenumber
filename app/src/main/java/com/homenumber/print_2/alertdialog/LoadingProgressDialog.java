package com.homenumber.print_2.alertdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.homenumber.print_2.basesuperclass.BaseDialog;
import com.homenumber.print_2_2.R;

import java.util.Objects;

/**
 * ProgressDialog을 보여주는 Loading 화면
 * @author 나비이쁜이
 * @since 2018.03.30
 */
public class LoadingProgressDialog extends BaseDialog {

	/**
	 * 생성자
	 ************************************************************************************************************************************************/
	public LoadingProgressDialog(Context context) {
		super(context, android.R.style.Theme_Holo_Light_Dialog);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_loading_progress);
		Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}
}
