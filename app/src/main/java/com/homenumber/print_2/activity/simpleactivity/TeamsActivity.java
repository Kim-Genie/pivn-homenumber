package com.homenumber.print_2.activity.simpleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityTeamsBinding;

/**
 * MemberJoinActivity
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class TeamsActivity extends BaseActivity {

    /**
     * Databinding
     */
    private ActivityTeamsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_teams);

        mBinding.setActivity(this);
    }

    public void onClick(int casenumber) {
        switch (casenumber) {
            case 0:
                finish();
                break;

            case 3:
                if (mBinding.checkboxAcceptAll.isChecked() == false) {
                    mBinding.checkboxAccept1.setChecked(false);
                    mBinding.checkboxAccept2.setChecked(false);
                    mBinding.checkboxAccept3.setChecked(false);
                    mBinding.checkboxAccept4.setChecked(false);
                    mBinding.checkboxAccept5.setChecked(false);
                } else {
                    mBinding.checkboxAccept1.setChecked(true);
                    mBinding.checkboxAccept2.setChecked(true);
                    mBinding.checkboxAccept3.setChecked(true);
                    mBinding.checkboxAccept4.setChecked(true);
                    mBinding.checkboxAccept5.setChecked(true);
                }

                break;
            case 4:
                if (mBinding.checkboxAccept2.isChecked() == true &&
                        mBinding.checkboxAccept3.isChecked() == true &&
                        mBinding.checkboxAccept4.isChecked() == true &&
                        mBinding.checkboxAccept5.isChecked() == true) {

                    mBinding.checkboxAcceptAll.setChecked(true);

                }
                if (mBinding.checkboxAccept1.isChecked() == false ||
                        mBinding.checkboxAccept2.isChecked() == false ||
                        mBinding.checkboxAccept3.isChecked() == false ||
                        mBinding.checkboxAccept4.isChecked() == false ||
                        mBinding.checkboxAccept5.isChecked() == false){
                    mBinding.checkboxAcceptAll.setChecked(false);
                }

                break;
            case 5:

                if (mBinding.checkboxAccept1.isChecked() == true &&
                        mBinding.checkboxAccept3.isChecked() == true &&
                        mBinding.checkboxAccept4.isChecked() == true &&
                        mBinding.checkboxAccept5.isChecked() == true) {

                    mBinding.checkboxAcceptAll.setChecked(true);

                }
                if (mBinding.checkboxAccept1.isChecked() == false ||
                        mBinding.checkboxAccept2.isChecked() == false ||
                        mBinding.checkboxAccept3.isChecked() == false ||
                        mBinding.checkboxAccept4.isChecked() == false ||
                        mBinding.checkboxAccept5.isChecked() == false){
                    mBinding.checkboxAcceptAll.setChecked(false);
                }


                break;
            case 6:
                if (mBinding.checkboxAccept1.isChecked() == true &&
                        mBinding.checkboxAccept2.isChecked() == true &&
                        mBinding.checkboxAccept4.isChecked() == true &&
                        mBinding.checkboxAccept5.isChecked() == true) {

                    mBinding.checkboxAcceptAll.setChecked(true);

                }
                if (mBinding.checkboxAccept1.isChecked() == false ||
                        mBinding.checkboxAccept2.isChecked() == false ||
                        mBinding.checkboxAccept3.isChecked() == false ||
                        mBinding.checkboxAccept4.isChecked() == false ||
                        mBinding.checkboxAccept5.isChecked() == false){
                    mBinding.checkboxAcceptAll.setChecked(false);
                }

                break;
            case 7:
                if (mBinding.checkboxAccept1.isChecked() == true &&
                        mBinding.checkboxAccept2.isChecked() == true &&
                        mBinding.checkboxAccept3.isChecked() == true &&
                        mBinding.checkboxAccept5.isChecked() == true) {

                    mBinding.checkboxAcceptAll.setChecked(true);

                }
                if (mBinding.checkboxAccept1.isChecked() == false ||
                        mBinding.checkboxAccept2.isChecked() == false ||
                        mBinding.checkboxAccept3.isChecked() == false ||
                        mBinding.checkboxAccept4.isChecked() == false ||
                        mBinding.checkboxAccept5.isChecked() == false){
                    mBinding.checkboxAcceptAll.setChecked(false);
                }

                break;
            case 8:
                if (mBinding.checkboxAccept1.isChecked() == true &&
                        mBinding.checkboxAccept2.isChecked() == true &&
                        mBinding.checkboxAccept3.isChecked() == true &&
                        mBinding.checkboxAccept4.isChecked() == true) {

                    mBinding.checkboxAcceptAll.setChecked(true);

                }
                if (mBinding.checkboxAccept1.isChecked() == false ||
                        mBinding.checkboxAccept2.isChecked() == false ||
                        mBinding.checkboxAccept3.isChecked() == false ||
                        mBinding.checkboxAccept4.isChecked() == false ||
                        mBinding.checkboxAccept5.isChecked() == false){
                    mBinding.checkboxAcceptAll.setChecked(false);
                }

                break;

            case 9:

                if (mBinding.scrollAccept1.getVisibility() == View.GONE) {
                    mBinding.scrollAccept1.setVisibility(View.VISIBLE);
                } else {
                    mBinding.scrollAccept1.setVisibility(View.GONE);
                }

                break;

            case 10:
                if (mBinding.scrollAccept2.getVisibility() == View.VISIBLE) {
                    mBinding.scrollAccept2.setVisibility(View.GONE);
                } else {
                    mBinding.scrollAccept2.setVisibility(View.VISIBLE);
                }

                break;
            case 11:

                if (mBinding.scrollAccept3.getVisibility() == View.VISIBLE) {
                    mBinding.scrollAccept3.setVisibility(View.GONE);
                } else {
                    mBinding.scrollAccept3.setVisibility(View.VISIBLE);
                }

                break;
            case 12:

                if (mBinding.scrollAccept4.getVisibility() == View.VISIBLE) {
                    mBinding.scrollAccept4.setVisibility(View.GONE);
                } else {
                    mBinding.scrollAccept4.setVisibility(View.VISIBLE);
                }

                break;

            case 13:
                // 홈넘버 서비스 이용약관
                if(!mBinding.checkboxAccept1.isChecked()) {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pls_accept1));
                    return;
                }
                // 개인정보 처리방침
                if(!mBinding.checkboxAccept2.isChecked()) {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pls_accept2));
                    return;
                }
                // HND 이용약관
                if(!mBinding.checkboxAccept3.isChecked()) {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pls_accept3));
                    return;
                }
                // 개인정보 수집 및 이용동의
                if(!mBinding.checkboxAccept4.isChecked()) {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pls_accept4));
                    return;
                }
                //  만 14세 이상임을 확인합니다
                if(!mBinding.checkboxAccept5.isChecked()) {
                    ToastUtil.showToastAsShort(mContext, getString(R.string.msg_pls_accept5));
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), MemberJoinActivity.class);
                startActivity(intent);
                break;

        }
    }


}
