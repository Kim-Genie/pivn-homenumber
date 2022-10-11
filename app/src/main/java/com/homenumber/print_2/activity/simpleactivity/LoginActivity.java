package com.homenumber.print_2.activity.simpleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.activity.fragmentactivity.MainFragmentActivity;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityLoginBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginActivity
 * @author 나비이쁜이
 * @since 2019.08.23
 */
public class LoginActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivityLoginBinding mBinding;

     /**
     * 정규식
     */
//    private static final Pattern Pattern_Id_Valid = Pattern.compile(Constant.SELLER_ID_VALID);                        // 아이디
    private static final Pattern Pattern_Passwold_Valid = Pattern.compile(Constant.SELLER_PW_VALID);                  // 비밀번호
    private static final Pattern Pattern_Passwold_check_Valid = Pattern.compile(Constant.PASSWORD_CHECK_VALID2);      // 비밀번호 체크
    private static final Pattern Pattern_Phone_Valid  = Pattern.compile(Constant.PHONENUMBER_VALID2);  // 휴대폰번호 정규식

    /**
    * header string
    **/
    private String haederString = "";

    /**
     * onCreate
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
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
        isBackButtonNotice = true;

        // 아이디 체크 저장 여부
        if(StringUtils.equals(userData.getUserIDSave(), "save")) {
//            mBinding.checkboxId.setChecked(true);
            mBinding.etId.setText(userData.getUserID());
        }
    }

    /**
     * 아이디 & 비밀빈호 입력창 - 입력시 창에 대한 테투리 변화
     */
    public void afterTextChanged(Editable s) {
//        if(s.length() == 0)
//            mBinding.etId.setBackgroundResource(R.drawable.drawable_radius_edittext_off);
//        else
//            mBinding.etId.setBackgroundResource(R.drawable.drawable_radius_edittext_on);
    }

    /**
     * BindingApapter - EditText setFilters
     */
    @BindingAdapter("setPasswordFilter")
    public static void setFilter(AppCompatEditText view, InputFilter filter) {
        view.setFilters(new InputFilter[] {filter});
    }

    /**
     * 비밀번호 입력시 한글이 입력되지 않도록 하는 Filter
     */
    public InputFilter filterAlphaNum = (source, start, end, dest, dstart, dend) -> {
        if(!Pattern_Passwold_check_Valid.matcher(source).matches())
            return "";

        return null;
    };

    /**
     * onClick
     */
    public void onClick(int casenumber) {
        switch (casenumber) {
            case 1:
                Intent intent = new Intent(getApplicationContext(), TeamsActivity.class);
                startActivity(intent);
                break;

            case 2:
                Intent intent2 = new Intent(getApplicationContext(), UserPWSearchActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /**
     * 로그인 요청
     */
    public void requestLogin(View view) {

        if(CommonUtil.isNetworkConnected(mContext)) {
            // 아이디를 입력하지 않은 경우
            if (StringUtils.isEmpty(mBinding.etId.getText().toString())) {
                mBinding.etId.requestFocus();
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_input_id));
                return;
            }
            // 휴대폰번호 정규식
            if (!Pattern_Phone_Valid.matcher(mBinding.etId.getText().toString()).matches()) {
                mBinding.etId.requestFocus();
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_phonenumber));
                return;
            }

            // 비밀번호를 입력하지 않은 경우
            if (StringUtils.isEmpty(mBinding.etPassword.getText().toString())) {
                mBinding.etPassword.requestFocus();
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_input_pw));
                return;
            }

            // 아이디 정규식ㅌ
//            if(!Pattern_Id_Valid.matcher(mBinding.etId.getText().toString()).matches()) {
//                AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_valid_id), (dialog, which) -> AlertDialogUtil.dismissDialog());
//                return;
//            }

            // 비밀번호 정규식
            if (!Pattern_Passwold_Valid.matcher(mBinding.etPassword.getText().toString()).matches()) {
                mBinding.etPassword.requestFocus();
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_valid_password));
                return;
            }

            // ProgressBar 시작
            if (!LoginActivity.this.isFinishing()) {
                showProgress();
            }

            // @ 전송하기 위한 디코딩
            try {
                haederString = URLDecoder.decode(Constant.REQ_MAIN_HEADER, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e("Yourapp", "UnsupportedEncodingException");
            }



            // 서버에 전송할 데이터를 Map형태로 정리
            HashMap<String, Object> params = new HashMap<>();
            params.put(Constant.RK_USER_ID, mBinding.etId.getText().toString());
            params.put(Constant.RK_USER_PW, mBinding.etPassword.getText().toString());
            params.put(Constant.RK_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid);
            params.put(Constant.RK_USER_ID_PW, mBinding.etId.getText().toString() + Constant.RK_AT + mBinding.etPassword.getText().toString());

            Call<RetrofitItemVo> LoginCall = RetrofitUtil.createService(Constant.REQ_MAIN_HEADER,"", RetrofitService.class).requestLogin(params);



            // Call 객체를 통한 통신 시작
            LoginCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                if(itemVo.getRspCode() ==200) {
                                    // 토큰과 아이디를 저장
                                    userData.setUserToken(itemVo.getData().getAccessToken());
                                    userData.setUserID(mBinding.etId.getText().toString());
                                    userData.setUserAccid(itemVo.getData().getAcc_id());
                                    userData.setItemKg("1");
                                    userData.setItemNumber("1");
                                    userData.setGoodNo(itemVo.getData().getGoodNo());

                                        //홈넘버 집 - 회사 저장
                                        userData.setUserHN(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getHOME_NO()); // 홈넘버
                                        userData.setUserHomeAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_BASE_ADDRESS()); // 기본주소
                                        userData.setUserDetailAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_DTL_ADDRESS()); // 상세주소
                                        userData.setUserName(itemVo.getData().getUserNm()); // 이름
                                        userData.setUserTelephone1(itemVo.getData().getUserId()); // 유저 폰 번호
                                        userData.setUserAlias(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getALIAS()); // 집 / 회사
                                        userData.setUserNormalTelephone1(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_TEL()); // 유저 폰 번호
                                        userData.setUserZipCode1(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getZIP_CD()); // 유저 오편번호
                                        if (itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 1) {
                                            userData.setUserHN2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getHOME_NO()); // 유저 홈넘버
                                            userData.setUserCompanyAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_BASE_ADDRESS()); // 기본주소
                                            userData.setUserDetailAddress2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_DTL_ADDRESS()); // 상세주소
                                            userData.setUserName2(itemVo.getData().getUserNm()); // 이름
                                            userData.setUserTelephone2(itemVo.getData().getUserId()); // 유저 폰 번호
                                            userData.setUserAlias2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getALIAS()); // 집 / 회사
                                            userData.setUserNormalTelephone2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_TEL()); // 유저 폰 번호
                                            userData.setUserZipCode2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getZIP_CD()); // 유저 우편번호
                                        } else {
                                            userData.setUserCompanyAddress("");
                                            userData.setUserDetailAddress2("");
                                            userData.setUserAlias2("");
                                            userData.setUserHN2("");
                                            userData.setUserName2("");
                                            userData.setUserZipCode2("");
                                        }

                                    // 아이디 체크 여부 저장
//                                    if(mBinding.checkboxId.isChecked()) {
//                                        userData.setUserIDSave("save");
//                                    } else {
//                                        userData.setUserIDSave("not save");
//                                    }

                                    // 키패드 옵션 고정값 설정
                                    if(StringUtils.isEmpty(userData.getUserKeypadOption())) {
                                        userData.setUserKeypadOption(getString(R.string.str_sound));
                                    }

                                    // 유저 데이터 저장
                                    userDataManager.setUserData(userData);

                                    // 다른 작업 없이 메인 페이지로 이동합니다.
                                    redirectActivityAllClear(MainFragmentActivity.class);
                                } else {
                                    // 로그인 실패의 경우
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
                public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                    // Progress 종료
                    dismissProgress();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
                    finish();

                }
            });
        } else {
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
        }
    }


}
