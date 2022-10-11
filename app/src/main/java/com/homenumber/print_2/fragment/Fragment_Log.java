package com.homenumber.print_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.activity.simpleactivity.HistoryDetailActivity;
import com.homenumber.print_2.activity.simpleactivity.LoginActivity;
import com.homenumber.print_2.adapter.recyclerview.PrintLogAdapter;
import com.homenumber.print_2.basesuperclass.BaseFragment;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.ClearDataUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.FragmentLogBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 출력내역 Fragment
 * @author 나비이쁜이
 * @since 2019.08.21
 */
public class Fragment_Log extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * Databinding
     */
    public FragmentLogBinding mBinding;

    private Boolean isCanRefresh = false;

    /**
     * Print Log DataList / Alert DataList
     ************************************************************************************************************************************************/

    private List<RetrofitItemVo.RetrofitOrderNumberList> dataList;
    private List<RetrofitItemVo.RetrofitOrderNumberList> alertdataList;

    /**
     * Print Log Adapter
     ************************************************************************************************************************************************/
    private PrintLogAdapter adapter;

    /**
     * Fragment uiInit
     */
    @Override
    protected View uiInit(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // set Databinding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_log, parent, false);
        mBinding.setFragmentLog(this);

        // Fragment 초기화 설정
        resetPageInfo();

        // Fragment 화면에 들어올때
        if(isAdded() && !isStartedRequest && activity.getCurrentFragment() == this) {
            startRequest();
            LogUtil.e("startRequest() 들어간다.");
        }

        LogUtil.e("init");

        return mBinding.getRoot();

    }

    /**
     * Fragment 초기화 설정
     */
    @Override
    public void resetPageInfo() {
        isStartedRequest = false;
        isMainScreen = true;

        LogUtil.e("resetPageInfo");
    }

    /**
     * Fragment 화면에 들어올때
     */

    @Override
    public void startRequest() {
        if(!isStartedRequest) {
            isCanRefresh = true;
            isStartedRequest = true;

            // 타이틀 작성
            mBinding.layoutHeader.txtFragmentHeader.setText(getString(R.string.str_print_log));

            // 초기화
            dataList = new ArrayList<>();
            alertdataList = new ArrayList<>();

//            //adapter
            adapter = new PrintLogAdapter(mContext, R.layout.item_log_content, dataList, (isCallback, item) -> {
                if(isCallback) {
                    // Fragment 1번 이동
                    mainFragmentActivity.mBinding.viewPagerMain.setCurrentItem(mainFragmentActivity.viewPagerAdapter.getPagePosition(Fragment_Keypad.class));
                    // 홈넘버 작성
                    StringBuffer origin = new StringBuffer(item.getReceiverHn());
                    origin.insert(3, "-");
                    origin.insert(8, "-");
                    mainFragmentActivity.fragment1.mBinding.txtHomenumber.setText(origin);



                } else {
                    LogUtil.e("수신사용자명 >"+item.getReceiverNm());
                    LogUtil.e("송신사용자명 > "+item.getSenderNm());
                    if(CommonUtil.isNetworkConnected(mContext)) {
                        // Alert 데이터를 보여주기 위한 요청
//                        requestLogAlert(item.getTarget_hn());
                        ArrayList<String> detailInfo = new ArrayList<String>();
                        detailInfo.add(0, item.orderInfoKeyproductNm);
//                        detailInfo.add(1, String.valueOf(item.orderInfoProductTotPrice));
                        detailInfo.add(2, item.paymentMethod);
                        detailInfo.add(3,item.orderInfoProductInfo);
                        Intent myinfo = new Intent(mContext, HistoryDetailActivity.class);
                        myinfo.putExtra("item", detailInfo);
                        startActivity(myinfo);
                    } else {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }

                }
            });


//            result_adapter = new AddressSearchResultAdapter(mContext, R.layout.item_address_search_content, adapterDataList, value -> {
//                // Fragment Textview에 작성
//                mBinding.txtZipcode.setText(value.getRoadnameZipCode());
//
//                mBinding.txtAddress1.setText(value.getFullRoadnameAddr());
//
//                LogUtil.e("우편"+value.getRoadnameZipCode());
//                LogUtil.e("주소"+value.getFullRoadnameAddr());
//
//                // 결과물 데이터 정의
//                joinItemVo = value;
//
//                // 조건문 정의
//                isAddress = true;
//
//                // 모든 팝업 종료
//                AlertDialogUtil.dismissDialog();
//
//                // AlertDialog 종료
//                dialog.dismiss();
//            });

//            adapter = new PrintLogAdapter(mContext, R.layout.item_log_content, dataList, (isKeypad, itemvo) -> {
//
//            });
//
            mBinding.logRecyclerview.setAdapter(adapter);

            // SwipeRefreshLayout의 회전마다 변화는 색상
            mBinding.swipeRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
            );
            mBinding.swipeRefreshLayout.setOnRefreshListener(this);

            // TODO 출력내역 요청
            if(CommonUtil.isNetworkConnected(mContext)) {
                // 출력내역 요청
                requestLog();
            } else {
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
            }
        }

        LogUtil.e("startRequest들어왔다.");
    }

    /**
     * 출력내역
     ************************************************************************************************************************************************/
    private void requestLog() {
        LogUtil.e("requestLog");
        // Progress 시작
        showProgress();

        Call<RetrofitItemVo> LogCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, mainFragmentActivity.token, mainFragmentActivity.device_uuid, RetrofitService.class).requestOrderList(activity.getUserData().getUserAccid());

        LogCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(Call<RetrofitItemVo> call, Response<RetrofitItemVo> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // 정상적으로 통신이 성곧된 경우
                    if(response.isSuccessful()) {
                        // 서버에서 정의한 데이터를 ItemVo로 가져옵니다.
                        RetrofitItemVo itemVo = response.body();

                        // 데이터 리스트 초기화
                        dataList = new ArrayList<>();

                        if(itemVo != null) {
                                if(itemVo.getRspCode() == 200) {
                                    if(itemVo.getData().getOrderList().size() != 0) {
                                        mBinding.logRecyclerview.setVisibility(View.VISIBLE);
                                        mBinding.txtNullData.setVisibility(View.GONE);

                                        dataList.addAll(itemVo.getData().getOrderList());

                                        adapter.setDataList(dataList);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        mBinding.logRecyclerview.setVisibility(View.GONE);
                                        mBinding.txtNullData.setVisibility(View.VISIBLE);
                                    }
                                } else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                    ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                    ClearDataUtil.ClearUserData(mContext);
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    startActivity(intent);
                                    activity.finish();
                                } else {
                                    // 정상적이지 않은 통신 실패의 경우
                                    ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                                }

                        }

                    } else {
                        // 정상적이지 않은 통신 실패의 경우
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }

                    dismissProgress();
                });

            }

            @Override
            public void onFailure(Call<RetrofitItemVo> call, Throwable t) {
                // 통신 자체가 문제되어 실패되는 경우
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress 종료
                dismissProgress();

                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });

    }
    /**
     * Swipe Refresh
     */
    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if(CommonUtil.isNetworkConnected(mContext)) {
                // TODO :: 리스트 재요청
                requestLog();
            }

            // SwipeRefresh중이라면 종료
            if(mBinding.swipeRefreshLayout.isRefreshing())
                mBinding.swipeRefreshLayout.setRefreshing(false);
        },100);
    }

    public void CheckRefresh(){
        if(isCanRefresh) {
            onRefresh();
        }else{
            startRequest();
        }
    }

}