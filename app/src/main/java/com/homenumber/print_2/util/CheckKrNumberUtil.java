package com.homenumber.print_2.util;

import android.Manifest;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.homenumber.print_2.permission.PermissionUtil;

public class CheckKrNumberUtil {

        public static String CheckKrNumber(AppCompatActivity activity, Context context){

            TelephonyManager mTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyManager == null) {
                ToastUtil.showToastAsShort(context, "휴대폰번호를 가져올 수 없습니다.");
                return "none";
            } else {
                try {
                    int simState = mTelephonyManager.getSimState();
                    switch (simState) {
                        // 유심 상태를 알 수 없는 경우
                        case TelephonyManager.SIM_STATE_UNKNOWN:
                        case TelephonyManager.SIM_STATE_ABSENT:
                        case TelephonyManager.SIM_STATE_PERM_DISABLED:
                        case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                        case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                            ToastUtil.showToastAsShort(context, "단말기의 유심이 존재하지 않거나 오류가 있는 경우, 앱을 사용할 수 없습니다.");
                            return "3";

                        default:
                            // 권한 등록이 완료된 경우
                            boolean isPermission = PermissionUtil.checkAndRequestPermission(activity, RequestCodeUtil.PermissionCode.REQ_PERMISSION_ALL, Manifest.permission.READ_PHONE_STATE);
                            if(isPermission){
                                String telNumber = mTelephonyManager.getLine1Number();
                                boolean krNum = telNumber.contains("+82");
                                if(krNum){
                                    return "0";
                                }else{
                                    return "1";
                                }
                            }
                    }
                } catch (Exception e) {
                    ToastUtil.showToastAsShort(context, "단말기의 정보를 가져오는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                    Log.e("simCheck", "Exception: " + e.toString());
                }
            }

            return "3";
        }

    public static String GetNumber(AppCompatActivity activity, Context context){

        TelephonyManager mTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager == null) {
            ToastUtil.showToastAsShort(context, "휴대폰번호를 가져올 수 없습니다.");
            return "none";
        } else {
            try {
                int simState = mTelephonyManager.getSimState();
                switch (simState) {
                    // 유심 상태를 알 수 없는 경우
                    case TelephonyManager.SIM_STATE_UNKNOWN:
                    case TelephonyManager.SIM_STATE_ABSENT:
                    case TelephonyManager.SIM_STATE_PERM_DISABLED:
                    case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                    case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                        ToastUtil.showToastAsShort(context, "단말기의 유심이 존재하지 않거나 오류가 있는 경우, 앱을 사용할 수 없습니다.");
                        return "none";

                    default:
                        // 권한 등록이 완료된 경우
                        boolean isPermission = PermissionUtil.checkAndRequestPermission(activity, RequestCodeUtil.PermissionCode.REQ_PERMISSION_ALL, Manifest.permission.READ_PHONE_STATE);
                        if(isPermission){
                            String telNumber = mTelephonyManager.getLine1Number();
                            return telNumber;
                        }
                }
            } catch (Exception e) {
                ToastUtil.showToastAsShort(context, "단말기의 정보를 가져오는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                Log.e("simCheck", "Exception: " + e.toString());
            }
        }

        return "none";
    }
}
