package com.homenumber.print_2.util;

/**
 * RequestCode
 * @author 나비이쁜이
 * @since 2019.04.30
 */
public class RequestCodeUtil {

    /**
     * Permission
     ************************************************************************************************************************************************/
	public static class PermissionCode {
		public static final int REQ_PERMISSION_ALL = 0;
	}

    /**
     * Google Play Service Check
     ************************************************************************************************************************************************/
    public static class GooglePlayServiceCheckReqCode {
        public static final int REQ_GOOGLE_NOT_SUCCES = 0;
    }

    /**
     * 주소록 및 출력내역에서 키패드로 이동 및 데이터 전달을 위한 Code 정의
     ************************************************************************************************************************************************/
    public static class ActivityReqCode {
        public static final int REQ_ADDRESS_BOOK_RETURN_KEYPAD = 1;
    }
}
