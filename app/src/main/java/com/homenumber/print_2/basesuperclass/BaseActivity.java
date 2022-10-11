package com.homenumber.print_2.basesuperclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.homenumber.print_2.alertdialog.LoadingProgressDialog;
import com.homenumber.print_2.device.DeviceFactory;
import com.homenumber.print_2.security.CryptUtil;
import com.homenumber.print_2.userdata.UserData;
import com.homenumber.print_2.userdata.UserDataManager;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


/**
 * 하위 Activity에서 사용할 Super Class
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Context
     */
    protected Context mContext;

    /**
     * Application User Local DataBase
     */
    protected UserDataManager userDataManager;
    protected UserData userData;

    /**
     * device_uuid
     */
    protected String device_uuid;
    protected String token;

    /**
     * Progress Dialog
     */
    protected LoadingProgressDialog progress;

    /**
     * Application Back Key 관련
     */
    private boolean isBackPressed = false;              // Back Key 눌렀는지 여부
    protected boolean isBackButtonNotice = true;        // Back Key 눌렀을 때 App 종료 문구 출력 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // Application 전역 정보 인터페이스
        mContext = this;

        // Application User Local DataBase
        userDataManager = new UserDataManager(getApplicationContext());
        userData = userDataManager.getUserData();

        // User Token
        token = userData.getUserToken();

        // ProgressBar
        progress = new LoadingProgressDialog(mContext);

        // Device UUID
        device_uuid = CryptUtil.hashSHA256(DeviceFactory.getDeviceUUID(getApplicationContext()).toString()).toUpperCase();
    }

    /**
     * Ui resource 초기화
     ************************************************************************************************************************************************/
    protected void uiInit() { }

    /**
     * ProgressBar Show
     ************************************************************************************************************************************************/
    public void showProgress() {
        progress.show();
    }

    /**
     * 프로그레스바 종료
     ************************************************************************************************************************************************/
    public void dismissProgress() {
        progress.dismiss();
    }

    /**
     * attachBaseContext -> 여기서는 XML에서 적용한 폰트를 context에 적용하는 부분입니다.
     ************************************************************************************************************************************************/
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }

    /**
     * Back Key 동작
     ************************************************************************************************************************************************/
    @Override
    public void onBackPressed() {
        if (!isBackButtonNotice || isBackPressed) {
            finish();
            return;
        }

        isBackPressed = true;
        ToastUtil.showToastAsShort(getApplicationContext(), getString(R.string.msg_back_button));

        new Handler().postDelayed(() -> isBackPressed = false, 2000);
    }

    /**
     * 기존에 stack에 쌓여있던 모든 Activity는 제거하고 새로운 Activity를 실행함.
     ************************************************************************************************************************************************/
    public void redirectActivityAllClear(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 현재 Activity를 종료하고 새로운 Activity를 start할 때 사용하며 새로운 Activity를 실행함.
     ************************************************************************************************************************************************/
    public void openActivity(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
        finish();
    }
}
