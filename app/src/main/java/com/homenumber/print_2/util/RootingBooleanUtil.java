package com.homenumber.print_2.util;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 루팅 여부 검사
 * @author 나비이쁜이
 * @since 2019.04.30
 */
public class RootingBooleanUtil {

    public static void main(String[] args) {
        RootingBooleanUtil rootingBooleanUtil = new RootingBooleanUtil();
    }

    private static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "";
    private static final String ROOTING_PATH_1 = "/system/bin/su";
    private static final String ROOTING_PATH_2 = "/system/xbin/su";
    private static final String ROOTING_PATH_3 = "/system/app/SuperUser.apk";
    @SuppressLint("SdCardPath") private static final String ROOTING_PATH_4 = "/data/data/com.homenumber.print.su";

    private static String[] RootFilesPath = new String[] {
            ROOT_PATH + ROOTING_PATH_1 ,
            ROOT_PATH + ROOTING_PATH_2 ,
            ROOT_PATH + ROOTING_PATH_3 ,
            ROOT_PATH + ROOTING_PATH_4
    };

    /**
     * 루팅여부
     ************************************************************************************************************************************************/
    public static boolean isRooting() {
        boolean isRootingFlag = false;

        try {
            Process process = Runtime.getRuntime().exec("find / -name su");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if(!reader.ready()) {
                return false;
            }

            String result = reader.readLine();
            if(result.contains("/su")) {
                isRootingFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isRootingFlag = false;
        }

        if(!isRootingFlag) {
            isRootingFlag = checkRootingFiles(RootFilesPath);
        }

        return isRootingFlag;
    }

    /**
     * 루팅 파일이 존재하는지 체크
     ************************************************************************************************************************************************/
    private static boolean checkRootingFiles(String[] filePaths) {
        boolean result = false;
        File file;

        for (String path : filePaths) {
            file = new File(path);

            if (file.exists() && file.isFile()) {
                result = true;
                break;
            } else {
                result = false;
            }
        }

        return result;
    }
}
