package com.homenumber.print_2.activity.simpleactivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.homenumber.print_2.basesuperclass.BaseActivity;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityMyhomenumberQrBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * 추천하기화면
 */
@SuppressLint("Registered")
public class MyHomeNumberQRActivity extends BaseActivity  {

    /**
     * Databinding
     */
    private ActivityMyhomenumberQrBinding mBinding;

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_myhomenumber_qr);
        mBinding.setActivity(this);
        uiInit();
    }

    /**
     * uiInit
     */
    @Override
    protected void uiInit() {
        super.uiInit();

        isBackButtonNotice = false;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        String homenumber = "";
        if(this.getIntent() != null ) {
            homenumber = (String) this.getIntent().getSerializableExtra("homenumber");
        }

        mBinding.txt04.setText(homenumber);
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(homenumber, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            mBinding.qrcode.setImageBitmap(bitmap);
        }catch (Exception e){}

        // 뒤로가기 여부

    }


    /**
     * onClick_back
     */
    public void workFinish() {
        finish();
    }


}

