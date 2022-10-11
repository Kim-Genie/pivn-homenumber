package com.homenumber.print_2.activity.simpleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.homenumber.print_2.util.AddressUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityAddressBinding;

import org.apache.commons.lang3.StringUtils;

public class SearchAddress extends AppCompatActivity {

    /**
     * Databinding
     */
    private ActivityAddressBinding mBinding;
    private Handler handler;
    String message ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        mBinding.setActivity(this);

        mBinding.layoutHeader.txtFragmentHeader.setText("주소찾기");


        Intent intent = getIntent();
        message = intent.getStringExtra("broadcastName");

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }
    public void init_webView() {
        // WebView 설정

        // JavaScript 허용
        mBinding.webview.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        mBinding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        mBinding.webview.addJavascriptInterface(new AndroidBridge(), "TestApp");

        // web client 를 chrome 으로 설정
        mBinding.webview.setWebChromeClient(new WebChromeClient());

        // webview url load. php 파일 주소
        mBinding.webview.loadUrl("http://203.245.41.111/daumapi.php");

    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3, final String arg4,final String arg5,final String arg6,final String arg7,final String arg8) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                    mBinding.result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    Intent intent = new Intent(message);
                    intent.putExtra("zonecode", arg1);
                    String sido = AddressUtil.sido(arg2);
                    intent.putExtra("roadaddress", sido);
                    intent.putExtra("building", arg3);
                    intent.putExtra("jibun", arg4);
                    String bcodeNo = AddressUtil.bcodeNo(arg7);
                    String[] array = arg8.substring(arg8.lastIndexOf(" ")+1).split("-");
                    //건물본번 및 건물부번
                    String buildCode1 ="";
                    String buildCode2 ="";
                    if(array.length == 2 ){
                        buildCode1 = StringUtils.leftPad(array[0], 5, '0');
                        buildCode2 = StringUtils.leftPad(array[1], 5, '0');
                    }else if(array.length == 1){
                        buildCode1 = StringUtils.leftPad(array[0], 5, '0');
                        buildCode2 = "00000";
                    }
                    intent.putExtra("subAddress", arg5+arg6+bcodeNo +"0" +buildCode1 +buildCode2);
                    sendBroadcast(intent);
//                    init_webView();
                    finish();

                }
            });
        }
    }
}

