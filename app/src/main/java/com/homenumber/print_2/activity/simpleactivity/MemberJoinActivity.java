package com.homenumber.print_2.activity.simpleactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.adapter.recyclerview.AddressSearchPageAdapter;
import com.homenumber.print_2.adapter.recyclerview.AddressSearchResultAdapter;
import com.homenumber.print_2.alertdialog.AlertDialogUtil;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityMemberjoinBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MemberJoinActivity
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class MemberJoinActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivityMemberjoinBinding mBinding;

    /**
     * 회원가입시 sms 인증요청
     */
    private String timestamp;
    private Long validationTimeStamp = 0L;
    private String checkPhonenumber;

    /**
     * 고객 가입 필수 데이터 확인 여부
     * 1. 휴대폰 인증 여부
     * 2. 주소 입력 여부
     * 3. 현재 휴대폰 인증중인지 여부
     */
    private boolean isPhonenumber = false;
    private boolean isAlertPhonenumber = false;
    private boolean isAddress = false;

    /**
     * 비밀번호 정규식 & 휴대폰 번호 정규식 & 비밀번호 체크
     */
    private static final Pattern Pattern_Password_Valid = Pattern.compile(Constant.PASSWORD_VALID);
    private static final Pattern Pattern_Phonenumber_Valid = Pattern.compile(Constant.PHONENUMBER_VALID);
    private static final Pattern Pattern_Password_check_Valid = Pattern.compile(Constant.PASSWORD_CHECK_VALID);

    /**
     * Alert RecyclerView & Result Item
     */
    private RecyclerView result_recyclerView;

    private AddressSearchResultAdapter result_adapter;
    private RetrofitItemVo.RetrofitAddressVo joinItemVo;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_memberjoin);

        mBinding.setActivity(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction("MemberJoinActivity");
        registerReceiver(mBroadcastReceiver, filter);

        uiInit();
    }

    /**
     * uiInit
     */
    @Override
    protected void uiInit() {
        super.uiInit();


        // 결과물 데이터
        joinItemVo = new RetrofitItemVo.RetrofitAddressVo();

        // 비밀번호 정규식 설정
        mBinding.etPassword.setFilters(new InputFilter[] {filterAlphaNum});
        mBinding.etPasswordCheck.setFilters(new InputFilter[] {filterAlphaNum});

        // 휴대폰번호를 입력하는 경우에 대한 변화 감지
        mBinding.etPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                // 휴대폰 번호에 변화가 생기면 false
                isPhonenumber = false;
                validationTimeStamp = 0L;
                // sms 버튼 변경
//                mBinding.btnSms.setText(getString(R.string.str_sms_confirm));
                mBinding.btnSms.setClickable(true);
            }
        });
    }

    /**
     * 비밀번호 정규식
     */
    private InputFilter filterAlphaNum = (source, start, end, dest, dstart, dend) -> {
        if(!Pattern_Password_check_Valid.matcher(source).matches()) {
            return "";
        }
        return null;
    };

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
                if(mBinding.etPhonenumber.getText() != null)
                    requestSMS(mBinding.etPhonenumber.getText().toString());
            } else {
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
            }
        });
    }

    /**
     * [Retrofit2] SMS 인증 요청
     */
    private void requestSMS(@NonNull String mobile_no) {
        // ProgressBar 시작
        if(! MemberJoinActivity.this.isFinishing()) {
            showProgress();
        }

        // Call 객체 생성
        Call<RetrofitItemVo> smsRequsetCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requesSMSRequset(PhoneNumberUtils.formatNumber(mobile_no, "KR"));

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
     * [Retrofit2] SMS 인증 체크
     */
    private void requestSMSCheck(@NonNull String validation_code) {
        // ProgressBar 시작
        if(! MemberJoinActivity.this.isFinishing()) {
            showProgress();
        }

        // Call 객체 생성
        Call<RetrofitItemVo> smsCheckCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoinSMSCheck(checkPhonenumber,validation_code);

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

                                    LogUtil.e("휴대폰 인증여부 check : "+isPhonenumber);

                                    // 휴대폰 인증 팝업창 여부
                                    isAlertPhonenumber = false;

                                    // 인증 확인 시간
                                    validationTimeStamp = itemVo.getData().getValidation_timestamp() == null ? 0L : itemVo.getData().getValidation_timestamp();
                                    // sms 버튼 변경
                                    mBinding.btnSms.setText("완료");
                                    mBinding.btnSms.setClickable(false);

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
     * [AlertDialog] 주소찾기
     */
    private void workSearchAddressAlert() {
        AlertDialogUtil.showAddressSearchDialog(mContext, (isCheck, value1, value2, value3, value4) -> {
            if(CommonUtil.isNetworkConnected(mContext)) {
                // 체크박스 여부에 대한 파악
                if(isCheck) {
                    // 도로명 주소 찾기 -> 1번 페이지에서 50개의 데이터를 요청합니다.
                    requestSearchAddressDoro(value1, value2, value3, value4);
                } else {
                    // 지번명 주소 찾기
                    requestSearchAddressJibun(value1, value2, value3, value4);
                }
            } else {
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
            }
        });
    }

    /**
     * [Retrofit2] 도로명 주소찾기
     */
    private void requestSearchAddressDoro(String sido, String sigungu, String roadname, String building_name) {
        // ProgressBar 시작
        if(! MemberJoinActivity.this.isFinishing()) {
            showProgress();
        }

        // Call 객체 생성
        Call<RetrofitItemVo> addressSearchCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoinDoro(sido, sigungu, roadname, building_name);

        // Call 객체를 통한 통신 시작
        addressSearchCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                    // 데이터 등록
                                    List<RetrofitItemVo.RetrofitAddressVo> dataList = new ArrayList<>(itemVo.getData().getAddress_list());

                                    // 데이터가 없는 경우 return
                                    if(dataList.size() == 0) {
                                        ToastUtil.showToastAsLong(mContext, getString(R.string.msg_search_null));
                                        return;
                                    }

                                    // 주소찾기 결과
                                    showAddressSearchResultDialog(dataList);
                                } else {
                                    // 주소 인증 여부
                                    isAddress = false;

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
     * [Retrofit2] 지번명 주소찾기
     */
    private void requestSearchAddressJibun(String sido, String sigungu, String roadname, String building_name) {
        // ProgressBar 시작
        if(! MemberJoinActivity.this.isFinishing()) {
            showProgress();
        }

        // Call 객체 생성
        Call<RetrofitItemVo> addressSearchCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoinJibun(sido, sigungu, roadname, building_name);

        // Call 객체를 통한 통신 시작
        addressSearchCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                    // 데이터 등록
                                    List<RetrofitItemVo.RetrofitAddressVo> dataList = new ArrayList<>(itemVo.getData().getAddress_list());

                                    // 데이터가 없는 경우 return
                                    if(dataList.size() == 0) {
                                        ToastUtil.showToastAsLong(mContext, getString(R.string.msg_search_null));
                                        return;
                                    }

                                    // 주소찾기 결과
                                    showAddressSearchResultDialog(dataList);
                                } else {
                                    // 주소 인증 여부
                                    isAddress = false;

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
     * 주소 검색 결과 팝업
     */
    private void showAddressSearchResultDialog(List<RetrofitItemVo.RetrofitAddressVo> dataList) {
        dismissDialog();

        // [전체 데이터 개수] & [페이지 수]
        int listSize = 0, pageNumber = 0;

        // Adapter에 초기화하여 보여줄 데이터 리스트
        List<RetrofitItemVo.RetrofitAddressVo> adapterDataList = new ArrayList<>();

        /**
         * 페이지 개수를 구한다. 나머지값이 존재하는 경우 페이지 개수를 +1
         */
        if(dataList.size() != 0) {
            // 데이터 개수
            listSize = dataList.size();

            // 몫
            pageNumber = (listSize / Constant.ARRAY_PAGE);

            // 나머지(존재여부)
            int pagePlus = (listSize % Constant.ARRAY_PAGE);

            // 나머지값이 존재하는 경우 몫(페이지수)을 +1
            if(pagePlus != 0)
                ++pageNumber;
        }

        /**
         * Adapter에 넣어줄 첫번째 데이터를 정의
         */
        if(listSize != 0) {
            if(listSize < Constant.ARRAY_PAGE) {
                for(int j = 0; j < listSize; j++)
                    adapterDataList.add(dataList.get(j));
            } else {
                for(int j = 0; j < Constant.ARRAY_PAGE; j++)
                    adapterDataList.add(dataList.get(j));
            }
        }
        /**
         * AlertDialog
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(new android.view.ContextThemeWrapper(mContext, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        View v = View.inflate(mContext, R.layout.layout_address_result, null);

        /**
         * RecyclerView
         */
        result_recyclerView = v.findViewById(R.id.result_recyclerview);

        RecyclerView page_recyclerView = v.findViewById(R.id.page_recyclerview);
        AppCompatImageView iv_back = v.findViewById(R.id.iv_back);

        /**
         * Result Adapter
         */
        result_adapter = new AddressSearchResultAdapter(mContext, R.layout.item_address_search_content, adapterDataList, value -> {
            // Fragment Textview에 작성
            mBinding.txtZipcode.setText(value.getRoadnameZipCode());

            mBinding.txtAddress1.setText(value.getFullRoadnameAddr());

            LogUtil.e("우편"+value.getRoadnameZipCode());
            LogUtil.e("주소"+value.getFullRoadnameAddr());

            // 결과물 데이터 정의
            joinItemVo = value;
            mBinding.txtAddrRef.setText(value.getAddressRef());
            // 조건문 정의
            isAddress = true;

            // 모든 팝업 종료
            AlertDialogUtil.dismissDialog();

            // AlertDialog 종료
            dialog.dismiss();
        });

        /**
         * Page Adapter
         */
        AddressSearchPageAdapter page_adapter = new AddressSearchPageAdapter(mContext, R.layout.item_address_search_page, pageNumber, value -> {
            // Adapter in Data Clear
            result_adapter.getDataList().clear();

            // Adapter out Data Clear
            adapterDataList.clear();

            // 원하는 페이지를 클릭한 경우 그 페이지를 계산해서 작업
            // 여기서 for문의 조건값이 실제 데이터의 크기보다 넘겨서는 안되기 때문에 조건물을 추가적으로 작성
            for(int j = Constant.ARRAY_PAGE * (Integer.parseInt(value) - 1) ; j < Constant.ARRAY_PAGE * Integer.parseInt(value) ; j++)
                if(j < dataList.size())
                    adapterDataList.add(dataList.get(j));

            // setAdapter & notifyDataSetChanged
            result_recyclerView.setAdapter(result_adapter);
            result_adapter.notifyDataSetChanged();
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        /**
         * setAdapter
         */
        result_recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        result_recyclerView.setAdapter(result_adapter);
        page_recyclerView.setAdapter(page_adapter);


        // setBuilder
        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(true);
        if(dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * Dialog 종료
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dialog = null;
            }
        }
    }


    public void requestMemberJoin() {

        // ProgressBar 시작
        if(! MemberJoinActivity.this.isFinishing()) {
            showProgress();
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put(Constant.PREF_KEY_USER_ID2,mBinding.etPhonenumber.getText().toString());
        params.put(Constant.PREF_KEY_USER_PW,mBinding.etPassword.getText().toString());
        params.put(Constant.PREF_KEY_USER_NM,mBinding.etName.getText().toString());
        params.put(Constant.PREF_KEY_USER_ZC, mBinding.txtZipcode.getText().toString());
        params.put(Constant.PREF_KEY_USER_ADD1,mBinding.txtAddress1.getText().toString());
        params.put(Constant.PREF_KEY_USER_ADD2, mBinding.etAddressSub.getText().toString());
        params.put(Constant.PREF_KEY_USER_KIND,Constant.RK_USER_KIND);
        params.put(Constant.PREF_KEY_USER_GN,Constant.RK_USER_GOODNO);
        params.put(Constant.PREF_KEY_USER_REF, mBinding.txtAddrRef.getText().toString());
        params.put(Constant.RK_VALIDATION_TIMESTAMP,validationTimeStamp);

        LogUtil.e("가입 번호 "+ checkPhonenumber);
        LogUtil.e("가입 비밀번호 "+ mBinding.etPassword.getText().toString());
        LogUtil.e("가입 이름 "+ mBinding.etName.getText().toString());
        LogUtil.e("가입 우편번호 "+ mBinding.txtZipcode.getText());
        LogUtil.e("가입 주소 "+ mBinding.txtAddress1.getText());
        LogUtil.e("가입 종류 "+ Constant.RK_USER_KIND);
        LogUtil.e("가입 넘버 "+ Constant.RK_USER_GOODNO);

        Call<RetrofitItemVo> LoginCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoin(params);


        LoginCall.enqueue(new Callback<RetrofitItemVo>() {
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

                                if(itemVo.getRspCode() == 200) {
                                    // 회원가입 성공 완료 Alert
                                    AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_success_join), (dialog, which) -> {
                                        // 결과물 데이터 초기화
                                        joinItemVo = new RetrofitItemVo.RetrofitAddressVo();

                                        // View 초기화
                                        mBinding.etName.setText(null);
                                        mBinding.etPhonenumber.setText(null);
                                        mBinding.etPassword.setText(null);
                                        mBinding.etPasswordCheck.setText(null);
                                        mBinding.txtZipcode.setText(null);
                                        mBinding.txtAddress1.setText(null);
                                        mBinding.etAddressSub.setText(null);

                                        // boolean 값 초기화
                                        isPhonenumber = false;
                                        validationTimeStamp = 0L;
                                        isAddress = false;
                                        isAlertPhonenumber = false;

                                        // AlertDialog 종료
                                        AlertDialogUtil.dismissDialog();

                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });


                                }else if (itemVo.getRspCode() == 400){


                                    AlertDialogUtil.showSingleDialog(mContext,  itemVo.getAlert_msg(), (dialog, which) -> {
                                        // AlertDialog 종료
                                        AlertDialogUtil.dismissDialog();
                                    });
                                } else {
                                    // 회원가입 실패 완료 Alert
                                    AlertDialogUtil.showSingleDialog(mContext,  itemVo.getAlert_msg(), (dialog, which) -> {
                                        // AlertDialog 종료
                                        AlertDialogUtil.dismissDialog();
                                    });
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
            }
        });

    }

    /**
     * onclick
     */
    public void onClick(int casenumber) {
        switch (casenumber) {
            // 뒤로가기
            case 0:
                finish();
                break;

            // sms인증
            case 1:
                    if (CommonUtil.isNetworkConnected(mContext)) {
                        // 휴대폰 번호가 없는 경우
                        if (StringUtils.isEmpty(mBinding.etPhonenumber.getText().toString())) {
                            mBinding.etPhonenumber.requestFocus();
                            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_request_phonenumber));
                            return;
                        }

                        // 휴대폰 번호가 정규식에 맞는 경우인지
                        if (!Pattern_Phonenumber_Valid.matcher(PhoneNumberUtils.formatNumber(mBinding.etPhonenumber.getText().toString(), "KR")).matches()) {
                            mBinding.etPhonenumber.requestFocus();
                            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_phonenumber));
                            return;
                        }

                        // SMS 인증 Alert창 여부를 초기화
                        isAlertPhonenumber = false;

                        // SMS 인증 여부 초기화
                        if (isPhonenumber) {
                            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_init_phonenumber));
                            isPhonenumber = false;
                        }

                        // SMS 인증 작업
                        requestSMS(mBinding.etPhonenumber.getText().toString());
                    } else {
                        // 인터넷을 연결하지 않은 경우
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }
                    break;


            // 주소찾기
            case 2:
                if(CommonUtil.isNetworkConnected(mContext)) {
                    // 주소찾기
                    workSearchAddressAlert();
                } else {
                    // 인터넷을 연결하지 않은 경우
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                }
                break;
            // 약관 전체동의

            case 13:
                if(StringUtils.isEmpty(mBinding.etName.getText().toString())) {
                    mBinding.etName.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_customername));
                    return;
                }

                if(StringUtils.isEmpty(mBinding.etPhonenumber.getText().toString())) {
                    mBinding.etPhonenumber.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_phonenumber));
                    return;
                }

                // 휴대폰 인증을 하지 않은 경우

                LogUtil.e("휴대폰 인증여부 case : "+isPhonenumber);
                if(!isPhonenumber) {
                    AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_check_phone_success), (dialog, which) -> AlertDialogUtil.dismissDialog());
                    return;
                }

                if(StringUtils.isEmpty(mBinding.txtZipcode.getText().toString())) {
                    mBinding.txtZipcode.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_request_address_accept));
                    return;
                }

                if(mBinding.txtAddress1.getText().toString() == "                                                       ") {
                    mBinding.txtAddress1.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_custom_addr));
                    return;
                }

                if(StringUtils.isEmpty(mBinding.etPassword.getText().toString())) {
                    mBinding.etPassword.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_input_pw));
                    return;
                }
                if(StringUtils.isEmpty(mBinding.etPasswordCheck.getText().toString())) {
                    mBinding.etPasswordCheck.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_check_pw));
                    return;
                }

                // 비밀번호 정규식
                if(!Pattern_Password_Valid.matcher(Objects.requireNonNull(mBinding.etPassword.getText()).toString()).matches()) {
                    mBinding.etPassword.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_valid_password2));
                    return;
                }

                // 변경할 비빌번호 같은지 체크
                if(!StringUtils.equals(mBinding.etPassword.getText().toString(), mBinding.etPasswordCheck.getText().toString())) {
                    mBinding.etPasswordCheck.requestFocus();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pw_equal_check2));
                    return;
                }


                requestMemberJoin();


                break;
            case 14:
                if(CommonUtil.isNetworkConnected(mContext)) {
                    // 주소찾기
                    Intent intent = new Intent(MemberJoinActivity.this, SearchAddress.class);
                    intent.putExtra("broadcastName","MemberJoinActivity");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent );
                } else {
                    // 인터넷을 연결하지 않은 경우
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                }
                break;

        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // intent ..
            // intent ..
            if(intent.getAction().equals("MemberJoinActivity")){


            String data1 = intent.getStringExtra("zonecode");
            String data2 = intent.getStringExtra("roadaddress");
            String data3 = intent.getStringExtra("building");
            String data4 = intent.getStringExtra("jibun");
            mBinding.txtZipcode.setText(data1);
            mBinding.txtAddress1.setText(data2);
            if(StringUtils.isEmpty(data3)){
                mBinding.txtAddrRef.setText("("+data4+")");
            }else{
                mBinding.txtAddrRef.setText("("+data4+","+ data3+")");
            }

        }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);

    }

}
