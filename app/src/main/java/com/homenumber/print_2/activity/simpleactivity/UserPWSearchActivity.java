package com.homenumber.print_2.activity.simpleactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.alertdialog.AlertDialogUtil;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityPwSearchBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * UserPWSearchActivity
 * @author 나비이쁜이
 * @since 2019.08.23
 */
public class UserPWSearchActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivityPwSearchBinding mBinding;

    private static final Pattern Pattern_Phonenumber_Valid = Pattern.compile(Constant.PHONENUMBER_VALID);
    private static final Pattern Pattern_Passwold_Valid = Pattern.compile(Constant.SELLER_PW_VALID);

    private boolean isPhonenumber = false;
    private boolean isAlertPhonenumber = false;
    private Long timestamp;
    private String checkPhonenumber;
    private Long validationTimeStamp = 0L;
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pw_search);
        mBinding.setActivity(this);

        // 뒤로가기 여부
        isBackButtonNotice = false;
    }

    /**
     * onClick_back
     */
    public void workFinish() {
        finish();
    }

    /**
     * onClick
     */
    public void requestIdAccept() {
        if(StringUtils.isEmpty(mBinding.etId.getText().toString())) {
            mBinding.etId.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_input_id));
            return;
        }

        // 휴대폰 번호가 정규식에 맞는 경우인지
        if(!Pattern_Phonenumber_Valid.matcher(PhoneNumberUtils.formatNumber(mBinding.etId.getText().toString(), "KR")).matches()) {
            mBinding.etId.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_phonenumber));
            return;
        }

        // SMS 인증 Alert창 여부를 초기화
        isAlertPhonenumber = false;

        // SMS 인증 여부 초기화
        if(isPhonenumber) {
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_init_phonenumber));
            isPhonenumber = false;
        }

        // sms 인증 요청
        requestSMS(mBinding.etId.getText().toString());
    }

    /**
     * onClick_pw_change
     */
    public void workPWChange() {
        // 비밀번호가 없으면
        if(StringUtils.isEmpty(mBinding.etPw.getText().toString()) || StringUtils.isEmpty(mBinding.etPwCheck.getText().toString())) {
            mBinding.etPw.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pw_null_check));
            return;
        }

        // 변경할 비밀번호 정규식
        if(!Pattern_Passwold_Valid.matcher(mBinding.etPw.getText().toString()).matches()) {
            mBinding.etPw.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_valid_password2));
            return;
        }

        // 변경할 비빌번호 같은지 체크
        if(!StringUtils.equals(mBinding.etPw.getText().toString(), mBinding.etPwCheck.getText().toString())) {
            mBinding.etPwCheck.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pw_equal_check));
            return;
        }

        requestPWUpdate();
    }

    /**
     * [Retrofit2] SMS 인증 요청
     */
    private void requestSMS(@NonNull String mobile_no) {
        // Progress 시작
        if(! UserPWSearchActivity.this.isFinishing()) {
            showProgress();
        }
        HashMap<String, String> params = new HashMap<>();

        params.put(Constant.RK_HOMENUMBER,mBinding.etHn.getText().toString());
        params.put(Constant.RK_MOBILE_NUMBER, mBinding.etId.getText().toString());
        // Call 객체 생성
        Call<RetrofitItemVo> smsRequsetCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestChangePwSMS(params);
        checkPhonenumber = PhoneNumberUtils.formatNumber(mobile_no, "KR");
        // Call 객체를 통한 통신 시작
        smsRequsetCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                // 팝업창이 열려있지 않으면 여부를 설정하고 Alert을 생성
                                // 팝업창이 열려있으면 다른 작업은 없음
                                if(!isAlertPhonenumber) {
                                    // 휴대폰 인증 팝업창 여부
                                    isAlertPhonenumber = true;

                                    // 인증 확인 시간
                                    timestamp = null;

                                    // SMS 인증 Alert창으로 이동
                                    workSMSAlert();
                                }
                            } else {

                                // 정상적이지 않은 경우
                                ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
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
    }

    /**
     * [AlertDialog] SMS 인증
     */
    private void workSMSAlert() {
        AlertDialogUtil.showPhonenumberAcceptDialog(mContext, value -> {
            if(CommonUtil.isNetworkConnected(mContext)) {
                // 인증 번호를 입력하지 않은 경우
                if(StringUtils.isEmpty(value)) {
                    ToastUtil.showToastAsLong(mContext, mContext.getString(R.string.msg_request_phonenumber_accept_number));
                    return;
                }

                // 인증번호가 6자리가 아닌 경우
                if(value.length() != 6) {
                    ToastUtil.showToastAsLong(mContext, mContext.getString(R.string.msg_request_phonenumber_accept_number_6));
                    return;
                }

                // SMS Check
                requestSMSCheck(value);
            } else {
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
            }
        }, v -> {
            if(CommonUtil.isNetworkConnected(mContext)) {
                // 문자 재요청시에 팝업이 열려있음의 여부
                isAlertPhonenumber = true;

                // 팝업창이 띄워진 상태로 통신을 재요청
                if(mBinding.etId.getText() != null)
                    requestSMS(mBinding.etId.getText().toString());
            } else {
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
            }
        });
    }

    /**
     * [Retrofit2] SMS 인증 체크
     */
    private void requestSMSCheck2() {
        // ProgressBar 시작
        if(! UserPWSearchActivity.this.isFinishing()) {
            showProgress();
        }

        HashMap<String, String> params = new HashMap<>();

        params.put(Constant.RK_HOMENUMBER,mBinding.etHn.getText().toString());
        params.put(Constant.RK_MOBILE_NUMBER, mBinding.etId.getText().toString());
//        params.put(Constant.RK_MOBILE_NUMBER, PhoneNumberUtils.formatNumber(mBinding.etId.getText().toString(), "KR"));

        // Call 객체 생성
        Call<RetrofitItemVo> smsCheckCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestChangePwSMS(params);

        // Call 객체를 통한 통신 시작
        smsCheckCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                // 사용자에게 성공했음을 알립니다.
                                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_success_phonenumber));

                                // 휴대폰 인증 여부 설정
                                isPhonenumber = true;

                                // 휴대폰 인증 팝업창 여부
                                isAlertPhonenumber = false;

                                // 인증 확인 시간
//                                timestamp = itemVo.getValidation_timestamp();

                                // sms 버튼 변경
                                mBinding.layoutFirst.setVisibility(View.GONE);
                                mBinding.layoutSecond.setVisibility(View.VISIBLE);

                                // 다이얼로그 종려
                                AlertDialogUtil.dismissDialog();
                            } else {
                                // 휴대폰 인증 여부 설정
                                isPhonenumber = false;

                                // 휴대폰 인증 팝업창 여부
                                isAlertPhonenumber = false;

                                // 다이얼로그 종려
                                AlertDialogUtil.dismissDialog();

                                // 정상적이지 않은 경우
                                ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
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
    }

    /**
     * [Retrofit2] 비밀번호 업데이트
     */
    private void requestPWUpdate() {
        // ProgressBar 시작
        if(! UserPWSearchActivity.this.isFinishing()) {
            showProgress();
        }

        HashMap<String, Object > params = new HashMap<>();

        params.put(Constant.RK_HOMENUMBER,mBinding.etHn.getText().toString());
        params.put(Constant.RK_MOBILE_NUMBER, mBinding.etId.getText().toString());
        params.put(Constant.RK_PWD, mBinding.etPwCheck.getText().toString());
        params.put(Constant.RK_VALIDATION_TIMESTAMP,  validationTimeStamp);



        Call<RetrofitItemVo> changeCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestChangePW(params);


        changeCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(Call<RetrofitItemVo> call, Response<RetrofitItemVo> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Progress 종료
                    dismissProgress();

                    // 정상적으로 통신이 성곧된 경우
                    if(response.isSuccessful()) {
                        // 서버에서 정의한 데이터를 ItemVo로 가져옵니다.
                        RetrofitItemVo itemVo = response.body();

                        if(itemVo != null) {
                            if(itemVo.getSuccess()) {
                                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_change_password));
                                finish();
                            } else {
                                ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                            }
                        }
                    } else {
                        // 정상적이지 않은 통신 실패의 경우
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }

                });

            }

            @Override
            public void onFailure(Call<RetrofitItemVo> call, Throwable t) {
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress 종료
                dismissProgress();
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
                finish();
            }
        });

    }

    /**
     * [Retrofit2] SMS 인증 체크
     */
    private void requestSMSCheck(@NonNull String validation_code) {
        // ProgressBar 시작
        if(! UserPWSearchActivity.this.isFinishing()) {
            showProgress();
        }

        // Call 객체 생성
        Call<RetrofitItemVo> smsCheckCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoinSMSCheck(mBinding.etId.getText().toString(),validation_code);

        // Call 객체를 통한 통신 시작
        smsCheckCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                // 사용자에게 성공했음을 알립니다.
                                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_success_phonenumber));

                                // 휴대폰 인증 여부 설정
                                isPhonenumber = true;
                                validationTimeStamp = itemVo.getData().getValidation_timestamp() == null ? 0L : itemVo.getData().getValidation_timestamp();
                                LogUtil.e("휴대폰 인증여부 check : "+isPhonenumber);
                                // 휴대폰 인증 팝업창 여부
                                isAlertPhonenumber = false;
                                // 인증 확인 시간
//                                timestamp = itemVo.getValidation_timestamp();

                                // sms 버튼 변경
                                mBinding.layoutFirst.setVisibility(View.GONE);
                                mBinding.layoutSecond.setVisibility(View.VISIBLE);

                                // 다이얼로그 종려
                                AlertDialogUtil.dismissDialog();
                            } else {
                                // 휴대폰 인증 여부 설정
                                isPhonenumber = false;
                                validationTimeStamp = 0L;
                                // 휴대폰 인증 팝업창 여부
                                isAlertPhonenumber = false;

                                // 다이얼로그 종려
                                AlertDialogUtil.dismissDialog();

                                // 정상적이지 않은 경우
                                ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());


                            }
                        }else{
                            // 에러 메세지를 토스트로 보여줍니다.
                            ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                        }
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
    }
}
