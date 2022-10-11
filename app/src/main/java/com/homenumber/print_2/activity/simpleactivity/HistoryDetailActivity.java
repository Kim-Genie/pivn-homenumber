package com.homenumber.print_2.activity.simpleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityHistoryDetailBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


public class HistoryDetailActivity extends BaseActivity {
    /**
     * Databinding
     */
    private ActivityHistoryDetailBinding mBinding;
    ArrayList<String> detailList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_history_detail);
        mBinding.setActivity(this);
        Intent intent = getIntent();
        ArrayList<String> list = (ArrayList<String>) intent.getSerializableExtra("item");
        detailList = list;
        uiInit();
    }

    @Override
    protected void uiInit() {
        super.uiInit();

        isBackButtonNotice = false;
        mBinding.layoutHeader.txtFragmentHeader.setText(getString(R.string.str_item_info));
        mBinding.txtItemName.setText(detailList.get(0));
        mBinding.etItemPrice.setText(detailList.get(1));
        mBinding.txtItemHow.setText(StringUtils.equals("CASH" , detailList.get(2))? "선불" : "착불");

        if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_1))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_11);
        } else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_2))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_22);
        } else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_3))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_33);
        } else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_4))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_44);
        } else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_5))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_55);
        } else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_6))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_66);
        } else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_7))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_77);
        }else if (StringUtils.equals(mBinding.txtItemName.getText(), getString(R.string.str_item_name_8))) {
            mBinding.etItemNamePlus.setText(R.string.str_item_name_88);
        }
        if(!StringUtils.isEmpty(detailList.get(3))){
            mBinding.etItemNamePlus.setText(detailList.get(3));
        }

    }

    /**
     * 종료
     */
    public void onClick(View view) {
        finish();
    }

}