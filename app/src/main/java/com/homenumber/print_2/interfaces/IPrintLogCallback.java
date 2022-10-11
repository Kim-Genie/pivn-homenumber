package com.homenumber.print_2.interfaces;

import com.homenumber.print_2.retrofit2.RetrofitItemVo;

/**
 * AlertDialog List Callback
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public interface IPrintLogCallback {
	void CallBackClick(boolean isKeypad, RetrofitItemVo.RetrofitOrderNumberList itemvo);
}
