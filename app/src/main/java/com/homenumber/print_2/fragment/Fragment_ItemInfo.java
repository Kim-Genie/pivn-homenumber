package com.homenumber.print_2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.alertdialog.AlertDialogUtil;
import com.homenumber.print_2.basesuperclass.BaseFragment;
import com.homenumber.print_2.util.LogUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.FragmentIteminfoBinding;

import org.apache.commons.lang3.StringUtils;

/**
 * 물품정보 Fragment
 * @author 나비이쁜이
 * @since 2019.09.03
 */
public class Fragment_ItemInfo extends BaseFragment {

    /**
     * Databinding
     */
    public FragmentIteminfoBinding mBinding;

    /**
     * 변화값
     */
    public String item_value;



    boolean checkAccept1Flag = true;
    boolean checkAccept2Flag = true;
    /**
     * 이미지
     */
    public String imgName = "";
    /**
     * Fragment uiInit
     */
    @Override
    protected View uiInit(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // set Databinding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_iteminfo, parent, false);
        mBinding.setFragmentIteminfo(this);

        // Fragment 초기화 설정
        resetPageInfo();

        // Fragment 화면에 들어올때
        if(isAdded() && !isStartedRequest && activity.getCurrentFragment() == this) {
            startRequest();
        }

        mBinding.checkboxAccept1.setChecked(false);
        mBinding.checkboxAccept2.setChecked(false);


        mBinding.checkboxAccept2.setOnClickListener(v -> {
            if(checkAccept1Flag) {
                mBinding.scrollAccept1.setVisibility(View.VISIBLE);
                checkAccept1Flag = false;
            }else{
                mBinding.scrollAccept1.setVisibility(View.GONE);
                checkAccept1Flag = true;

            }

        });

        mBinding.checkboxAccept1.setOnClickListener(v -> {
            if(checkAccept2Flag) {
                mBinding.scrollAccept2.setVisibility(View.VISIBLE);
                checkAccept2Flag = false;
            }else{
                mBinding.scrollAccept2.setVisibility(View.GONE);
                checkAccept2Flag = true;

            }

        });

        // 택배이용 약관동의
        // 유의사항 확인 및 동의
        // 개인정보 수집 및 이용 동의

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
    @Override
    public void startRequest() {
        if(!isStartedRequest) {
            isStartedRequest = true;

            // 타이틀 작성
            mBinding.layoutHeader.txtFragmentHeader.setText(getString(R.string.str_item_info));

            // 물품 정보 불러오기
            mBinding.txtItemName.setText(mainFragmentActivity.getUserData().getItemName());         // 품목

            mBinding.etItemNamePlus.setText(mainFragmentActivity.getUserData().getItemPlusInfo());  // 물품명
            mBinding.etItemNumber.setText(mainFragmentActivity.getUserData().getItemNumber());      // 수량
            mBinding.etItemPrice.setText(mainFragmentActivity.getUserData().getItemPrice()); //가격
            mBinding.etItemKg.setText(mainFragmentActivity.getUserData().getItemKg());
            mBinding.etItemMemo.setText(mainFragmentActivity.getUserData().getItemMemo());
            mBinding.imgviewWarning.setImageResource(mainFragmentActivity.getUserData().getResourceImage()); //이미지

            if (StringUtils.equals(mainFragmentActivity.getUserData().getItemHow(),"CASH")) {
                mBinding.txtItemHow.setText("선불");
            } else {
                mBinding.txtItemHow.setText("착불");
            }

            mBinding.checkboxAccept1.setChecked(mainFragmentActivity.getUserData().isAccept_gs_1());
            mBinding.checkboxAccept2.setChecked(mainFragmentActivity.getUserData().isAccept_gs_2());
            mBinding.checkboxAccept3.setChecked(mainFragmentActivity.getUserData().isAccept_gs_3());


            // 데이터가 존재하지 않는 경우
            if(StringUtils.isEmpty(mainFragmentActivity.getUserData().getItemHow())) {
                mBinding.txtItemHow.setText("선불");
            }

            if(StringUtils.isEmpty(mainFragmentActivity.getUserData().getItemName())) {
                mBinding.txtItemName.setText("품목선택");
            }
            if(mBinding.txtItemName.getText().toString().equals("품목선택")){
                mBinding.checkboxAccept2.setEnabled(false);
            }

            mBinding.txtItemName.setOnClickListener(v -> AlertDialogUtil.showItemName(mContext,0,value -> mBinding.txtItemName.setText(value)
                    ,value -> {
                        // 의류
                        if (StringUtils.equals(value, getString(R.string.str_item_name_1))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_cloth);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_11);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_cloth);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="cloth";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_2))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_document);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_22);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_document);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="document";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_3))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_Appliances);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_33);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_applianes);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="applianes";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_4))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_fruit);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_44);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_fruit);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="fruit";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_5))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_grain);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_55);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_grain);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="grain";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_6))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_medicine);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_66);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_medicine);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="medicine";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_7))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_food);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_77);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_food);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="food";
                        } else if (StringUtils.equals(value, getString(R.string.str_item_name_8))) {
                            mBinding.checkboxAccept2.setText(R.string.msg_checkbox_books);
                            mBinding.etItemNamePlus.setText(R.string.str_item_name_88);
                            mBinding.imgviewWarning.setImageResource(R.drawable.img_warning_book);
                            mBinding.checkboxAccept2.setChecked(false);
                            imgName ="book";

                        }
                        mBinding.checkboxAccept2.setEnabled(true);


                    }));
            // 결제 방법 선택 팝업
            mBinding.txtItemHow.setOnClickListener(v -> AlertDialogUtil.showItemHow(mContext, 0, value -> mBinding.txtItemHow.setText(value)));
            // mBinding.etItemNamePlus.setText(value)


            // 입력값 완료 버튼 이벤트
            mBinding.etItemMessage.setOnEditorActionListener((v, actionId, event) -> false);

            // 입력값 변화
            mBinding.etItemMessage.setOnClickListener(v -> AlertDialogUtil.showItemMessageDialog(mContext, 0, value -> {
                if(StringUtils.isEmpty(value)) {
                    return;
                }

                if(StringUtils.equals(value, getString(R.string.msg_item_message_6))) {
                    // 사용자에게 Toast
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_message));

                    // 입력값 초기화
                    mBinding.etItemMessage.setText(null);

                    // 터치 true
                    mBinding.etItemMessage.setFocusableInTouchMode(true);
                } else {
                    // 키보드 내리기
                    InputMethodManager inputMethodManager_back = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager_back.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    // 터치 false
                    mBinding.etItemMessage.setFocusableInTouchMode(false);

                    // 포커스 false
                    mBinding.etItemMessage.setFocusable(false);

                    // 배송 메세지 변경
                    mBinding.etItemMessage.setText(value);
                }
            }));

        }
    }

    /**
     * 물품 정보 저장
     */
    public void onClick(View view) {
        // 상품명이 없는 경우

        LogUtil.e("이름="+ mBinding.txtItemName.getText().toString());

        if(mBinding.txtItemName.getText().toString().equals("품목선택")) {
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_name));
            return;
        }

        if(mBinding.etItemPrice.getText().toString().equals("") || mBinding.etItemPrice.getText().toString().equals("0")) {
            mBinding.etItemPrice.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_price));
            return;
        }

        // 상품추가정보가 없는 경우
        if(StringUtils.isEmpty(mBinding.etItemNamePlus.getText().toString())) {
            mBinding.etItemNamePlus.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_name_plus2));
            return;
        }

        // 결제방법 선택
        if(StringUtils.isEmpty(mBinding.txtItemHow.getText().toString())) {
            mBinding.txtItemHow.requestFocus();
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_name_plus3));
            return;
        }

        // 면책 동의 여부
        if(!mBinding.checkboxAccept2.isChecked()) {
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_name_plus4));
            return;
        }

        // 이용약관 여부
        if(!mBinding.checkboxAccept1.isChecked()) {
            ToastUtil.showToastAsShort(mContext, getString(R.string.msg_item_name_plus5));
            return;
        }

//        // 개인정보 처리방침 여부
//        if(!mBinding.checkboxAccept2.isChecked()) {
//            AlertDialogUtil.showSingleDialog(mContext, "개인정보 처리방침에 동의해주세요.", (dialog, which) -> AlertDialogUtil.dismissDialog());
//            return;
//        }
//
//        // 개인정보 취급위탁 여부
//        if(!mBinding.checkboxAccept3.isChecked()) {
//            AlertDialogUtil.showSingleDialog(mContext, "개인정보 취급위탁에 동의해주세요.", (dialog, which) -> AlertDialogUtil.dismissDialog());
//            return;
//        }


        // 키보드 내리기
        InputMethodManager inputMethodManager_back = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager_back.hideSoftInputFromWindow(view.getWindowToken(), 0);

        // 물품 정보 저장
        AlertDialogUtil.showSingleDialog(mContext, getString(R.string.msg_item_name_plus6), (dialog, which) -> {
            /**
             * 물품 정보 저장
             */
            mainFragmentActivity.getUserData().setItemName(mBinding.txtItemName.getText().toString());
            mainFragmentActivity.getUserData().setItemPlusInfo(mBinding.etItemNamePlus.getText().toString());
            mainFragmentActivity.getUserData().setItemPrice(mBinding.etItemPrice.getText().toString());
            mainFragmentActivity.getUserData().setItemNumber(mBinding.etItemNumber.getText().toString());
            if(!StringUtils.equals(imgName ,"")){
                int resId = getResources().getIdentifier("img_warning_"+imgName,"drawable",getContext().getPackageName());
                mainFragmentActivity.getUserData().setResourceImage(resId);
            }

            if (StringUtils.equals(mBinding.txtItemHow.getText().toString(),"선불")) {
                mainFragmentActivity.getUserData().setItemHow(Constant.PAYMENT_CASH);
            } else if (StringUtils.equals(mBinding.txtItemHow.getText().toString(),"착불")) {
                mainFragmentActivity.getUserData().setItemHow(Constant.PAYMENT_CASH_LATER);
//            } else if (mBinding.txtItemHow.toString() == "신용") {
//                mainFragmentActivity.getUserData().setItemHow(Constant.PAYMENT_CREDIT);
            } else {
                mainFragmentActivity.getUserData().setItemHow(Constant.PAYMENT_CASH);
            }
            mainFragmentActivity.getUserData().setAccept_gs_1(mBinding.checkboxAccept1.isChecked());
            mainFragmentActivity.getUserData().setAccept_gs_2(mBinding.checkboxAccept2.isChecked());
            mainFragmentActivity.getUserData().setAccept_gs_3(mBinding.checkboxAccept3.isChecked());

            mainFragmentActivity.getUserDataManager().setUserData(mainFragmentActivity.getUserData());

            AlertDialogUtil.dismissDialog();
        });
    }
}