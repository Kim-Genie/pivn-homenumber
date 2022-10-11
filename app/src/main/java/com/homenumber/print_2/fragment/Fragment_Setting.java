package com.homenumber.print_2.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.activity.simpleactivity.LoginActivity;
import com.homenumber.print_2.activity.simpleactivity.MyHomeNumberActivity;
import com.homenumber.print_2.activity.simpleactivity.MyInfoActivity;
import com.homenumber.print_2.activity.simpleactivity.UserRecommendActivity;
import com.homenumber.print_2.alertdialog.AlertDialogUtil;
import com.homenumber.print_2.basesuperclass.BaseFragment;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.ClearDataUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.FragmentSettingBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 더보기 Fragment
 * @author 나비이쁜이
 * @since 2019.08.21
 */
public class Fragment_Setting extends BaseFragment {

    /**
     * Databinding
     */
    public FragmentSettingBinding mBinding;

    /**
     * Fragment uiInit
     */
    @Override
    protected View uiInit(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // set Databinding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, parent, false);
        mBinding.setFragmentSetting(this);

        // Fragment 초기화 설정
        resetPageInfo();

        // Fragment 화면에 들어올때
        if(isAdded() && !isStartedRequest && activity.getCurrentFragment() == this) {
            startRequest();
        }

        return mBinding.getRoot();
    }

    /**
     * Fragment 초기화 설정
     */
    @Override
    public void resetPageInfo() {
        isStartedRequest = false;
        isMainScreen = true;
    }

    /**
     * Fragment 화면에 들어올때
     */
    @SuppressLint("SetTextI18n") @Override
    public void startRequest() {
        if(!isStartedRequest) {
            isStartedRequest = true;

            // 타이틀 작성
            mBinding.layoutHeader.txtFragmentHeader.setText(getString(R.string.str_setting));

            // 사용자 아이디
            mBinding.txtUserId.setText(activity.getUserData().getUserID());

            // 키패드 설정
            mBinding.txtKeypadSetting.setText(activity.getUserData().getUserKeypadOption());

            // 앱 버전 정보
            mBinding.txtAppVersion.setText((Constant.REQ_MAIN_HEADER.equals("https://hnc2crest.homenumber.co.kr/") ?  "App Version " + CommonUtil.getCurrentVersion(mContext) :"TestServer"));

            // 고객센터 휴대폰번호
            mBinding.txtSupportNumber.setText(getString(R.string.str_support_center_number));
            mBinding.layoutSupportNumber.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mBinding.txtSupportNumber.getText().toString()))));
        }
    }

    /**
     * 로그아웃
     */
    public void requestLogout() {
        if(CommonUtil.isNetworkConnected(mContext)) {
            AlertDialogUtil.showLogoutChoiceDialog(mContext, (dialog, which) -> {
                // Progress 시작
                showProgress();

                // 서버에 전송할 데이터를 Map형태로 정리
                HashMap<String, String> params = new HashMap<>();
                params.put(Constant.RK_ACCESS_TOKEN, mainFragmentActivity.token);
                params.put(Constant.RK_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + mainFragmentActivity.device_uuid);
                params.put(Constant.RK_ACC_ID, mainFragmentActivity.acc_id);


                // Call 객체 생성
                Call<RetrofitItemVo> LogoutCall = RetrofitUtil.createService(Constant.REQ_MAIN_HEADER,"", RetrofitService.class).requestLogout(params);

                // Call 객체를 통한 통신 시작
                LogoutCall.enqueue(new Callback<RetrofitItemVo>() {
                    @Override
                    public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            // Progress 종료
                            dismissProgress();

                            // 정상적으로 통신이 성곧된 경우
                            if(response.isSuccessful()) {
                                // 서버에서 정의한 데이터를 ItemVo로 가져옵니다.
                                RetrofitItemVo itemVo = response.body();

                                if(itemVo != null) {
                                        if(itemVo.getRspCode() == 200) {
                                            ToastUtil.showToastAsLong(mContext, getString(R.string.msg_logout));
                                            ClearDataUtil.ClearUserData(mContext);
                                            Intent intent = new Intent(mContext, LoginActivity.class);
                                            startActivity(intent);
                                            activity.finish();
                                        } else {
                                            ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                            ClearDataUtil.ClearUserData(mContext);
                                            Intent intent = new Intent(mContext, LoginActivity.class);
                                            startActivity(intent);
                                            activity.finish();
                                        }
                                }
                            } else {
                                // 정상적이지 않은 통신 실패의 경우
                                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                        // 통신 자체가 문제되어 실패되는 경우
                        LogUtil.e("onFailure: " + t.getMessage());

                        // Progress 종료
                        dismissProgress();

                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));

                    }
                });
            }, (dialog, which) -> AlertDialogUtil.dismissDialog());
        } else {
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
        }
    }

    /**
     * 내정보 세팅
     */
    public void onClickMyInfo() {
        Intent myinfo = new Intent(mContext, MyInfoActivity.class);
        startActivity(myinfo);
    }

    /**
     * 사용자 추천
     */
    public void onClickRecommend() {
        Intent myinfo = new Intent(mContext, UserRecommendActivity.class);
        startActivity(myinfo);
    }

    /**
     * 홈넘버 조회
     */
    public void onClickMyHomeNumber() {
        Intent myinfo = new Intent(mContext, MyHomeNumberActivity.class);
        startActivity(myinfo);
    }
    /**
     * 키패드 세팅
     */
    public void onClickKeypadSetting() {
        int checkitem = -1;
        if(StringUtils.equals(activity.getUserData().getUserKeypadOption(), getString(R.string.str_sound))) {
            checkitem = 0;
        } else if(StringUtils.equals(activity.getUserData().getUserKeypadOption(), getString(R.string.str_vibrator))) {
            checkitem = 1;
        } else if(StringUtils.equals(activity.getUserData().getUserKeypadOption(), getString(R.string.str_no_effect))) {
            checkitem = 2;
        }

        AlertDialogUtil.showKeypadChoiceDialog(mContext, checkitem, value -> {
            if(StringUtils.isEmpty(value)) {
                return;
            }

            mBinding.txtKeypadSetting.setText(value);

            // userData 설정
            activity.getUserData().setUserKeypadOption(value);
            activity.getUserDataManager().setUserData(activity.getUserData());

            // Fragment 1번 이동
            mainFragmentActivity.mBinding.viewPagerMain.setCurrentItem(mainFragmentActivity.viewPagerAdapter.getPagePosition(Fragment_Keypad.class));

            // 초기화
            restartFragment();
        });
    }


}