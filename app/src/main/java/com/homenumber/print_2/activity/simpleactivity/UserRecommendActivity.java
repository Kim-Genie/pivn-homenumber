package com.homenumber.print_2.activity.simpleactivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.adapter.ItemDecoration.StickHeaderItemDecoration;
import com.homenumber.print_2.adapter.recyclerview.RecommnedListAdapter;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2.vo.RecommendVo;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityRecommendBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 추천하기화면
 */
@SuppressLint("Registered")
public class UserRecommendActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener {

    /**
     * Databinding
     */
    private ActivityRecommendBinding mBinding;

//    private StickHeaderItemDecoration stickHeaderItemDecoration;

    /**
     * Print Log Adapter
     */
    private RecommnedListAdapter adapter;

    private List<RecommendVo> dataList;
    private RecommendVo item;

    private String url = "";

    private boolean singlenessShopmall = true;
    private StickHeaderItemDecoration stickHeaderItemDecoration;
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommend);
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
//        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new RecommnedListAdapter(mContext, dataList);

//        stickHeaderItemDecoration = new StickHeaderItemDecoration(adapter);
//        mBinding.recommendRecyclerview.addItemDecoration(stickHeaderItemDecoration);
//        mBinding.recommendRecyclerview.setAdapter(adapter);

        RecommendList();
    }

    /**
     * setAdapter
     */
    private void setDataList(List<RecommendVo> dataList) {
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
     * 복사
     */
    public void copy() {
        if (StringUtils.isEmpty(url)) {
            RequestUrl(1);
        } else {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("URL", url);
            clipboard.setPrimaryClip(clip);
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_success_copyurl));

        }
    }
    /**
     * 공유
     */
    public void share() {
        if(StringUtils.isEmpty(url)){
            RequestUrl(2);
        }else{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, url);
            Intent chooser = Intent.createChooser(intent, "공유");
            startActivity(chooser);
        }
    }


    /**
     * 추천인 목록 조회
     */
    private void RecommendList() {
        // Progress 시작
        if (!UserRecommendActivity.this.isFinishing()) {
            showProgress();
        }
        // Call 객체 생성
        Call<RetrofitItemVo> LogCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).recommendPeople(userData.getUserID());


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
                                item = new RecommendVo();
                                // 데이터 리스트 초기화
                                dataList = new ArrayList<>();
                                dataList.add(item);
                                url = itemVo.getData().getRcmnUrl();
                                if (itemVo.getData().getRecommendVoList().size() != 0) {
                                    for (int i = 0; i < itemVo.getData().getRecommendVoList().size(); i++) {
                                        dataList.add(new RecommendVo(1,itemVo.getData().getRecommendVoList().get(i) ));

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

    /**
     * 단축Url 조회
     */
    private void RequestUrl(int type) {
        // Progress 시작
        if (!UserRecommendActivity.this.isFinishing()) {
            showProgress();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.RK_USER_ID, userData.getUserID());
        // Call 객체 생성
        Call<RetrofitItemVo> LogCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).requestUrl(params);


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
                                if(!StringUtils.isEmpty(itemVo.getData().getRcmnUrl())) {
                                    url =itemVo.getData().getRcmnUrl();
                                    if(type == 1){
                                        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("URL", url);
                                        clipboard.setPrimaryClip(clip);
                                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_success_copyurl));
                                    }else{
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                                        intent.putExtra(Intent.EXTRA_TEXT, url);
                                        Intent chooser = Intent.createChooser(intent, "공유");
                                        startActivity(chooser);
                                    }
                                } else{
                                    ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                                }
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
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//
//            // 주소록 리스트 요청
//            if(CommonUtil.isNetworkConnected(mContext))
//                RecommendList();
//            else
//                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
//
//            // SwipeRefresh중이라면 종료
//            if(mBinding.swipeRefreshLayout.isRefreshing())
//                mBinding.swipeRefreshLayout.setRefreshing(false);
//        },500);

    }
}

