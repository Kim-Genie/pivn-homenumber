package com.homenumber.print_2;

/**
 * 통신 규약 및 Return의 고정 값들등 여러가지 값들을 지정하는 Class
 * @author 나비이쁜이
 * @since 2019.09.03
 */
public class Constant {

    /**
     * 디버그 모드
     */
    public static final boolean DEBUG_MODE = true;

    /**
     * 주소찾기 결과 개수 / 출력내역 결과 개수
     ************************************************************************************************************************************************/
    public static final int ARRAY_PAGE                          = 15;


    /**
     * 택배사
     ************************************************************************************************************************************************/
    public static final String PRINT_LOGEN                      = "Logen";
    public static final String PRINT_CVSNET                     = "cvsnet";
    public static final String PRINT_CJ                         = "CJLogistics";
    public static final String PRINT_LOTTE                         = "Lotteglogis";

    /**
     * RequestCodeUtil intent 상수값
     ************************************************************************************************************************************************/
    public static final String KEYPAD_HOMENUMBER                = "KEYPAD_HOMENUMBER";

    /**
     * 정규식
     ************************************************************************************************************************************************/
    public static final String PHONENUMBER_VALID                = "^(010|011|016|017|018|019)-[0-9]{3,4}-[0-9]{4}$";                                // 휴대폰번호 정규식
    public static final String PHONENUMBER_VALID2                = "^(010|011|016|017|018|019)[0-9]{7,8}$";                                // 휴대폰번호 정규식
    public static final String PASSWORD_VALID                   = "^(?=.*[0-9])(?=.*[A-Za-z])[0-9A-Za-z!@#$%^&*()+_=-]{6,16}$";                     // 비밀번호 결과 정규식
    public static final String SELLER_ID_VALID                  = "^[A-Za-z][0-9A-Za-z]{3,19}$";                                                    // 아이디 정규식

    public static final String SELLER_PW_VALID                  = "^(?=.*[0-9])(?=.*[A-Za-z])[!-~]{4,20}$";                                         // 비밀번호 임시 정규식
    public static final String PASSWORD_CHECK_VALID             = "^[a-zA-Z0-9!@#$%^&*()+_=-]+$";                                                   // 비밀번호 입력 정규식
    public static final String PASSWORD_CHECK_VALID2            = "^[!-~]+$";                                                                       // 비밀번호 입력 정규식

    /**
     * REQUSET_HEADER_PATH
     ************************************************************************************************************************************************/
    public static final String REQ_MAIN_HEADER                  = "https://hnc2crest.homenumber.co.kr/";            // Main Header
//    public static final String REQ_MAIN_HEADER                  = "http://tc2crest.homenumber.co.kr/";         // 현재 구글 올라간 서버
//    public static final String REQ_MAIN_HEADER                  = "http://192.168.0.2:28008/";         // Test Main Header
//    public static final String REQ_MAIN_HEADER                  = "http://192.168.0.2:28008/";         // Test Main Header
//    public static final String REQ_MAIN_HEADER                  = "http://192.168.0.27:8080/"; // kmj test




    /**


     * REQUSET_KEY - Server Header
     ************************************************************************************************************************************************/
    public static final String DEVICE_UUID_HEADER               = "android-";                                       // Device UUID 앞에 사용될 구분자
    public static final String RK_MOBILE_NO                     = "mobile_no";                                      // 유저 휴대폰번호
    public static final String RK_HEADER_ACCESS_TOKEN           = "access_token";                                   // Header -> access_token
    public static final String RK_HEADER_DEVICE_UUID            = "device_uuid";                                    // Header -> device_uuid

    public static final String RK_USER_ID                       = "usrId";                                          // 유저 아이디
    public static final String RK_USER_PW                       = "usrPw";                                          // 유저 비밀번호
    public static final String RK_USER_ID_PW                    = "usrIdPw";                                        // 유저 아이디+@+비밀번호
    public static final String RK_AT                            = "@";                                              //@
    public static final String RK_DEVICE_UUID                   = "device_uuid";                                    // 유저 디바이스 UUID
    public static final String RK_ACCESS_TOKEN                  = "access_token";                                   // 유저 Token
    public static final String RK_ACC_ID                        = "acc_id";                                         // 유저 Token
    public static final String RK_OLD_PASSWORD                  = "old_password";                                   // 현재 비번
    public static final String RK_NEW_PASSWORD                  = "new_password";                                   // 신규 비번
    public static final String RK_VALIDATION_TIMESTAMP          = "validation_timestamp";                           // 유저 SMS 인증요청 시간

    public static final String RK_HOMENUMBER                    = "homeNo";                                   // 신규 비번
    public static final String RK_MOBILE_NUMBER                 = "mobileNo";                                   // 신규 비번
    public static final String RK_PWD                           = "usrPwd";                                   // 신규 비번

    public static final String SENDER_HN                        = "senderHn";                                      // 송하인 홈넘버
    public static final String RECEIVER_HN                      = "receiverHn";                                    // 수하인 홈넘버
    public static final String PAYMENT_CHARGE                   = "paymentCharge";                                 // 운임           필수
    public static final String PAYMENT_DISCOUNT                 = "paymentDiscount";                               // 할인금액
    public static final String PAYMENT_METHOD                   = "paymentMethod";                                 // 결제방법
    public static final String ORDER_INFO_KEYPRODUCT_NM         = "orderInfoKeyproductNm";                       // 대표상품명
    public static final String ORDER_INFO_PRODUCT_INFO          = "orderInfoProductInfo";                        // 상품 추가정보
    public static final String ORDER_INFO_PRODUCT_TOT_PRICE     = "orderInfoProductTotPrice";                   // 전체상품 물품가액
    public static final String ORDER_INFO_PRODUCT_TOT_QTY       = "orderInfoProductTotQty";                     // 전체상품 수량
    public static final String ORDER_INFO_PRODUCT_LIMIT_WEIGHT  = "orderInfoProductLimitWeight";                // 무게 한도
    public static final String ORDER_INFO_CUST_MSG              = "orderInfoCustMsg";                            // 고객 요청 메세지
    public static final String ORDER_INFO_MISC                  = "orderInfoMisc";                                // 주문관련 추가 정보

    public static final String COURIER_CODE                  = "courierCode";                                // 택배사 코드


    public static final String PAYMENT_CASH                     = "CASH";                                           // 선불
    public static final String PAYMENT_CASH_LATER               = "CASH_LATER";                                     // 착불
    public static final String PAYMENT_CREDIT                   = "CREDIT";                                         // 신용

    public static final String RK_PROJECT_CODE                  = "cvsnet";                                         // 프로젝트코드
    public static final String RK_MAll_CODE                     = "99";                                             // cvsnet에서 제공하는 쇼핑몰 코드

    public static final String RK_USER_KIND                     = "1";                                              // 유저 계정타입
    public static final String RK_USER_GOODNO                   = "CH";                                           // 유저 홈넘버타입
    public static final String RK_USER_FROM                   = "rkuserid";                                           // 유저 집(1)회사(2)

    /**
     * PREFERENCE KEY
     ************************************************************************************************************************************************/
    public static final String PREF_NAME_KEY                    = "homenumber_ble_print";                           // Preference Key
    public static final String PREF_KEY_USER_ID                 = "userId";                                         // 유저 Id
    public static final String PREF_KEY_USER_ID2                = "usrId";                                         // 휴대폰번호
    public static final String PREF_KEY_USER_PW                 = "usrPwd";                                         // 유저 비밀번호
    public static final String PREF_KEY_USER_NM                 = "usrNm";                                          // 유저 고객명
    public static final String PREF_KEY_USER_ZC                 = "zipCd";                                          // 유저 우편번호
    public static final String PREF_KEY_USER_ADD1               = "addr1";                                          // 유저 기본주소
    public static final String PREF_KEY_USER_ADD2               = "addr2";                                          // 유저 상세주소
    public static final String PREF_KEY_USER_EMAIL              = "usrEmail";                                       // 유저 이메일
    public static final String PREF_KEY_USER_REF                = "addrRef";                                        // 유저 주소 참조값
    public static final String PREF_KEY_USER_KIND               = "usrKind";                                        // 유저 계정타입
    public static final String PREF_KEY_USER_GN                 = "goodNo";                                         // 유저 홈넘버타입
    public static final String PREF_KEY_USER_ACC                = "accId";                                          // 유저 ACC

    public static final String PREF_KEY_USER_ID_SAVE            = "userIdSave";                                     // 유저 Id save
    public static final String PREF_KEY_USER_TOKEN              = "userToken";                                      // 유저 Token
    public static final String PREF_KEY_USER_KEYPAD_OPTION      = "userKeypadOption";                               // 유저 키패드 옵션
    public static final String PREF_KEY_USER_ACCID              = "userAccid";                                      // 유저 acc id
    public static final String PREF_ITEM_NAME                   = "itemName";                                       // 아이템 이름
    public static final String PREF_ITEM_PLUSINFO               = "itemPlusInfo";                                   // 아이템 추가정보
    public static final String PREF_ITEM_PRICE                  = "itemPrice";                                      // 아이템 가격
    public static final String PREF_ITEM_NUMBER                 = "itemNumber";                                     // 아이템 개수
    public static final String PREF_ITEM_CHARGE                 = "itemCharge";                                     // 아이템 개수
    public static final String PREF_ITEM_KG                     = "itemKg";                                         // 아이템 무게
    public static final String PREF_ITEM_MESSAGE                = "itemMessage";                                    // 아이템 메세지
    public static final String PREF_ITEM_MEMO                   = "itemMemo";                                       // 아이템 메모
    public static final String PREF_ITEM_HOW                    = "itemHow";                                        // 아이템 결제방법
    public static final String PREF_ITEM_ACCEPT_GS_1            = "accept_gs_1";                                    // GS 이용약관 동의 1
    public static final String PREF_ITEM_ACCEPT_GS_2            = "accept_gs_2";                                    // GS 이용약관 동의 2
    public static final String PREF_ITEM_ACCEPT_GS_3            = "accept_gs_3";                                    // GS 이용약관 동의 3
    public static final String PREF_KEY_GOOD_NO             = "goodNo";  //일반/골드홈넘버

    public static final String PREF_IMG_RESOURCE                  = "imgResource";                                      // 이미지 Resource

    public static final String PREF_ORDER_DATE                  = "orderDate";                                      //
    public static final String PREF_ORDER_LOGO                  = "orderLogo";                                      //
    public static final String PREF_ORDER_NUMBER                = "orderNumber";                                    //
    public static final String PREF_ORDER_HOMENUMBER            = "orderHomenumber";                                //

    public static final String PREF_KEY_USER_HOMENUMBER         = "userHN";                                         // 유저 홈넘버1
    public static final String PREF_KEY_USER_HOMENUMBER2        = "userHN2";                                         // 유저 홈넘버2
    public static final String PREF_KEY_USER_HOMEADDRESS        = "userHomeAddress";                                         // 유저 Id
    public static final String PREF_KEY_USER_DTLADDRESS         = "userDetailAddress";                                         // 유저 Id
    public static final String PREF_KEY_USER_DTLADDRESS2        = "userDetailAddress2";                                         // 유저 Id
    public static final String PREF_KEY_USER_ZIPCODE1         = "userZipCode1";                                         // 유저 Id
    public static final String PREF_KEY_USER_ZIPCODE2        = "userZipCode2";                                         // 유저 Id
    public static final String PREF_KEY_USER_COMPANYADDRESS     = "userCompanyAddress";                                         // 유저 Id
    public static final String PREF_KEY_USER_TELEPHONE1         = "userTelephone1";                                         // 유저 휴대폰번호 1
    public static final String PREF_KEY_USER_TELEPHONE2         = "userTelephone2";                                         // 유저 휴대폰번호 2
    public static final String PREF_KEY_USER_NORMAL_TELEPHONE1         = "userNormalTelephone1";                                         // 유저 Id
    public static final String PREF_KEY_USER_NORMAL_TELEPHONE2         = "userNormalTelephone2";                                         // 유저 Id

    public static final String PREF_KEY_USER_ALIAS              = "alias";                                         // 유저 Id
    public static final String PREF_KEY_USER_ALIAS2              = "alias2";                                         // 유저 Id
    public static final String PREF_KEY_USER_NAME              = "userName";                                         // 유저 Id
    public static final String PREF_KEY_USER_NAME2              = "userName2";                                         // 유저 Id


    public static final String PREF_KEY_USER_COOKIE             = "Set-Cookie";                                      //

    public static final String PREF_KEY_SMTADDRS         = "smtAddrs";                                         // 내 정보 수정
    public static final String PREF_KEY_SMTSN         = "smtSn";                                         // 순번
    public static final String PREF_KEY_SMTADDR         = "smtAddr";                                         // 홈넘버
    public static final String PREF_KEY_USERTEL         = "userTel";                                         // 홈넘버

    public static final String PREF_KEY_HOMENUMBER        = "homeNumber";                                         // 홈넘버 for authkey
    public static final String PREF_KEY_HOMENUMBERPASSWORD       = "homeNumberPassword";                                 // 홈넘버 for authkey



}
