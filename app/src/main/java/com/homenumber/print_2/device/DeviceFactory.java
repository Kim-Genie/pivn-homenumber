package com.homenumber.print_2.device;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Android 디바이스 정보
 * @author 나비이쁜이
 * @since 2019.08.13
 */
@SuppressLint("HardwareIds")
public class DeviceFactory {

    /**
     * UUID Static Value
     ************************************************************************************************************************************************/
    private static final String PREFS_FILE = "device_id.xml";
    private static final String PREFS_DEVICE_ID = "device_id";
    private static final String PREFS_ERROR = "9774d56d682e549c";
    private volatile static UUID uuid;

    public static void main(String[] args) {
        DeviceFactory deviceFactory = new DeviceFactory();
    }

    /**
     * Android 디바이스 고유 ID
     ************************************************************************************************************************************************/
    public static UUID getDeviceUUID(Context context) {
        if(uuid == null) {
            synchronized (DeviceFactory.class) {
                if(uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);

                    if(id != null) {
                        // 이전에 계산되어 있는 SharedPreferences 파일에 저장된 ID를 사용
                        uuid = UUID.fromString(id);
                    } else {
                        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                        // 안드로이드 ID가 고장 나지 않았다면 사용하고, 이 경우에는 deviceId가 다시 적용됩니다.
                        // 사용할 수 없는 경우를 제외하고는, 우리가 SharedPreferences 파일에 저장하는 임의의 숫자에 대해 재조정합니다.
                        try {
                            if(!PREFS_ERROR.equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes(StandardCharsets.UTF_8));
                            } else {
                                @SuppressLint("MissingPermission") final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes(StandardCharsets.UTF_8)) : UUID.randomUUID();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 값을 SharedPreferences 파일에 기록합니다.
                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).apply();
                    }
                }
            }
        }

        return uuid;
    }
}
