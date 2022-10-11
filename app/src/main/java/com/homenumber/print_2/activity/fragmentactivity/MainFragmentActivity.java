package com.homenumber.print_2.activity.fragmentactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.homenumber.print_2.Constant;
import com.homenumber.print_2.adapter.viewpager.MainViewPagerAdapter;
import com.homenumber.print_2.basesuperclass.BaseFragment;
import com.homenumber.print_2.basesuperclass.BaseFragmentActivity;
import com.homenumber.print_2.device.DeviceFactory;
import com.homenumber.print_2.fragment.Fragment_ItemInfo;
import com.homenumber.print_2.fragment.Fragment_Keypad;
import com.homenumber.print_2.fragment.Fragment_Log;
import com.homenumber.print_2.fragment.Fragment_Setting;
import com.homenumber.print_2.security.CryptUtil;
import com.homenumber.print_2.util.CommonUtil;
import com.homenumber.print_2.util.RequestCodeUtil;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.ActivityMainfragmentBinding;

/**
 * Fragment를 담아두는 FragmentActivity
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class MainFragmentActivity extends BaseFragmentActivity {

    /**
     * token & uuid
     ************************************************************************************************************************************************/
    public String token;
    public String device_uuid;
    String deviceVersion;
    String storeVersion;
//    private BackgroundThread backgroundThread;
    /**
     * Databinding
     */
    public ActivityMainfragmentBinding mBinding;
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfragment);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mainfragment);
        mBinding.setActivity(this);


        uiInit();
    }

    /**
     * View Resource Setting
     */
    @Override
    protected void uiInit() {
        super.uiInit();

        // 뒤로가기 버튼 사용 조건
        isBackButtonNotice = true;

        // User Token
        token = getUserData().getUserToken();

        // 디바이스 UUID
        device_uuid = CryptUtil.hashSHA256(DeviceFactory.getDeviceUUID(getApplicationContext()).toString()).toUpperCase();

        // ViewPager setAdapter
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mBinding.viewPagerMain.setAdapter(viewPagerAdapter);
        mBinding.viewPagerMain.freeze();
        // ViewPager Option
        mBinding.viewPagerMain.setOffscreenPageLimit(viewPagerAdapter.getCount() - 1);
        mBinding.viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 페이지가 변경될때마다 현재 활성 Fragment 설정함.
                currentFragment = (BaseFragment) viewPagerAdapter.getItem(position);

                switch (position) {
                    case 0:
                        mBinding.linearlayout1.setBackgroundResource(R.color.C_FFFFFF);
                        mBinding.linearlayout2.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout3.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout4.setBackgroundResource(R.color.C_EFEFEF);
                        break;

                    case 1:
                        mBinding.linearlayout1.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout2.setBackgroundResource(R.color.C_FFFFFF);
                        mBinding.linearlayout3.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout4.setBackgroundResource(R.color.C_EFEFEF);
                        break;

                    case 2:
                        mBinding.linearlayout1.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout2.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout3.setBackgroundResource(R.color.C_FFFFFF);
                        mBinding.linearlayout4.setBackgroundResource(R.color.C_EFEFEF);
                        break;

                    case 3:
                        mBinding.linearlayout1.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout2.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout3.setBackgroundResource(R.color.C_EFEFEF);
                        mBinding.linearlayout4.setBackgroundResource(R.color.C_FFFFFF);
                        break;
                }
                if(2 == position && ResetHistory){
                    ResetHistory = false;
                    fragment2.CheckRefresh();
                }else if(isRefreshCalled){
                    // refresh를 요청받은 경우 조건을 초기화하고 fragment를 refresh합니다.
                    isRefreshCalled = false;
                    refreshFragment();

                } else {
                    currentFragment.startRequest();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });

        // current Fragment
        currentFragment = (BaseFragment) viewPagerAdapter.getItem(mBinding.viewPagerMain.getCurrentItem());

        // Fragment를 사용하도록 설정
        fragment1 = (Fragment_Keypad) viewPagerAdapter.getItem(0);
        fragment3 = (Fragment_ItemInfo) viewPagerAdapter.getItem(1);
        fragment5 = (Fragment_Setting) viewPagerAdapter.getItem(3);
        fragment2 = (Fragment_Log) viewPagerAdapter.getItem(2);

//        backgroundThread = new BackgroundThread();
//        backgroundThread.start();
    }

    /**
     * onActivityResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * Activity.RESULT_OK -> 키패드로 이동(키패드 작성)
         * Activity.RESULT_CANCELED -> 주소록 데이터를 재요청
         */
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // 주소록에서 홈넘버를 받아와서 키패드로 뿌려주는 작업
                case RequestCodeUtil.ActivityReqCode.REQ_ADDRESS_BOOK_RETURN_KEYPAD:
                    // Fragment 키패드로 이동
                    mBinding.viewPagerMain.setCurrentItem(viewPagerAdapter.getPagePosition(Fragment_Keypad.class));

                    // 키패드 작성
                    if(data != null)
                        fragment1.mBinding.txtHomenumber.setText(data.getStringExtra(Constant.KEYPAD_HOMENUMBER));

                    break;
            }
        } else if(resultCode == Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case RequestCodeUtil.ActivityReqCode.REQ_ADDRESS_BOOK_RETURN_KEYPAD:
                    if(CommonUtil.isNetworkConnected(getApplicationContext())) {
                        // Fragment 키패드로 이동
                        mBinding.viewPagerMain.setCurrentItem(viewPagerAdapter.getPagePosition(Fragment_ItemInfo.class), false);

                        // 재요청
                        //fragment3.requestAddressList(false);
                    } else {
                        ToastUtil.showToastAsShort(getApplicationContext(), getString(R.string.msg_not_auth_internet));
                    }

                    break;
            }
        }
    }

    /**
     * Back Key 눌렀을 때 잔액 게시판(첫 Fragment)이 아닌 경우, 잔액 게시판(첫 Fragment)으로 이동함.
     */
    @Override
    protected boolean isMainPage() {
        if(mBinding.viewPagerMain.getCurrentItem() > 0) {
            mBinding.viewPagerMain.setCurrentItem(0);
            return false;
        }

        return true;
    }

    /**
     * onClick_Tab
     */
    public void onClick_Tab(int caseNumber) {
        switch (caseNumber) {
            case 1:
                mBinding.viewPagerMain.setCurrentItem(viewPagerAdapter.getPagePosition(Fragment_Keypad.class));
                break;

            case 2:
                mBinding.viewPagerMain.setCurrentItem(viewPagerAdapter.getPagePosition(Fragment_ItemInfo.class));
                break;

            case 3:
                mBinding.viewPagerMain.setCurrentItem(viewPagerAdapter.getPagePosition(Fragment_Log.class));
                break;

            case 4:
                mBinding.viewPagerMain.setCurrentItem(viewPagerAdapter.getPagePosition(Fragment_Setting.class));
                break;
        }
    }


//    public class BackgroundThread extends Thread{
//        @Override
//        public void run(){
//            // 앱 버전 정보
//            storeVersion = MarketVersionCheckerUtil.getMarketVersion(mContext.getPackageName());
//            try {
//                deviceVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            deviceVersionCheckHandler.sendMessage(deviceVersionCheckHandler.obtainMessage());
//
//
//        }
//    }

//    private final DeviceVersionCheckHandler deviceVersionCheckHandler = new DeviceVersionCheckHandler(this);
//
//    private static class DeviceVersionCheckHandler extends Handler {
//        private final WeakReference<MainFragmentActivity> mainFragmentActivityWeakReference;
//         DeviceVersionCheckHandler(MainFragmentActivity mainFragmentActivity){
//            mainFragmentActivityWeakReference = new WeakReference<MainFragmentActivity>(mainFragmentActivity);
//        }
//        @Override
//        public void handleMessage(Message msg){
//            MainFragmentActivity activity = mainFragmentActivityWeakReference.get();
//            if(activity != null){
//                activity.handleMessage(msg);
//            }
//        }
//    }

//    private void handleMessage(Message msg){
//            if(storeVersion.compareTo(deviceVersion) > 0){
//                AlertDialog.Builder alertDialogBuilder =
//                        new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
////            new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light)
//                alertDialogBuilder.setTitle("업데이트")
//                        .setMessage("새로운 버전이 있습니다.\n보다 나은 사용을 위해 업데이트 해 주세요.")
//                        .setCancelable(false)
//                        .setPositiveButton("업데이트", (dialog, which) -> {
//                            // 구글플레이 업데이트 링크
//                            Intent intent = new Intent(Intent.ACTION_VIEW); intent.setData(Uri.parse("market://details?id=" + getPackageName())); startActivity(intent);
//                        });
//                AlertDialog alertDialog = alertDialogBuilder.create();
////            alertDialog.setCanceledOnTouchOutside(true);
//                alertDialog.show();
//            }
//
//    }



}
