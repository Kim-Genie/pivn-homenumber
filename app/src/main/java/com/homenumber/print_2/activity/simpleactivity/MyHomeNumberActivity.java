package com.homenumber.print_2.activity.simpleactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.adapter.ItemDecoration.StickHeaderItemDecoration;
import com.homenumber.print_2.adapter.recyclerview.MyHomeNumberListAdapter;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2.vo.MyHomeNumberVo;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityMyhomenumberBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 추천하기화면
 */
@SuppressLint("Registered")
public class MyHomeNumberActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener {

    /**
     * Databinding
     */
    private ActivityMyhomenumberBinding mBinding;



    private MyHomeNumberListAdapter adapter;

    private List<MyHomeNumberVo> dataList;
    private MyHomeNumberVo item;

    private String url = "";

    private boolean singlenessShopmall = true;
    private StickHeaderItemDecoration stickHeaderItemDecoration;
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_myhomenumber);
        mBinding.setActivity(this);

        uiInit();
    }

    /**
     * uiInit
     */
    @Override
    protected void uiInit() {
        super.uiInit();

        // 뒤로가기 여부
        isBackButtonNotice = false;

        dataList = new ArrayList<>();
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new MyHomeNumberListAdapter(mContext, dataList);

//        stickHeaderItemDecoration = new StickHeaderItemDecoration(adapter);
//        mBinding.recommendRecyclerview.addItemDecoration(stickHeaderItemDecoration);
        mBinding.myhomenumberRecyclerview.setAdapter(adapter);

//        RecommendList();
        MyHomeNumberList();
    }

    /**
     * setAdapter
     */
    private void setDataList(List<MyHomeNumberVo> dataList) {
        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();
    }

    /**
     * onClick_back
     */
    public void workFinish() {
        finish();
    }

    /**
     * 추천인 목록 조회
     */
    private void MyHomeNumberList() {
        // Progress 시작
        if (!MyHomeNumberActivity.this.isFinishing()) {
            showProgress();
        }
        // Call 객체 생성
        Call<RetrofitItemVo> LogCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).myHomeNumbers(userData.getUserID());


        // Call 객체를 통한 통신 시작
        LogCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(Call<RetrofitItemVo> call, Response<RetrofitItemVo> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // 정상적으로 통신이 성곧된 경우
                    if (response.isSuccessful()) {
                        // 서버에서 정의한 데이터를 ItemVo로 가져옵니다.
                        RetrofitItemVo itemVo = response.body();
                        if (itemVo != null) {
                            if (itemVo.getRspCode() == 200) {
                                Log.d("local", "onResponse: "+ itemVo.getData().getHomenumberVoList());
                                item = new MyHomeNumberVo();
                                // 데이터 리스트 초기화
                                dataList = new ArrayList<>();
                                dataList.add(item);
                                if (itemVo.getData().getHomenumberVoList().size() != 0) {
                                    for (int i = 0; i < itemVo.getData().getHomenumberVoList().size(); i++) {
                                        dataList.add(new MyHomeNumberVo(1, itemVo.getData().getHomenumberVoList().get(i)));
                                        Log.d("KIM MINJAE ", "onResponse: "+ itemVo.getData().getHomenumberVoList().get(i));
                                    }

                                }
                                setDataList(dataList);
                            } else {
                                // 정상적이지 않은 통신 실패의 경우
                                ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                            }
                        }

                    } else {
                        // 정상적이지 않은 통신 실패의 경우
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }

                    // Progress 종료
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

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            // 주소록 리스트 요청
            if(CommonUtil.isNetworkConnected(mContext))
                MyHomeNumberList();
            else
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));

            // SwipeRefresh중이라면 종료
            if(mBinding.swipeRefreshLayout.isRefreshing())
                mBinding.swipeRefreshLayout.setRefreshing(false);
        },500);
    }
}

