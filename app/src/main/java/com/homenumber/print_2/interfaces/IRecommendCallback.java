package com.homenumber.print_2.interfaces;

import com.homenumber.print_2.retrofit2.RetrofitItemVo;

/**
 * 협력사선택 이벤트
 */
public interface IRecommendCallback {
	void CallBackClick(RetrofitItemVo.RecommendVo vo);
}
