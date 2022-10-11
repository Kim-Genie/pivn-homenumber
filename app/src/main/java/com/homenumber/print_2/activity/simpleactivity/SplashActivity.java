package com.homenumber.print_2.activity.simpleactivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.homenumber.print_2.Constant;
import com.homenumber.print_2.activity.fragmentactivity.MainFragmentActivity;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.permission.PermissionUtil;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.RequestCodeUtil;
import com.homenumber.print_2.util.RootingBooleanUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivitySplashBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SplashActivity
 * @author 나비이쁜이
 * @since 2019.08.23
 */
public class SplashActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivitySplashBinding mBinding;

    /**
     * onCreate
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mBinding.setActivity(this);  // data - variable가 없으므로 setLifecycleOwner

        uiInit();
    }

    /**
     * uiInit
     */
    @Override
    protected void uiInit() {
        super.uiInit();

        // 권한 등록이 완료된 경우
        boolean isPermission = PermissionUtil.checkAndRequestPermission(this, RequestCodeUtil.PermissionCode.REQ_PERMISSION_ALL, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE);
        if(isPermission) {
            // 이미지 로드가 완료된 경우
            mBinding.ivSplashHomenumber.postDelayed(() -> {
                if(CommonUtil.isNetworkConnected(getApplicationContext())) {
                    // 구글플레이 서비스가 등록되지 않은 경우
                    if(!isGooglePlayService()) {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_google_play_service));
                        finish();
                        return;
                    }

                    // 디바이스 UUID가 존재하지 않는 경우
                    if(StringUtils.isEmpty(device_uuid)) {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_uuid));
                        finish();
                        return;
                    }

                    // 루팅이 되어 있는 경우
                    if(RootingBooleanUtil.isRooting()) {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_rooting));
                        finish();
                        return;
                    }

                    // 유저 토큰이 없는 경우
                    if(StringUtils.isEmpty(userData.getUserToken()))
                        // 로그인 페이지로 이동합니다.
                        openActivity(LoginActivity.class);
                    else
                        requestAutoLogin();
//                        openActivity(LoginActivity.class);
                } else {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    finish();
                }
            },2000);
        }
    }

    /**
     * Permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(PermissionUtil.verifyPermissions(grantResults)) {
            if(requestCode == RequestCodeUtil.PermissionCode.REQ_PERMISSION_ALL) {
                mBinding.ivSplashHomenumber.postDelayed(() -> {
                    // 구글플레이 서비스가 등록되지 않은 경우
                    if(!isGooglePlayService()) {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_google_play_service));
                        finish();
                        return;
                    }

                    // 디바이스 UUID가 존재하지 않는 경우
                    if(StringUtils.isEmpty(device_uuid)) {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_uuid));
                        finish();
                        return;
                    }

                    // 루팅이 되어 있는 경우
                    if(RootingBooleanUtil.isRooting()) {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_rooting));
                        finish();
                        return;
                    }

                    // 유저 토큰이 없는 경우
                    if(StringUtils.isEmpty(userData.getUserToken()))
                        // 로그인 페이지로 이동합니다.
                        openActivity(LoginActivity.class);
//                    else
//                        requestAutoLogin();
                },2000);
            }
        } else {
            // 권한 설정에 대하여 하나라도 거부하는 경우
            new AlertDialog.Builder(mContext)
                    .setTitle(getString(R.string.str_notice))
                    .setMessage(getString(R.string.msg_permission_negative))
                    .setPositiveButton(getString(R.string.str_confirm), (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + mContext.getPackageName()));
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                    })
                    .setNegativeButton(getString(R.string.str_cancle), (dialog, which) -> SplashActivity.this.finish())
                    .setCancelable(false)
                    .create()
                    .show();

        }
    }

    /**
     * 구글 플레이 서비스 체크 여부
     */
    private boolean isGooglePlayService() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();

        if(googleAPI == null) {
            return false;
        }

        // 현재 구글 플레이 상태
        int status = googleAPI.isGooglePlayServicesAvailable(mContext);

        switch (status) {
            case ConnectionResult.SUCCESS:
                // Google Play Service 가 정상적으로 설치 된 상태
                return true;

            case ConnectionResult.SERVICE_MISSING:
                // Google Play Service 가 없는 상태

                // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET는 21버전부터 FLAG_ACTIVITY_NEW_DOCUMENT로 대체
                Intent intent_missing = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_PACKAGE));
                intent_missing.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                intent_missing.setPackage("com.homenumber.parcel_print");
                startActivity(intent_missing);
                return false;

            case ConnectionResult.SERVICE_UPDATING:
                // Google Play Service 가 업데이트 중

                // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET는 21버전부터 FLAG_ACTIVITY_NEW_DOCUMENT로 대체
                Intent intent_update = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_PACKAGE));
                intent_update.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                intent_update.setPackage("com.homenumber.parcel_print");
                startActivity(intent_update);
                return false;

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                // Google Play Service 버전이 오래되어 업데이트가 필요한 상태

                // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET는 21버전부터 FLAG_ACTIVITY_NEW_DOCUMENT로 대체
                Intent intent_update_require = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_PACKAGE));
                intent_update_require.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                intent_update_require.setPackage("com.homenumber.parcel_print");
                startActivity(intent_update_require);
                return false;

            case ConnectionResult.SERVICE_DISABLED:
                // Google Play Service 가 사용중지 된 상태

                googleAPI.getErrorDialog(this, status, RequestCodeUtil.GooglePlayServiceCheckReqCode.REQ_GOOGLE_NOT_SUCCES, dialog -> finish());
                return false;

            case ConnectionResult.SERVICE_INVALID:
                // Google Play Service 가 인증되지 않은 상태

                googleAPI.getErrorDialog(this, status, RequestCodeUtil.GooglePlayServiceCheckReqCode.REQ_GOOGLE_NOT_SUCCES, dialog -> finish());
                return false;

            default:
                return false;
        }
    }

    /**
     * 자동로그인
     */


    private void requestAutoLogin() {
        // ProgressBar 시작
        if(! SplashActivity.this.isFinishing()) {
            showProgress();
        }
        // 서버에 전송할 데이터를 Map형태로 정리
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.RK_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid);
        params.put(Constant.RK_ACCESS_TOKEN, userData.getUserToken());
        params.put(Constant.RK_ACC_ID, userData.getUserAccid());

        // Call 객체 생성
        Call<RetrofitItemVo> AutoLoginCall = RetrofitUtil.createService(Constant.REQ_MAIN_HEADER,"", RetrofitService.class).requestAutoLogin(params);

        // Call 객체를 통한 통신 시작
        AutoLoginCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Progress 종료
                    dismissProgress();

                    // 정상적으로 통신이 성곧된 경우
                    if(response.isSuccessful()) {
                        // 서버에서 정의한 데이터를 ItemVo로 가져옵니다.
                        RetrofitItemVo retrofitItemVo = response.body();

                        if(retrofitItemVo != null)
                            if(retrofitItemVo.getRspCode() ==200) {
                                // 토큰을 저장합니다.
                                userData.setUserToken(retrofitItemVo.getData().getAccessToken());
                                userData.setUserAccid(retrofitItemVo.getData().getAcc_id());
                                /**
                                 * 유저정보 추가
                                 */
                                //홈넘버 집 - 회사 저장
                                ////////////////////////////////////////////////////////////////////////////
                                userData.setUserHN(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getHOME_NO()); // 홈넘버
                                userData.setUserHomeAddress(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_BASE_ADDRESS()); // 기본주소
                                userData.setUserDetailAddress(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_DTL_ADDRESS()); // 상세주소
                                userData.setUserName(retrofitItemVo.getData().getUserNm()); // 이름
                                userData.setUserTelephone1(retrofitItemVo.getData().getUserId()); // 유저 폰 번호
                                userData.setUserAlias(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getALIAS()); // 집 / 회사
                                userData.setUserNormalTelephone1(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_TEL()); // 유저 폰 번호
                                userData.setUserZipCode1(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getZIP_CD()); // 유저 오편번호

                                if (retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 1) {
                                    userData.setUserHN2(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getHOME_NO()); // 유저 홈넘버
                                    userData.setUserCompanyAddress(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_BASE_ADDRESS()); // 기본주소
                                    userData.setUserDetailAddress2(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_DTL_ADDRESS()); // 상세주소
                                    userData.setUserName2(retrofitItemVo.getData().getUserNm()); // 이름
                                    userData.setUserTelephone2(retrofitItemVo.getData().getUserId()); // 유저 폰 번호
                                    userData.setUserAlias2(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getALIAS()); // 집 / 회사
                                    userData.setUserNormalTelephone2(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_TEL()); // 유저 폰 번호
                                    userData.setUserZipCode2(retrofitItemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getZIP_CD()); // 유저 우편번호

                                } else {
                                    userData.setUserCompanyAddress("");
                                    userData.setUserDetailAddress2("");
                                    userData.setUserAlias2("");
                                    userData.setUserHN2("");
                                    userData.setUserName2("");
                                    userData.setUserZipCode2("");
                                }
                                ////////////////////////////////////////////////////////////////////////////
                                userDataManager.setUserData(userData);

                                // 다른 작업 없이 메인 페이지로 이동합니다.
                                redirectActivityAllClear(MainFragmentActivity.class);
                            } else {
                                // 에러 메세지를 토스트로 보여줍니다.
                                ToastUtil.showToastAsLong(mContext, retrofitItemVo.getAlert_msg());

                                // 토큰정보를 삭제합니다.
                                userData.setUserToken(null);
                                userData.setUserAccid(null);
                                userDataManager.setUserData(userData);

                                // 로그인 페이지로 이동합니다.
                                openActivity(LoginActivity.class);
                            }
                    } else {
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                        finish();
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
                finish();
            }
        });
    }



}
