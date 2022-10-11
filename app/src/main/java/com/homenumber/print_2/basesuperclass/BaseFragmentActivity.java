package com.homenumber.print_2.basesuperclass;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.homenumber.print_2.adapter.viewpager.MainViewPagerAdapter;
import com.homenumber.print_2.alertdialog.LoadingProgressDialog;
import com.homenumber.print_2.device.DeviceFactory;
import com.homenumber.print_2.fragment.Fragment_ItemInfo;
import com.homenumber.print_2.fragment.Fragment_Keypad;
import com.homenumber.print_2.fragment.Fragment_Log;
import com.homenumber.print_2.fragment.Fragment_Setting;
import com.homenumber.print_2.security.CryptUtil;
import com.homenumber.print_2.userdata.UserData;
import com.homenumber.print_2.userdata.UserDataManager;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * 하위 FragmentActivity에서 사용할 Super Class
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    /**
     * FragmentActivity 전역 정보 인터페이스
     */
    protected Context mContext;

    /**
     * token & device_uuid
     */
    public String token;
    public String device_uuid;

    /**
     * acc_id
     */
    public String acc_id;

    /**
     * Adapter
     */
    public MainViewPagerAdapter viewPagerAdapter;

    /**
     * Application User Local DataBase
     */
    protected UserDataManager userDataManager;
    protected UserData userData;

    /**
     * 현재 화면에 보이는 활성화 상태인 Fragment
     */
    protected BaseFragment currentFragment;

    /**
     * Fragment
     */
    public Fragment_Keypad fragment1;
    public Fragment_ItemInfo fragment3;
    public Fragment_Setting fragment5;
    public Fragment_Log fragment2;

    /**
     * Progress Dialog
     */
    protected static LoadingProgressDialog progress;

    /**
     * 게시판 새로고침 액션인지 여부
     */
    protected boolean isRefreshCalled = false;
    /**
     * 히스토리 새로고침 액션인지 여부
     */
    public boolean ResetHistory = false;

    /**
     * Back Key 관련
     */
    protected boolean isBackButtonNotice = true;
    protected boolean isBackPressed = false;

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // Application 전역 정보 인터페이스
        mContext = this;

        // Application User Local DataBase
        userDataManager = new UserDataManager(mContext);

        // Application User Local DataBase
        userData = userDataManager.getUserData();

        // User Token
        token = getUserData().getUserToken();

        // user acc_id
        acc_id = getUserData().getUserAccid();

        // Progress Dialog
        progress = new LoadingProgressDialog(mContext);

        // Device UUID
        device_uuid = CryptUtil.hashSHA256(DeviceFactory.getDeviceUUID(getApplicationContext()).toString()).toUpperCase();
    }

    /**
     * UI 초기화
     */
    protected void uiInit() {}

    /**
     * Back Key 동작
     */
    @Override
    public void onBackPressed() {
        if (!currentFragment.getIsMainScreen()) {
            // 게시판 메인화면이 아닌 경우 메인 이동
            refreshFragment();
        } else {
            // 현재 화면이 메인 게시판인 경우 App 종료 안내 로직 진행
            if(isMainPage()) {
                if (!isBackButtonNotice || isBackPressed) {
                    finish();
                    return;
                }

                // "뒤로" 버튼을 한번 더 누르시면 종료됩니다.
                isBackPressed = true;
                ToastUtil.showToastAsShort(getApplicationContext(), getString(R.string.msg_back_button));

                new Handler().postDelayed(() -> isBackPressed = false, 2000);
            }
        }
    }

    /**
     * onDestroy() :: View 정리 및 비정상 종료를 대비한 finish() 호출
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 유저 데이터 최종 저장
        userData = userDataManager.getUserData();
        userDataManager.setUserData(userData);

        // 전부 종료
        finish();
    }

    /**
     * attachBaseContext
     * 1. 여기서는 XML에서 적용한 폰트를 context에 적용하는 부분입니다.
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }

    /**
     * 메인페이지인지의 여부(검색에 사용)
     */
    protected abstract boolean isMainPage();

    /**
     * 하위 Fragment에서 호출하는 현재 활성상태 Fragment
     */
    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    /**
     * 프로그레스바 시작
     */
    public static void showProgress() {
        progress.show();
    }

    /**
     * 프로그레스바 종료
     */
    public static void dismissProgress() {
        progress.dismiss();
    }

    /**
     * Get UserDataManager
     */
    public UserDataManager getUserDataManager() {
        return userDataManager;
    }

    /**
     * Get UserData
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * Fragment를 재시작
     */
    public void refreshFragment() {
        if (currentFragment != null) {
            currentFragment.resetPageInfo();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.detach(currentFragment);
            transaction.attach(currentFragment);
            transaction.commitAllowingStateLoss(); // Fragment에서는 commitAllowingStateLoss()
            //transaction.commit();                // Activity에서는 commit()
        }
    }
}
