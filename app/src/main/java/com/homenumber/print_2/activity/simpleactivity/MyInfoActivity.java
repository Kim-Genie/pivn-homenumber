package com.homenumber.print_2.activity.simpleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityMyInfoBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MyInfoActivity
 *
 * @author 나비이쁜이
 * @since 2019.08.23
 */
public class MyInfoActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivityMyInfoBinding mBinding;
    /**
     * Application User Local DataBase
     */
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_info);
        mBinding.setActivity(this);
        uiInit();
    }

    /**
     * uiInit
     */
    @Override
    protected void uiInit() {
        super.uiInit();

        if( StringUtils.isNotEmpty(userData.getUserHN()) &&
                StringUtils.isNotEmpty(userData.getUserHN2())){
            mBinding.btnPrint.setVisibility(View.GONE);
        }else{
            mBinding.btnPrint.setVisibility(View.VISIBLE);
        }


        // 뒤로가기 여부
        isBackButtonNotice = false;

        // 유저 아이디
//        mBinding.txtUserId.setText(userData.getUserID());

        LogUtil.e("이름" + userData.getUserName());
        // 유저 집


        mBinding.txtUserAddress1.setText(userData.getUserHomeAddress() + " " + userData.getUserDetailAddress());
        StringBuffer phoneNum = new StringBuffer(userData.getUserTelephone1());
        phoneNum.insert(3, "-");
        phoneNum.insert(8, "-");
        mBinding.txtUserCall1.setText(phoneNum);
        mBinding.txtUserName1.setText(userData.getUserName());
        StringBuffer hn1 = new StringBuffer(userData.getUserHN());
        hn1.insert(3, "-");
        hn1.insert(8, "-");
        mBinding.txtHomenumber1.setText(hn1); //집 홈넘버
        mBinding.txtUserNormalCall1.setText(userData.getUserNormalTelephone1());//일반전화
        mBinding.txtAlias1.setText(userData.getUserAlias());//별칭

        System.out.println("userData.getUserHN2() = " + userData.getUserHN2());

        if (StringUtils.isNotEmpty(userData.getUserHN2())) {
            mBinding.secondLayout.setVisibility(View.VISIBLE);
            mBinding.txtUserAddress2.setText(userData.getUserCompanyAddress() + " " + userData.getUserDetailAddress2());
            mBinding.txtUserCall2.setText(phoneNum);
            mBinding.txtUserName2.setText(userData.getUserName2());
            StringBuffer hn2 = new StringBuffer(userData.getUserHN2());
            hn2.insert(3, "-");
            hn2.insert(8, "-");
            mBinding.txtHomenumber2.setText(hn2);
            mBinding.txtUserNormalCall2.setText(userData.getUserNormalTelephone2());
            mBinding.txtAlias2.setText(userData.getUserAlias2());
            if (userData.getSelectedUserAlias().equals("HOMENUM1")) {
                mBinding.txtUserName1.setTextColor(getResources().getColor(R.color.C_DA6C6B));
                mBinding.txtUserName2.setTextColor(getResources().getColor(R.color.C_000000));
            } else {
                mBinding.txtUserName1.setTextColor(getResources().getColor(R.color.C_000000));
                mBinding.txtUserName2.setTextColor(getResources().getColor(R.color.C_DA6C6B));
            }
        } else {
            mBinding.txtUserName1.setTextColor(getResources().getColor(R.color.C_DA6C6B));
        }


    }


    public void workFinish(){
        finish();
    }

    public void workModify() {
        Intent intent = new Intent(MyInfoActivity.this, AddMyInfoActivity.class);
        startActivity(intent);
    }


    public void deleteHn(int type){

        if((userData.getSelectedUserAlias().equals("HOMENUM1") ? 0 : 1) == type){
            ToastUtil.showToastAsShort(mContext, "사용 중인 홈넘버는 삭제할 수 없습니다.");
            return;
        }

        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.PREF_KEY_SMTADDR, type == 0 ? userData.getUserHN() : userData.getUserHN2());

        // Call 객체 생성
        Call<RetrofitItemVo> nameinfoCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).deleteHn(params);

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
                                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_myinfo8));

                                if (type == 0) {
                                    mBinding.secondLayout2.setVisibility(View.GONE);
                                    userData.setUserHN("");
                                    userDataManager.setUserData(userData);
                                }else{
                                    mBinding.secondLayout.setVisibility(View.GONE);
                                    userData.setUserHN2("");
                                    userDataManager.setUserData(userData);
                                }


                            } else {
                                ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                            }
                        }

                        if( StringUtils.isNotEmpty(userData.getUserHN()) &&
                                StringUtils.isNotEmpty(userData.getUserHN2())){
                            mBinding.btnPrint.setVisibility(View.GONE);
                        }else{
                            mBinding.btnPrint.setVisibility(View.VISIBLE);
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

    public void selectedHomenumber(int index){
        if(index == 0){
            mBinding.txtUserName1.setTextColor(getResources().getColor(R.color.C_DA6C6B));
            mBinding.txtUserName2.setTextColor(getResources().getColor(R.color.C_000000));
            userData.setSelectedUserAlias("HOMENUM1");
            userDataManager.setUserData(userData);
        }else{
            mBinding.txtUserName2.setTextColor(getResources().getColor(R.color.C_DA6C6B));
            mBinding.txtUserName1.setTextColor(getResources().getColor(R.color.C_000000));
            userData.setSelectedUserAlias("HOMENUM2");
            userDataManager.setUserData(userData);
        }
    }

    public void getModify(int index){
        if(index == 0 || index == 1){
            Intent intent = new Intent(MyInfoActivity.this, AddMyInfoActivity.class);
            intent.putExtra("item_index", String.valueOf(index));
            startActivityForResult(intent, 0);
        }
    }

    public void getQRView(int index){
        Intent intent = new Intent(MyInfoActivity.this, MyHomeNumberQRActivity.class);
        if(index == 0){
            intent.putExtra("homenumber",
                    userDataManager.getUserData().getUserHN());

        }else{
            intent.putExtra("homenumber",
                    userDataManager.getUserData().getUserHN2());

        }

        startActivityForResult(intent, 0);

    }
}