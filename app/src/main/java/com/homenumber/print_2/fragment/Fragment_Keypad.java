package com.homenumber.print_2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.activity.simpleactivity.LoginActivity;
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
import com.homenumber.print_2_2.databinding.FragmentKeypadBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 키패드 Fragment
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class Fragment_Keypad extends BaseFragment {

    /**
     * Databinding
     */
    public FragmentKeypadBinding mBinding;

    public String D = "D";

    /**
     * 이름 정보 준비 완료 여부 & 진동 사용 여부
     */
    private boolean isNameReady;
    private boolean isVibrator;
    private boolean isTargetHNCheck;
    private Vibrator vibrator;

    /**
     * Fragment uiInit
     */
    @Override
    protected View uiInit(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // set Databinding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_keypad, parent, false);
        mBinding.setFragmentKeyPad(this);

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
        isNameReady = false;
        isVibrator = false;
        isTargetHNCheck = false;

        // View initial ButterKnife
        ButterKnife.bind(this, mBinding.getRoot());

        // Context 초기화
        if(mContext == null)
            mContext = getContext();

        if(mContext == null)
            mContext = getActivity();

        if(mContext == null)
            mContext = container.getContext();
    }

    /**
     * Fragment 화면에 들어올때
     */
    @Override
    public void startRequest() {
        if (!isStartedRequest) {
            isStartedRequest = true;

            // 타이틀 작성
//            mBinding.layoutHeader.txtFragmentHeader.setText(getString(R.string.str_keypad));

            // 진동 기능
            vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

            // 홈넘버의 13자리 입력이 모두 완료된 경우를 확인하기 위한 Listener
            mBinding.txtHomenumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(count == 13) {
                        // 글자가 13자리라면 색상을 모두 변경
                        mBinding.txtName.setTextColor(ContextCompat.getColor(mContext, R.color.C_000000));

                        if(CommonUtil.isNetworkConnected(mContext)) {
                            /**
                             * 이름 정보 요청
                             * TODO :: 이름정보 요청 -> 주소록 등록 여부 -> 이미지 인쇄 -> 주소록 추가 여부
                             */
//                            ToastUtil.showToastAsShort(mContext, "이미지를 요청할 작업 대기중");
                            requestNameInfo();
                        } else {
                            // 기본 속성 초기화
                            isNameReady = false;
                            mBinding.txtHomenumber.setText(getString(R.string.str_number_101));
                            mBinding.txtName.setText(null);

                            // 인터넷 연결 확인 팝업
                            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });

            // 키패드 옵션
            String option = activity.getUserData().getUserKeypadOption();

            // 키패드 설정
            if(StringUtils.equals(option, mContext.getString(R.string.str_sound)))
                workKeypadSetting(false, true);
            else if(StringUtils.equals(option, mContext.getString(R.string.str_vibrator)))
                workKeypadSetting(true, false);
            else if(StringUtils.equals(option, mContext.getString(R.string.str_no_effect)))
                workKeypadSetting(false, false);
        }
    }

    /**
     * 키패드 터치 설정
     */
    private void workKeypadSetting(boolean isVibrator, boolean isSound) {
        if(isVibrator)
            this.isVibrator = true;

        mBinding.btn1.setSoundEffectsEnabled(isSound);
        mBinding.btn2.setSoundEffectsEnabled(isSound);
        mBinding.btn3.setSoundEffectsEnabled(isSound);
        mBinding.btn4.setSoundEffectsEnabled(isSound);
        mBinding.btn5.setSoundEffectsEnabled(isSound);
        mBinding.btn6.setSoundEffectsEnabled(isSound);
        mBinding.btn7.setSoundEffectsEnabled(isSound);
        mBinding.btn8.setSoundEffectsEnabled(isSound);
        mBinding.btn9.setSoundEffectsEnabled(isSound);
        mBinding.btnC.setSoundEffectsEnabled(isSound);
        mBinding.btn0.setSoundEffectsEnabled(isSound);
        mBinding.btnD.setSoundEffectsEnabled(isSound);
    }

    /**
     * 숫자패드 계산기
     */
    private void workCalculator(String number) {
        // 현재 작성된 길이를 확인합니다.
        int checkLength = mBinding.txtHomenumber.getText().toString().length();

        // 전체 글자수가 13자리이면서 D가 아닌 경우를 누르면 아무 작업하지 않습니다.
        if(checkLength == 13 && !number.equals(getString(R.string.str_number_D))) {
            return;
        }

        // 작성시에 이름 정보를 초기화합니다.
        mBinding.txtName.setText(null);
        mBinding.txtName.setTextColor(ContextCompat.getColor(mContext, R.color.C_000000));
        isNameReady = false;

        switch (number) {
            case "D":
                switch (checkLength) {
                    case 3:
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_do_not_delete));
                        break;

                    case 5:
                    case 10:
                    case 15:
                        if(checkLength == 5) {
                            mBinding.txtHomenumber.setText(mBinding.txtHomenumber.getText().toString().substring(0, checkLength - 2));
                        } else {
                            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(mBinding.txtHomenumber.getText().toString().substring(0, checkLength - 2));
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.C_FF0000)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mBinding.txtHomenumber.setText(spannableStringBuilder);
                        }
                        break;

                    default:
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(mBinding.txtHomenumber.getText().toString().substring(0, checkLength - 1));
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.C_FF0000)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mBinding.txtHomenumber.setText(spannableStringBuilder);
                        break;
                }
                break;

            default:
                int MAX_HOME_NUMBER = 13;

                if(checkLength >= MAX_HOME_NUMBER) {
                    Toast.makeText(mContext, getString(R.string.msg_do_not_insert), Toast.LENGTH_SHORT).show();
                } else {
                    String check;
                    if (checkLength == 3 || checkLength == 8)
                        check = mBinding.txtHomenumber.getText().toString() + getString(R.string.str_number_division);
                    else
                        check = mBinding.txtHomenumber.getText().toString();

                    StringBuilder stringBuilder = new StringBuilder(check);
                    stringBuilder.append(number);

                    if(stringBuilder.length() == 13) {
                        mBinding.txtHomenumber.setText(stringBuilder);
                    } else {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.C_FF0000)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mBinding.txtHomenumber.setText(spannableStringBuilder);
                    }
                }
                break;
        }
    }

    /**
     * onClick_button_Number(Delete)
     */
    public void onClick_Number(String number) {
        if(isVibrator)
            vibrator.vibrate(250);

        workCalculator(number);
    }

    /**
     * onClick_button_Clear
     */
    public void onClick_Clear() {
        if(isVibrator)
            vibrator.vibrate(250);

        // 기본 속성 초기화
        isNameReady = false;
        mBinding.txtHomenumber.setText(getString(R.string.str_number_101));
        mBinding.txtName.setText(null);
    }

    /**
     * 이름 정보 요청
     ************************************************************************************************************************************************/
    private void requestNameInfo() {
        // Progress 시작
        showProgress();

        // Call 객체 생성
        Call<RetrofitItemVo> nameinfoCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, mainFragmentActivity.token, mainFragmentActivity.device_uuid, RetrofitService.class).requestHomenumberName(mBinding.txtHomenumber.getText().toString());

        // Call 객체를 통한 통신 시작
        nameinfoCall.enqueue(new Callback<RetrofitItemVo>() {
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
                                    // 작성된 홈넘버의 정보중 이름을 가져옵니다.
                                    StringBuilder myName = new StringBuilder(itemVo.getData().getUSER_NM());

                                    // 작성된 이름에서 맨 앞글자와 뒷글자를 제외한 모든 글자를 [*]로 변경합니다.
                                    for(int i = 1; i <= itemVo.getData().getUSER_NM().length(); i++) {
                                        if(i != 1 && i != itemVo.getData().getUSER_NM().length())
                                            myName.setCharAt(i - 1, '*');
                                    }

                                    // 변경된 이름을 작성합니다.
                                    mBinding.txtName.setText(myName);

                                    // 인쇄를 하기 위하여 통신에서 기본적인 이름 정보를 가져옴에 확인했음을 정의합니다.
                                    isNameReady = true;
                                } else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                    ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                    ClearDataUtil.ClearUserData(mContext);
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    startActivity(intent);
                                    activity.finish();
                                } else if(itemVo.getRspCode() == 400 && itemVo.getErrorCode().equals("1201")){
                                    // 정상적이지 않은 경우
                                     ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                                    // 기본 속성 초기화 :: 정상적인 홈넘버가 아니라면 이름란에 색상을 빨강색으로 변경
                                    isNameReady = false;
                                    mBinding.txtName.setTextColor(ContextCompat.getColor(mContext, R.color.C_FF0000));
                                    mBinding.txtName.setText("잘못된 홈넘버입니다");
                                }else{
                                    ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                                }
                        }
                    } else {
                        // 정상적이지 않은 통신 실패의 경우
//                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                        // 기본 속성 초기화 :: 정상적인 홈넘버가 아니라면 이름란에 색상을 빨강색으로 변경
                        isNameReady = false;
                        mBinding.txtName.setTextColor(ContextCompat.getColor(mContext, R.color.C_FF0000));
                        mBinding.txtName.setText("잘못된 홈넘버입니다");
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
     * 예약
     ************************************************************************************************************************************************/
    public void createOrderNumber() {
        if(CommonUtil.isNetworkConnected(mContext)) {

            String receiver_hn = mBinding.txtHomenumber.getText().toString();
            receiver_hn = receiver_hn.replace("-", "");

            // 서버에 전송할 데이터를 Map형태로 정리
            HashMap<String, Object> params = new HashMap<>();
            if(mainFragmentActivity.getUserData().getSelectedUserAlias().equals("HOMENUM1")){

                params.put(Constant.SENDER_HN, activity.getUserData().getUserHN());                         // 보내는 사람 홈넘버   // 필수
            }else{
                if(StringUtils.isEmpty(activity.getUserData().getUserHN2())){
                    params.put(Constant.SENDER_HN, activity.getUserData().getUserHN());                         // 보내는 사람 홈넘버   // 필수
                }else{
                    params.put(Constant.SENDER_HN, activity.getUserData().getUserHN2());                         // 보내는 사람 홈넘버   // 필수

                }
            }
            params.put(Constant.RECEIVER_HN, receiver_hn);                                              // 받는 사람 홈넘버    // 필수
//            params.put(Constant.PAYMENT_CHARGE, activity.getUserData().getItemPrice());                 // 운임 금액         // 필수
//            params.put(Constant.PAYMENT_DISCOUNT, discount);                                            // 할인금액
            params.put(Constant.PAYMENT_METHOD, activity.getUserData().getItemHow());                   // 결제 방법         // 필수
            params.put(Constant.ORDER_INFO_KEYPRODUCT_NM, activity.getUserData().getItemName());        // 상품명           // 필수
            params.put(Constant.ORDER_INFO_PRODUCT_INFO, activity.getUserData().getItemPlusInfo());          // 상품 추가정보
            params.put(Constant.ORDER_INFO_PRODUCT_TOT_PRICE, activity.getUserData().getItemPrice());   // 상품 가격        // 필수
            params.put(Constant.ORDER_INFO_PRODUCT_TOT_QTY, activity.getUserData().getItemNumber());    // 상품 갯수        // 필수
            params.put(Constant.ORDER_INFO_PRODUCT_LIMIT_WEIGHT, activity.getUserData().getItemKg());   // 상품 무게        // 필수
//            params.put(Constant.ORDER_INFO_CUST_MSG, activity.getUserData().getItemMessage());          // 고객 요청 메세지
//            params.put(Constant.ORDER_INFO_MISC, activity.getUserData().getItemPlusInfo());             // 주문 관련 추가 정보
            params.put(Constant.COURIER_CODE, Constant.PRINT_CVSNET);             // 주문 관련 추가 정보

            showProgress();

            Call<RetrofitItemVo> createOrderNumber = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, activity.token ,activity.device_uuid , RetrofitService.class).requestCVSNET(params);

            createOrderNumber.enqueue(new Callback<RetrofitItemVo>() {
                @Override
                public void onResponse(@NotNull Call<RetrofitItemVo> call,@NotNull  Response<RetrofitItemVo> response) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        dismissProgress();
                    if (response.isSuccessful()) {
                        RetrofitItemVo itemVo = response.body();
                        mainFragmentActivity.ResetHistory = true;
                        if (itemVo != null) {
                            if (itemVo.getRspCode() == 200) {

                                // 오더넘버 4글자로 끊어서 보여주기

                                String on = itemVo.getData().getResult().getOrderNo();

                                String[] ox = splitStringEvery(on, 4);

                                AlertDialogUtil.orderKeypadDialog(mContext, "승인번호는 " + ox[0] + "-" + ox[1] + "-" + ox[2] + " 입니다", (dialog, which) -> AlertDialogUtil.dismissDialog());

                            }else if (itemVo.getRspCode() == 500) {

                                AlertDialogUtil.showSingleDialog(mContext, itemVo.getAlert_msg(), (dialog, which) -> AlertDialogUtil.dismissDialog());

                            }else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                ClearDataUtil.ClearUserData(mContext);
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                startActivity(intent);
                                activity.finish();
                            }else{
                                // 에러 메세지를 토스트로 보여줍니다.
                                ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                            }

                        } else {
                            // 정상적이지 않은 통신 실패의 경우
                            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                        }
                    }
                    });
                }

                @Override
                public void onFailure(Call<RetrofitItemVo> call, Throwable t) {
                    dismissProgress();
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pls_correct_info));
                    // adapter

                    mainFragmentActivity.mBinding.viewPagerMain.setCurrentItem(mainFragmentActivity.viewPagerAdapter.getPagePosition(Fragment_ItemInfo.class));
                }
            });



        }
    }

    // string 문자열 4글자로 끊어서 출력
    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }


    /**
     * 예약번호 출력
     */
    @OnClick({R.id.btn_print, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_0, R.id.btn_c, R.id.btn_d})
    void butterknife_onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_print:
                if (CommonUtil.isNetworkConnected(mContext)) {
                    // 이름 정보가 입력되어 있지 않은 있는 경우
                    if (!isNameReady) {
                        dismissProgress();
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_push_homenumber));
                        return;
                    }

                    createOrderNumber();

                } else {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));

                    break;

                }

            case R.id.btn_1:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_1));
                break;

            case R.id.btn_2:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_2));
                break;

            case R.id.btn_3:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_3));
                break;

            case R.id.btn_4:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_4));
                break;

            case R.id.btn_5:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_5));
                break;

            case R.id.btn_6:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_6));
                break;

            case R.id.btn_7:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_7));
                break;

            case R.id.btn_8:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_8));
                break;

            case R.id.btn_9:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_9));
                break;

            case R.id.btn_0:
                if(isVibrator)
                    vibrator.vibrate(250);

                workCalculator(getString(R.string.str_number_0));
                break;

            case R.id.btn_c:
                if(isVibrator)
                    vibrator.vibrate(250);

                // 기본 속성 초기화
                isNameReady = false;
                mBinding.txtHomenumber.setText(getString(R.string.str_number_101));
                mBinding.txtName.setText(null);
                break;

            case R.id.btn_d:
                if(isVibrator)
                    vibrator.vibrate(250);

                // 글자만 하나씩 뒤에서 지워줍니다.
                workCalculator(getString(R.string.str_number_D));
                break;
        }


    }

}