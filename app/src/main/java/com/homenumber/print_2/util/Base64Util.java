package com.homenumber.print_2.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;

/**
 * Base64 - Bitmap Util - 이미지 인쇄 출력에 속도를 향상 시키기 위한 과정의 작업
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class Base64Util {

    public static void main(String[] args) {
        Base64Util base64Util = new Base64Util();
    }

    /**
     * Byte Array값을 Bitmap으로 변환
     */
    public static Bitmap base64ToBitmap(String result_string) {
        // convert base64
        byte[] bImage = Base64.decode(result_string, 0);

        // convert stream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bImage);

        // convert image
        return BitmapFactory.decodeStream(byteArrayInputStream);
    }
}
