package com.homenumber.print_2.activity.simpleactivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.activity.fragmentactivity.MainFragmentActivity;
import com.homenumber.print_2.adapter.recyclerview.AddressSearchPageAdapter;
import com.homenumber.print_2.adapter.recyclerview.AddressSearchResultAdapter;
import com.homenumber.print_2.alertdialog.AlertDialogUtil;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.permission.PermissionUtil;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2.retrofit2.RetrofitService;
import com.homenumber.print_2.retrofit2.RetrofitUtil;
import com.homenumber.print_2.util.ClearDataUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.RequestCodeUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityAddMyInfoBinding;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MyInfoActivity
 *
 * @author ???????????????
 * @since 2019.08.23
 */
public class AddMyInfoActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivityAddMyInfoBinding mBinding;
    //???????????????2 ????????????
    private boolean canUseGoldNum = true;
    //???????????????2 ??????????????????
    private boolean hvHn2 = false;
    //??????????????? ?????? ??????
    private boolean goldUser = false;
    //?????????1????????????
    private String zipCode1 = "";
    //?????????2????????????
    private String zipCode2 = "";
    //?????????1??????
    private String smtCode1 = "";
    //?????????2??????
    private String smtCode2 = "";
    //canDelete
    private boolean canDelete = false;

    private boolean canChangeAuthKey = false;

    private AlertDialog dialog;
    private AddressSearchResultAdapter result_adapter;
    private RetrofitItemVo.RetrofitAddressVo joinItemVo;
    /**
     * Alert RecyclerView & Result Item
     */
    private RecyclerView result_recyclerView;
    /**
     * Application User Local DataBase
     */
    private String whichAddress = "";

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_my_info);
        mBinding.setActivity(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("AddMyInfoActivity1");
        filter.addAction("AddMyInfoActivity2");
        registerReceiver(mBroadcastReceiver, filter);
        usimCheck();
        uiInit();
        if(this.getIntent() != null ){
            String index = (String)this.getIntent().getSerializableExtra("item_index");

            if("0".equals(index)){
                mBinding.homenumber1Layout.setVisibility(View.VISIBLE);
                mBinding.homenumber2Layout.setVisibility(View.GONE);
            }else if("1".equals(index)){
                mBinding.homenumber1Layout.setVisibility(View.GONE);
                mBinding.homenumber2Layout.setVisibility(View.VISIBLE);

            }

        }



    }

    /**
     * uiInit
     */
    @Override
    protected void uiInit() {
        super.uiInit();

        // ???????????? ??????
        isBackButtonNotice = false;

        //        mBinding.txtUserNormalCall1.addTextChangedListener(new MaskWatcher("###-####-####"));
//        mBinding.txtUserNormalCall2.addTextChangedListener(new MaskWatcher("###-####-####"));

        // ?????? ?????????
//        mBinding.txtUserId.setText(userData.getUserID());
        mBinding.txtHomenumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                canUseGoldNum = false;
//                zipCode1 = "";
//                zipCode2 = "";
            }
        });

        LogUtil.e("??????" + userData.getUserName());

        requestSelectMyInfo();


    }

    public void delete(int casenumber) {
        switch (casenumber) {
            case 1:
                if (canDelete) {
                    requestDeleteHn(mBinding.txtHomenumber1.getText().toString());
                } else {
                    AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_myinfo2), (dialog, which) -> {
                    });
                }

                break;
            case 2:
                requestDeleteHn(mBinding.txtHomenumber2.getText().toString());
                break;
        }
    }

    private void usimCheck() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager == null) {
            ToastUtil.showToastAsShort(this, "?????????????????? ????????? ??? ????????????.");
        } else {
            try {
                int simState = mTelephonyManager.getSimState();
                switch (simState) {
                    // ?????? ????????? ??? ??? ?????? ??????
                    case TelephonyManager.SIM_STATE_UNKNOWN:
                        // ????????? ?????? ??????
                    case TelephonyManager.SIM_STATE_ABSENT:
                        // ?????? ??????, ???????????? ?????? ?????? ??????
                    case TelephonyManager.SIM_STATE_PERM_DISABLED:
                        // ????????? ????????? ?????? ????????? ??????
                    case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                        // ????????? ????????? ????????? ???????????? ?????? ??????
                    case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                        ToastUtil.showToastAsShort(this, "???????????? ????????? ???????????? ????????? ????????? ?????? ??????, ?????? ????????? ??? ????????????.");
                        break;

                    default:
                        // ?????? ????????? ????????? ??????
                        boolean isPermission = PermissionUtil.checkAndRequestPermission(this, RequestCodeUtil.PermissionCode.REQ_PERMISSION_ALL, Manifest.permission.READ_PHONE_STATE);
                        if(isPermission){
                            String telNumber = mTelephonyManager.getLine1Number();
                            telNumber = telNumber.replace("+82", "0");


                            Log.d("simCheck", "Tel No. " + telNumber);
                            if(telNumber.equals(userData.getUserID())){
                                canChangeAuthKey = true;
                            }
                            break;
                        }
                }
            } catch (Exception e) {
                ToastUtil.showToastAsShort(this, "???????????? ????????? ???????????? ??? ????????? ??????????????????. ?????? ??? ?????? ??????????????????.");
                Log.e("simCheck", "Exception: " + e.toString());
            }
        }
    }



    /**
     * onClick_back
     */
    public void workFinish() {
        finish();
    }

    public void searchAddress(int casenumber) {
        switch (casenumber) {
            case 1:
                if(CommonUtil.isNetworkConnected(mContext)) {
                    workSearchAddressAlert("1");
                }else{
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                }
//                Intent intent = new Intent(AddMyInfoActivity.this, SearchAddress.class);
//                intent.putExtra("broadcastName", "AddMyInfoActivity1");
//                startActivity(intent);
                break;
            case 2:
                if(CommonUtil.isNetworkConnected(mContext)) {
                    workSearchAddressAlert("2");
                }else{
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                }
//                Intent intent2 = new Intent(AddMyInfoActivity.this, SearchAddress.class);
//                intent2.putExtra("broadcastName", "AddMyInfoActivity2");
//                startActivity(intent2);
                break;

        }


    }

    /**
     * ????????? ??????
     * @param key
     */
    public void authModify(int key) {
        switch (key) {
            case 1:
                if(mBinding.txtAuthkey1.getText().toString().length() < 4){
                    ToastUtil.showToastAsShort(mContext,getString(R.string.msg_myinfo9));
                    mBinding.txtAuthkey1.requestFocus();
                    break;
                }
                modifyAuthKey(mBinding.txtHomenumber1.getText().toString().replace("-", ""), mBinding.txtAuthkey1.getText().toString());
                
                break;
            case 2:
                if(mBinding.txtAuthkey2.getText().toString().length() < 4){
                    ToastUtil.showToastAsShort(mContext,getString(R.string.msg_myinfo9));
                    mBinding.txtAuthkey2.requestFocus();
                    break;
                }

                modifyAuthKey(mBinding.txtHomenumber2.getText().toString().replace("-", ""), mBinding.txtAuthkey2.getText().toString());
                break;

        }


    }

    /**
     * 
     * @param homNumber
     * @param homNumberPassWord
     */
    private void modifyAuthKey(String homNumber, String homNumberPassWord) {

        // ProgressBar ??????
        if(! AddMyInfoActivity.this.isFinishing()) {
            showProgress();
        }

        // Call ????????? ?????? ?????? ??????
        Call<RetrofitItemVo> requestCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token,device_uuid, RetrofitService.class).modifyAuthKey(homNumber,homNumberPassWord);
        requestCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                // ??????????????? ????????? ????????? ??????
                // Progress ??????
                dismissProgress();
                if (response.isSuccessful()) {
                    // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                    RetrofitItemVo itemVo = response.body();
                    if(itemVo.getRspCode() == 200){
                        if(itemVo.getData().getUPDATE_SECRET_KEY_COUNT() == 1){
                            ToastUtil.showToastAsLong(mContext, getString(R.string.msg_myinfo10));
                        }else{
                            ToastUtil.showToastAsLong(mContext, getString(R.string.msg_myinfo11));
                        }
                    }else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                        ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                        ClearDataUtil.ClearUserData(mContext);
                        redirectActivityAllClear(LoginActivity.class);
                    }else{
                        ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                    }

                    // dismaiss Progrss
                    dismissProgress();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        // ??????????????? ?????? ?????? ????????? ??????
                        ToastUtil.showToastAsShort(mContext, jObjError.getString("alert_msg"));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                // ?????? ????????? ???????????? ???????????? ??????
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress ??????
                dismissProgress();

                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });

    }




    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("AddMyInfoActivity1")) {

                String data1 = intent.getStringExtra("zonecode");
                String data2 = intent.getStringExtra("roadaddress");
                String data3 = intent.getStringExtra("building");
                String data4 = intent.getStringExtra("jibun");
                zipCode1 =data1;
                mBinding.txtUserAddress1.setText(data2);
                if(StringUtils.isEmpty(data3)){
                    mBinding.txtUserRef1.setText("("+data4+")");
                }else{
                    mBinding.txtUserRef1.setText("("+data4+","+ data3+")");
                }

            } else if (intent.getAction().equals("AddMyInfoActivity2")) {
                String data1 = intent.getStringExtra("zonecode");
                String data2 = intent.getStringExtra("roadaddress");
                String data3 = intent.getStringExtra("building");
                String data4 = intent.getStringExtra("jibun");
                zipCode2 =data1;
                mBinding.txtUserAddress2.setText(data2);
                if(StringUtils.isEmpty(data3)){
                    mBinding.txtUserRef2.setText("("+data4+")");
                }else{
                    mBinding.txtUserRef2.setText("("+data4+")");
                }

            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);

    }

    /**
     * ?????????????????????
     */
    private void requestSelectMyInfo() {
        // ProgressBar ??????
        if(! AddMyInfoActivity.this.isFinishing()) {
            showProgress();
        }
        // Call ????????? ?????? ?????? ??????
        Call<RetrofitItemVo> requestCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).requestUserHnInfo();

        requestCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                // ??????????????? ????????? ????????? ??????
                // Progress ??????
                dismissProgress();
                if (response.isSuccessful()) {
                    // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                    RetrofitItemVo itemVo = response.body();

                    if (itemVo != null) {

                            if (itemVo.getRspCode() == 200) {

                                /**
                                 * ????????? ?????????
                                 */
                                goldUser = itemVo.getData().getGoodNo().equals("GH");
                                mBinding.txtUserAddress1.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_BASE_ADDRESS());
                                mBinding.txtUserDetailAddress1.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_DTL_ADDRESS());
                                StringBuffer hn1 = new StringBuffer(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getHOME_NO());
                                hn1.insert(3, "-");
                                hn1.insert(8, "-");
                                mBinding.txtHomenumber1.setText(hn1); //??? ?????????
                                mBinding.txtUserNormalCall1.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_TEL());
                                mBinding.txtAlias1.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getALIAS());
                                mBinding.txtUserRef1.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getADDR_REF());
                                smtCode1 =  Integer.toString(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getSMT_SN());
                                smtCode2 =  smtCode1.equals("1")? "2":"1";
                                zipCode1 = itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getZIP_CD();
                                if (itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 1 && !StringUtils.isEmpty(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getHOME_NO())) {
                                    StringBuffer hn2 = new StringBuffer(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getHOME_NO());
                                    hn2.insert(3, "-");
                                    hn2.insert(8, "-");
                                    mBinding.txtHomenumber2.setText(hn2);//?????? ?????????
                                    mBinding.txtHomenumber2.setEnabled(false);
                                    mBinding.txtUserAddress2.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_BASE_ADDRESS());
                                    mBinding.txtUserDetailAddress2.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_DTL_ADDRESS());
                                    mBinding.txtUserNormalCall2.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_TEL());
                                    mBinding.txtAlias2.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getALIAS());
                                    mBinding.txtUserRef2.setText(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getADDR_REF());
                                    zipCode2 = itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getZIP_CD();
                                    if(goldUser){
                                        hvHn2 = true;
                                    }
                                    if(!StringUtils.isEmpty(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_BASE_ADDRESS()) && !StringUtils.isEmpty(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getALIAS())){
                                        canDelete =true;
                                    }
                                } else {
                                    /**
                                     * ?????????2??? ??????????????? ????????????????????? ?????? > ?????? ??????
                                     */
                                    mBinding.txtHomenumber2.setEnabled(true);
                                    canDelete = false;
//                                    mBinding.btnCheckDuplicate.setVisibility(View.VISIBLE);
                                }
                                if (itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 1 && itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getSMT_SN() > 0) {
//                                    mBinding.btnDelete2.setVisibility(View.VISIBLE);
                                } else {
//                                    mBinding.btnDelete2.setVisibility(View.GONE);
                                }
//                                if(canChangeAuthKey){
//                                    mBinding.layoutAuthKey1.setVisibility(View.VISIBLE);
//                                    if(!StringUtils.isEmpty(mBinding.txtUserAddress2.getText().toString())){
//                                        mBinding.layoutAuthKey2.setVisibility(View.VISIBLE);
//
//                                    }
//                                }
                            } else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                ClearDataUtil.ClearUserData(mContext);
                                redirectActivityAllClear(LoginActivity.class);
                            }else {
                                // ??????????????? ?????? ??????
                                ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                            }
                    }

                    // dismaiss Progrss
                    dismissProgress();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        // ??????????????? ?????? ?????? ????????? ??????
                        ToastUtil.showToastAsShort(mContext, jObjError.getString("alert_msg"));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                // ?????? ????????? ???????????? ???????????? ??????
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress ??????
                dismissProgress();

                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });
    }

    /**
     * ??????????????? ????????????
     */
    public void CheckDuplicate(){
        // Call ????????? ?????? ?????? ??????
        if(mBinding.txtHomenumber2.getText().toString().length() != 11){
            mBinding.txtHomenumber2.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_valid_num1));
            return;

        }
        if(!"101".equals(mBinding.txtHomenumber2.getText().toString().substring(0,3))){
            mBinding.txtHomenumber2.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_valid_num2));
            return;
        }
        Call<RetrofitItemVo> requestCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).requestDuplicate(mBinding.txtHomenumber2.getText().toString());

        requestCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                // ??????????????? ????????? ????????? ??????
                // Progress ??????
                dismissProgress();
                if (response.isSuccessful()) {
                    // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                    RetrofitItemVo itemVo = response.body();

                    if (itemVo != null) {
                        if(itemVo.getRspCode() ==200){
                            if (itemVo.getData().isDuplicateResult()) {
                                AlertDialogUtil.showSingleDialog(mContext,getString(R.string.msg_can_use_number),(dialog, which) -> {});
                                canUseGoldNum = true;
                            } else {
                                // ??????????????? ?????? ??????
                                canUseGoldNum = false;
                                ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                            }
                        }else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                            ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                            ClearDataUtil.ClearUserData(mContext);
                            redirectActivityAllClear(LoginActivity.class);
                        }else{
                            // ??????????????? ?????? ??????
                            ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                            canUseGoldNum = false;
                        }
                    }

                    // dismaiss Progrss
                    dismissProgress();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        // ??????????????? ?????? ?????? ????????? ??????
                        ToastUtil.showToastAsShort(mContext, jObjError.getString("alert_msg"));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                // ?????? ????????? ???????????? ???????????? ??????
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress ??????
                dismissProgress();
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });
    }

    public void updateMyInfo(){

        if(chackValid()){
            HashMap<String,ArrayList<JSONObject>> param = new HashMap<>();
            ArrayList<JSONObject> listItem = new ArrayList<>();
            for(int i = 0; i< 2 ; i++){
                HashMap<String, Object> item = new HashMap<>();
                if(i==0){
                    item.put(Constant.PREF_KEY_SMTSN,smtCode1); //??????
                    item.put(Constant.PREF_KEY_SMTADDR,mBinding.txtHomenumber1.getText().toString()); // ?????????
                    item.put(Constant.PREF_KEY_USER_ZC,zipCode1); //????????????
                    item.put(Constant.PREF_KEY_USER_ADD1, mBinding.txtUserAddress1.getText().toString()); // ????????????
                    item.put(Constant.PREF_KEY_USER_ADD2,mBinding.txtUserDetailAddress1.getText().toString()); // ????????????
                    item.put(Constant.PREF_KEY_USER_REF, mBinding.txtUserRef1.getText().toString()); // ???????????????
                    item.put(Constant.PREF_KEY_USERTEL,mBinding.txtUserNormalCall1.getText().toString()); //?????????
                    item.put(Constant.PREF_KEY_USER_ALIAS, mBinding.txtAlias1.getText().toString());//??????
                    JSONObject json = new JSONObject(item);
                    listItem.add(json);
                }else{
                    item.put(Constant.PREF_KEY_SMTSN,smtCode2);//??????
                    if(goldUser && mBinding.txtHomenumber2.getText().toString().length() == 11){
                        StringBuffer hn1 = new StringBuffer(mBinding.txtHomenumber2.getText().toString());
                        hn1.insert(3, "-");
                        hn1.insert(8, "-");
                        item.put(Constant.PREF_KEY_SMTADDR,hn1);// ?????????
                    }else{
                        item.put(Constant.PREF_KEY_SMTADDR,mBinding.txtHomenumber2.getText().toString());// ?????????
                    }
                    item.put(Constant.PREF_KEY_USER_ZC,zipCode2);//????????????
                    item.put(Constant.PREF_KEY_USER_ADD1, mBinding.txtUserAddress2.getText().toString()); // ????????????
                    item.put(Constant.PREF_KEY_USER_ADD2,mBinding.txtUserDetailAddress2.getText().toString());// ????????????
                    item.put(Constant.PREF_KEY_USER_REF, mBinding.txtUserRef2.getText().toString());// ???????????????
                    item.put(Constant.PREF_KEY_USERTEL,mBinding.txtUserNormalCall2.getText().toString());//?????????
                    item.put(Constant.PREF_KEY_USER_ALIAS, mBinding.txtAlias2.getText().toString());//??????
                    JSONObject json = new JSONObject(item);
                    listItem.add(json);
                }
            }
            param.put(Constant.PREF_KEY_SMTADDRS,listItem);
            System.out.println("listItem = " + listItem);
            Call<RetrofitItemVo> requestCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).updateUserInfo(param);
            // ProgressBar ??????
            if(! AddMyInfoActivity.this.isFinishing()) {
                showProgress();
            }
            requestCall.enqueue(new Callback<RetrofitItemVo>() {
                @Override
                public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                    // ??????????????? ????????? ????????? ??????
                    // Progress ??????
                    dismissProgress();
                    if (response.isSuccessful()) {
                                // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                                RetrofitItemVo itemVo = response.body();
                                if(itemVo.getRspCode() == 200){

                                    if (itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 0) {
                                        userData.setGoodNo(itemVo.getData().getGoodNo());
                                        //????????? ??? - ?????? ??????
                                        userData.setUserHN(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getHOME_NO()); // ?????????
                                        userData.setUserHomeAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_BASE_ADDRESS()); // ????????????
                                        userData.setUserDetailAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_DTL_ADDRESS()); // ????????????
                                        userData.setUserName(itemVo.getData().getUserNm()); // ??????
                                        userData.setUserTelephone1(itemVo.getData().getUserId()); // ?????? ??? ??????
                                        userData.setUserAlias(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getALIAS()); // ??? / ??????
                                        userData.setUserNormalTelephone1(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_TEL()); // ?????? ??? ??????
                                        userData.setUserZipCode1(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getZIP_CD()); // ?????? ????????????
                                        if (itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 1) {
                                            userData.setUserHN2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getHOME_NO()); // ?????? ?????????
                                            userData.setUserCompanyAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_BASE_ADDRESS()); // ????????????
                                            userData.setUserDetailAddress2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_DTL_ADDRESS()); // ????????????
                                            userData.setUserName2(itemVo.getData().getUserNm()); // ??????
                                            userData.setUserTelephone2(itemVo.getData().getUserId()); // ?????? ??? ??????
                                            userData.setUserAlias2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getALIAS()); // ??? / ??????
                                            userData.setUserNormalTelephone2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_TEL()); // ?????? ??? ??????
                                            userData.setUserZipCode2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getZIP_CD()); // ?????? ????????????
                                        } else {
                                            userData.setUserCompanyAddress("");
                                            userData.setUserDetailAddress2("");
                                            userData.setUserAlias2("");
                                            userData.setUserHN2("");
                                            userData.setUserName2("");
                                            userData.setUserZipCode2("");
                                        }
                                        // ?????? ????????? ??????
                                        userDataManager.setUserData(userData);

                                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_myinfo1));
                                        Intent intent = new Intent(AddMyInfoActivity.this, MainFragmentActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }
                                }else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                    ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                    ClearDataUtil.ClearUserData(mContext);
                                    redirectActivityAllClear(LoginActivity.class);
                                }else{
                                    ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                }
                        // dismaiss Progrss
                        dismissProgress();
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());

                            // ??????????????? ?????? ?????? ????????? ??????
                            ToastUtil.showToastAsShort(mContext, jObjError.getString("alert_msg"));
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        dismissProgress();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                    // ?????? ????????? ???????????? ???????????? ??????
                    LogUtil.e("onFailure: " + t.getMessage());

                    // Progress ??????
                    dismissProgress();

                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
                }
            });
        }

    }
    public boolean chackValid(){
        if(goldUser){
            if(hvHn2){
                if(StringUtils.isEmpty(mBinding.txtUserAddress1.getText().toString())){
                    mBinding.txtUserAddress1.requestFocus();
                    ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo3));
                    return false;
                }
                if(StringUtils.isEmpty(mBinding.txtAlias1.getText().toString())){
                    mBinding.txtAlias1.requestFocus();
                    ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo4));
                    return false;
                }
                if(StringUtils.isEmpty(mBinding.txtUserAddress2.getText().toString())){
                    mBinding.txtUserAddress2.requestFocus();
                    ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo5));
                    return false;
                }
                if(StringUtils.isEmpty(mBinding.txtAlias2.getText().toString())){
                    mBinding.txtAlias2.requestFocus();
                    ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo6));
                    return false;
                }
            }else{
                if(StringUtils.isEmpty(mBinding.txtUserAddress1.getText().toString())){
                    mBinding.txtUserAddress1.requestFocus();
                    ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo3));
                    return false;
                }
                if(StringUtils.isEmpty(mBinding.txtAlias1.getText().toString())){
                    mBinding.txtAlias1.requestFocus();
                    ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo4));
                    return false;
                }
                if(!StringUtils.isEmpty(mBinding.txtHomenumber2.getText().toString())){
                    if(!canUseGoldNum){
                        ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo7));
                        return false;
                    }else{
                        if(StringUtils.isEmpty(mBinding.txtUserAddress2.getText().toString())){
                            mBinding.txtUserAddress2.requestFocus();
                            ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo5));
                            return false;
                        }
                        if(StringUtils.isEmpty(mBinding.txtAlias2.getText().toString())){
                            mBinding.txtAlias2.requestFocus();
                            ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo6));
                            return false;
                        }
                    }
                }
            }
        }else{
            if(StringUtils.isEmpty(mBinding.txtUserAddress1.getText().toString())){
                mBinding.txtUserAddress1.requestFocus();
                ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo3));
                return false;
            }
            if(StringUtils.isEmpty(mBinding.txtAlias1.getText().toString())){
                mBinding.txtAlias1.requestFocus();
                ToastUtil.showToastAsLong(mContext,getString(R.string.msg_myinfo4));
                return false;
            }
        }
        return true;
    }
    private void requestDeleteHn(String hn) {

        // ProgressBar ??????
        if(! AddMyInfoActivity.this.isFinishing()) {
            showProgress();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.PREF_KEY_SMTADDR, hn);
        // Call ????????? ?????? ?????? ??????
        Call<RetrofitItemVo> requestCall = RetrofitUtil.createHeaderService1(Constant.REQ_MAIN_HEADER, token, device_uuid, RetrofitService.class).deleteHn(params);

        requestCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                // ??????????????? ????????? ????????? ??????
                // Progress ??????
                dismissProgress();
                if (response.isSuccessful()) {
                    // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                    RetrofitItemVo itemVo = response.body();
                        if(itemVo.getRspCode() == 200){
                            if ( itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 0) {

                                userData.setGoodNo(itemVo.getData().getGoodNo());
                                //????????? ??? - ?????? ??????
                                userData.setUserHN(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getHOME_NO()); // ?????????
                                userData.setUserHomeAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_BASE_ADDRESS()); // ????????????
                                userData.setUserDetailAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_DTL_ADDRESS()); // ????????????
                                userData.setUserName(itemVo.getData().getUserNm()); // ??????
                                userData.setUserTelephone1(itemVo.getData().getUserId()); // ?????? ??? ??????
                                userData.setUserAlias(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getALIAS()); // ??? / ??????
                                userData.setUserNormalTelephone1(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getUSER_TEL()); // ?????? ??? ??????
                                userData.setUserZipCode1(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(0).getZIP_CD()); // ?????? ????????????
                                if (itemVo.getData().getUSER_DTL_INFO_BY_HN().size() > 1) {
                                    userData.setUserHN2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getHOME_NO()); // ?????? ?????????
                                    userData.setUserCompanyAddress(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_BASE_ADDRESS()); // ????????????
                                    userData.setUserDetailAddress2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_DTL_ADDRESS()); // ????????????
                                    userData.setUserName2(itemVo.getData().getUserNm()); // ??????
                                    userData.setUserTelephone2(itemVo.getData().getUserId()); // ?????? ??? ??????
                                    userData.setUserAlias2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getALIAS()); // ??? / ??????
                                    userData.setUserNormalTelephone2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getUSER_TEL()); // ?????? ??? ??????
                                    userData.setUserZipCode2(itemVo.getData().getUSER_DTL_INFO_BY_HN().get(1).getZIP_CD()); // ?????? ????????????
                                } else {
                                    userData.setUserCompanyAddress("");
                                    userData.setUserDetailAddress2("");
                                    userData.setUserAlias2("");
                                    userData.setUserHN2("");
                                    userData.setUserName2("");
                                    userData.setUserZipCode2("");
                                }
                                // ?????? ????????? ??????
                                userDataManager.setUserData(userData);
                                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_myinfo8));
                                Intent intent = new Intent(AddMyInfoActivity.this, MainFragmentActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }

                        }else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                            ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                            ClearDataUtil.ClearUserData(mContext);
                            redirectActivityAllClear(LoginActivity.class);
                        }else{
                            ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                        }

                    // dismaiss Progrss
                    dismissProgress();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        // ??????????????? ?????? ?????? ????????? ??????
                        ToastUtil.showToastAsShort(mContext, jObjError.getString("alert_msg"));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                // ?????? ????????? ???????????? ???????????? ??????
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress ??????
                dismissProgress();

                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });

    }

    /**
     * [AlertDialog] ????????????
     */
    private void workSearchAddressAlert(String type) {
        whichAddress = type;
        AlertDialogUtil.showAddressSearchDialog(mContext, (isCheck, value1, value2, value3, value4) -> {
            if(CommonUtil.isNetworkConnected(mContext)) {
                // ???????????? ????????? ?????? ??????
                if(isCheck) {
                    // ????????? ?????? ?????? -> 1??? ??????????????? 50?????? ???????????? ???????????????.
                    requestSearchAddressDoro(value1, value2, value3, value4);
                } else {
                    // ????????? ?????? ??????
                    requestSearchAddressJibun(value1, value2, value3, value4);
                }
            } else {
                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
            }
        });
    }

    /**
     * [Retrofit2] ????????? ????????????
     */
    private void requestSearchAddressDoro(String sido, String sigungu, String roadname, String building_name) {
        // Progress ??????
        // ProgressBar ??????
        if(! AddMyInfoActivity.this.isFinishing()) {
            showProgress();
        }

        // Call ?????? ??????
        Call<RetrofitItemVo> addressSearchCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoinDoro(sido, sigungu, roadname, building_name);

        // Call ????????? ?????? ?????? ??????
        addressSearchCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Progress ??????
                    dismissProgress();

                    // ??????????????? ????????? ????????? ??????
                    if(response.isSuccessful()) {
                        // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                        RetrofitItemVo itemVo = response.body();

                        if(itemVo != null) {
                                if(itemVo.getRspCode() == 200) {
                                    // ????????? ??????
                                    List<RetrofitItemVo.RetrofitAddressVo> dataList = new ArrayList<>(itemVo.getData().getAddress_list());

                                    // ???????????? ?????? ?????? return
                                    if(dataList.size() == 0) {
                                        ToastUtil.showToastAsLong(mContext, getString(R.string.msg_search_null));
                                        return;
                                    }

                                    // ???????????? ??????
                                    showAddressSearchResultDialog(dataList);
                                } else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                    ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                    ClearDataUtil.ClearUserData(mContext);
                                    redirectActivityAllClear(LoginActivity.class);
                                } else {

                                    // ??????????????? ?????? ??????
                                    ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                                }

                        }
                    } else {
                        // ??????????????? ?????? ?????? ????????? ??????
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                // ?????? ????????? ???????????? ???????????? ??????
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress ??????
                dismissProgress();

                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });
    }

    /**
     * [Retrofit2] ????????? ????????????
     */
    private void requestSearchAddressJibun(String sido, String sigungu, String roadname, String building_name) {
        // Progress ??????
        // ProgressBar ??????
        if(! AddMyInfoActivity.this.isFinishing()) {
            showProgress();
        }

        // Call ?????? ??????
        Call<RetrofitItemVo> addressSearchCall = RetrofitUtil.createHeaderService4(Constant.REQ_MAIN_HEADER, device_uuid, RetrofitService.class).requestHomenumberMemberJoinJibun(sido, sigungu, roadname, building_name);

        // Call ????????? ?????? ?????? ??????
        addressSearchCall.enqueue(new Callback<RetrofitItemVo>() {
            @Override
            public void onResponse(@NotNull Call<RetrofitItemVo> call, @NotNull Response<RetrofitItemVo> response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Progress ??????
                    dismissProgress();

                    // ??????????????? ????????? ????????? ??????
                    if(response.isSuccessful()) {
                        // ???????????? ????????? ???????????? ItemVo??? ???????????????.
                        RetrofitItemVo itemVo = response.body();

                        if(itemVo != null) {
                                if(itemVo.getRspCode() == 200) {
                                    // ????????? ??????
                                    List<RetrofitItemVo.RetrofitAddressVo> dataList = new ArrayList<>(itemVo.getData().getAddress_list());

                                    // ???????????? ?????? ?????? return
                                    if(dataList.size() == 0) {
                                        ToastUtil.showToastAsLong(mContext, getString(R.string.msg_search_null));
                                        return;
                                    }

                                    // ???????????? ??????
                                    showAddressSearchResultDialog(dataList);
                                } else if(itemVo.getRspCode() == 401 && itemVo.getErrorCode().equals("1400")){
                                    ToastUtil.showToastAsLong(mContext, itemVo.getAlert_msg());
                                    ClearDataUtil.ClearUserData(mContext);
                                    redirectActivityAllClear(LoginActivity.class);
                                }else {
                                    // ??????????????? ?????? ??????
                                    ToastUtil.showToastAsShort(mContext, itemVo.getAlert_msg());
                                }
                        }
                    } else {
                        // ??????????????? ?????? ?????? ????????? ??????
                        ToastUtil.showToastAsShort(mContext, getString(R.string.msg_not_auth_internet));
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call<RetrofitItemVo> call, @NotNull Throwable t) {
                // ?????? ????????? ???????????? ???????????? ??????
                LogUtil.e("onFailure: " + t.getMessage());

                // Progress ??????
                dismissProgress();

                ToastUtil.showToastAsShort(mContext, getString(R.string.msg_error_server_call));
            }
        });
    }

    /**
     * ?????? ?????? ?????? ??????
     */
    private void showAddressSearchResultDialog(List<RetrofitItemVo.RetrofitAddressVo> dataList) {

        dismissDialog();

        // [?????? ????????? ??????] & [????????? ???]
        int listSize = 0, pageNumber = 0;

        // Adapter??? ??????????????? ????????? ????????? ?????????
        List<RetrofitItemVo.RetrofitAddressVo> adapterDataList = new ArrayList<>();

        /**
         * ????????? ????????? ?????????. ??????????????? ???????????? ?????? ????????? ????????? +1
         */
        if(dataList.size() != 0) {
            // ????????? ??????
            listSize = dataList.size();

            // ???
            pageNumber = (listSize / Constant.ARRAY_PAGE);

            // ?????????(????????????)
            int pagePlus = (listSize % Constant.ARRAY_PAGE);

            // ??????????????? ???????????? ?????? ???(????????????)??? +1
            if(pagePlus != 0)
                ++pageNumber;
        }

        /**
         * Adapter??? ????????? ????????? ???????????? ??????
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

            if(whichAddress.equals("1")){
                zipCode1 =value.getRoadnameZipCode();
                mBinding.txtUserAddress1.setText(value.getFullRoadnameAddr());
                mBinding.txtUserRef1.setText(value.addressRef);
            }else{
                zipCode2 =value.getRoadnameZipCode();
                mBinding.txtUserAddress2.setText(value.getFullRoadnameAddr());
                mBinding.txtUserRef2.setText(value.addressRef);
            }

            LogUtil.e("??????"+value.getRoadnameZipCode());
            LogUtil.e("??????"+value.getFullRoadnameAddr());


            // ?????? ?????? ??????
            AlertDialogUtil.dismissDialog();

            // AlertDialog ??????
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

            // ????????? ???????????? ????????? ?????? ??? ???????????? ???????????? ??????
            // ????????? for?????? ???????????? ?????? ???????????? ???????????? ???????????? ????????? ????????? ???????????? ??????????????? ??????
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
     * Dialog ??????
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

}
