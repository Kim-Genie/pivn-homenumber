package com.homenumber.print_2.userdata;

import android.content.Context;
import android.content.SharedPreferences;

import com.homenumber.print_2.Constant;

import java.util.Set;

/**
 * 유저의 스마트폰에서 저장되어 사용될 Data입니다. 중요하게 다루어지므로 확인하십시오.
 * @author 나비이쁜이
 * @since 2019.09.03
 */
public class UserDataManager {
    private Context context;

    public UserDataManager(Context context) {
        this.context = context;
    }

    /**
     * SharedPreferences Getter
     **************************************************************************************************************************************/
    public UserData getUserData() {
        SharedPreferences preference = context.getSharedPreferences(Constant.PREF_NAME_KEY, Context.MODE_PRIVATE);
        String userID = preference.getString(Constant.PREF_KEY_USER_ID, null);
        String userIDSave = preference.getString(Constant.PREF_KEY_USER_ID_SAVE, null);
        String userToken = preference.getString(Constant.PREF_KEY_USER_TOKEN, null);
        String userKeypadOption = preference.getString(Constant.PREF_KEY_USER_KEYPAD_OPTION, null);
        String userAccid = preference.getString(Constant.PREF_KEY_USER_ACCID, null);
        String itemName = preference.getString(Constant.PREF_ITEM_NAME, null);
        String itemPlusInfo = preference.getString(Constant.PREF_ITEM_PLUSINFO, null);
        String itemPrice = preference.getString(Constant.PREF_ITEM_PRICE, null);
        String itemCharge = preference.getString(Constant.PREF_ITEM_CHARGE, null);
        String itemNumber = preference.getString(Constant.PREF_ITEM_NUMBER, null);
        String itemKg = preference.getString(Constant.PREF_ITEM_KG, null);
        String itemMessage = preference.getString(Constant.PREF_ITEM_MESSAGE, null);
        String itemMemo = preference.getString(Constant.PREF_ITEM_MEMO, null);
        String itemHow = preference.getString(Constant.PREF_ITEM_HOW, null);

        int imgResource = preference.getInt(Constant.PREF_IMG_RESOURCE, 0);

        String orderDate = preference.getString(Constant.PREF_ORDER_DATE, null);
        String orderLogo = preference.getString(Constant.PREF_ORDER_LOGO, null);
        String orderNumber = preference.getString(Constant.PREF_ORDER_NUMBER, null);
        String orderHomenumber = preference.getString(Constant.PREF_ORDER_HOMENUMBER, null);

        String userHN = preference.getString(Constant.PREF_KEY_USER_HOMENUMBER, null);
        String userHN2 = preference.getString(Constant.PREF_KEY_USER_HOMENUMBER2, null);
        String userHomeAddress = preference.getString(Constant.PREF_KEY_USER_HOMEADDRESS, null);
        String userCompanyAddress = preference.getString(Constant.PREF_KEY_USER_COMPANYADDRESS, null);
        String userSelectedAlias = preference.getString(Constant.RK_USER_FROM, "HOMENUM1"); //유저가 선택한 집or회사
        String goodNo = preference.getString(Constant.PREF_KEY_GOOD_NO, null); //유저가 선택한 집or회사

        String userDetailAddress = preference.getString(Constant.PREF_KEY_USER_DTLADDRESS, null);
        String userDetailAddress2 = preference.getString(Constant.PREF_KEY_USER_DTLADDRESS2, null);

        String userZipCode1 = preference.getString(Constant.PREF_KEY_USER_ZIPCODE1, null);
        String userZipCode2 = preference.getString(Constant.PREF_KEY_USER_ZIPCODE2, null);

        String userTelephone1 = preference.getString(Constant.PREF_KEY_USER_TELEPHONE1, null);
        String userTelephone2 = preference.getString(Constant.PREF_KEY_USER_TELEPHONE2, null);
        String userNormalTelephone1 = preference.getString(Constant.PREF_KEY_USER_NORMAL_TELEPHONE1, null);
        String userNormalTelephone2 = preference.getString(Constant.PREF_KEY_USER_NORMAL_TELEPHONE2, null);


        String userAlias = preference.getString(Constant.PREF_KEY_USER_ALIAS, null);          // 유저 전화번호 2
        String userAlias2 = preference.getString(Constant.PREF_KEY_USER_ALIAS2, null);          // 유저 전화번호 2

        String userName = preference.getString(Constant.PREF_KEY_USER_NAME, null);          // 유저 이름1
        String userName2 = preference.getString(Constant.PREF_KEY_USER_NAME2, null);          // 유저 이름2

        boolean itemAcceptGS1 = preference.getBoolean(Constant.PREF_ITEM_ACCEPT_GS_1, false);
        boolean itemAcceptGS2 = preference.getBoolean(Constant.PREF_ITEM_ACCEPT_GS_2, false);
        boolean itemAcceptGS3 = preference.getBoolean(Constant.PREF_ITEM_ACCEPT_GS_3, false);

        Set<String> userCookie = preference.getStringSet(Constant.PREF_KEY_USER_COOKIE, null);

        UserData userData = new UserData();
        userData.setUserID(userID);
        userData.setUserIDSave(userIDSave);
        userData.setUserToken(userToken);
        userData.setUserKeypadOption(userKeypadOption);
        userData.setUserAccid(userAccid);
        userData.setItemName(itemName);
        userData.setItemPlusInfo(itemPlusInfo);
        userData.setItemPrice(itemPrice);
        userData.setItemCharge(itemCharge);
        userData.setItemNumber(itemNumber);
        userData.setItemKg(itemKg);
        userData.setItemMessage(itemMessage);
        userData.setItemMemo(itemMemo);
        userData.setItemHow(itemHow);
        userData.setAccept_gs_1(itemAcceptGS1);
        userData.setAccept_gs_2(itemAcceptGS2);
        userData.setAccept_gs_3(itemAcceptGS3);
        userData.setResourceImage(imgResource);
        userData.setOrderDate(orderDate);
        userData.setOrderLogo(orderLogo);
        userData.setOrderNumber(orderNumber);
        userData.setOrderHomenumber(orderHomenumber);

        userData.setUserHN(userHN);
        userData.setUserHN2(userHN2);
        userData.setGoodNo(goodNo);
        userData.setUserHomeAddress(userHomeAddress);
        userData.setUserCompanyAddress(userCompanyAddress);
        userData.setUserTelephone1(userTelephone1);
        userData.setUserTelephone2(userTelephone2);
        userData.setUserNormalTelephone1(userNormalTelephone1);
        userData.setUserNormalTelephone2(userNormalTelephone2);

        userData.setUserDetailAddress(userDetailAddress);
        userData.setUserDetailAddress2(userDetailAddress2);
        userData.setUserZipCode1(userZipCode1);
        userData.setUserZipCode2(userZipCode2);
        userData.setSelectedUserAlias(userSelectedAlias);

        userData.setUserAlias(userAlias);
        userData.setUserAlias2(userAlias2);

        userData.setUserCookie(userCookie);

        userData.setUserName(userName);
        userData.setUserName2(userName2);

        return userData;
    }

    /**
     * SharedPreferences Setter
     **************************************************************************************************************************************/
    public void setUserData(UserData userData) {
        SharedPreferences preference = context.getSharedPreferences(Constant.PREF_NAME_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        editor.putString(Constant.PREF_KEY_USER_ID, userData.getUserID());
        editor.putString(Constant.PREF_KEY_USER_ID_SAVE, userData.getUserIDSave());
        editor.putString(Constant.PREF_KEY_USER_TOKEN, userData.getUserToken());
        editor.putString(Constant.PREF_KEY_USER_KEYPAD_OPTION, userData.getUserKeypadOption());
        editor.putString(Constant.PREF_KEY_USER_ACCID, userData.getUserAccid());
        editor.putString(Constant.PREF_ITEM_NAME, userData.getItemName());
        editor.putString(Constant.PREF_ITEM_PLUSINFO, userData.getItemPlusInfo());
        editor.putString(Constant.PREF_ITEM_PRICE, userData.getItemPrice());
        editor.putString(Constant.PREF_ITEM_CHARGE, userData.getItemCharge());
        editor.putString(Constant.PREF_ITEM_NUMBER, userData.getItemNumber());
        editor.putString(Constant.PREF_ITEM_KG, userData.getItemKg());
        editor.putString(Constant.PREF_ITEM_MESSAGE, userData.getItemMessage());
        editor.putString(Constant.PREF_ITEM_MEMO, userData.getItemMemo());
        editor.putString(Constant.PREF_ITEM_HOW, userData.getItemHow());

        editor.putInt(Constant.PREF_IMG_RESOURCE, userData.getResourceImage());

        editor.putBoolean(Constant.PREF_ITEM_ACCEPT_GS_1, userData.isAccept_gs_1());
        editor.putBoolean(Constant.PREF_ITEM_ACCEPT_GS_2, userData.isAccept_gs_2());
        editor.putBoolean(Constant.PREF_ITEM_ACCEPT_GS_3, userData.isAccept_gs_3());

        editor.putString(Constant.PREF_ORDER_DATE, userData.getOrderDate());
        editor.putString(Constant.PREF_ORDER_LOGO, userData.getOrderLogo());
        editor.putString(Constant.PREF_ORDER_NUMBER, userData.getOrderNumber());
        editor.putString(Constant.PREF_ORDER_HOMENUMBER, userData.getOrderHomenumber());

        editor.putString(Constant.PREF_KEY_USER_HOMENUMBER, userData.getUserHN());
        editor.putString(Constant.PREF_KEY_USER_HOMENUMBER2, userData.getUserHN2());
        editor.putString(Constant.PREF_KEY_GOOD_NO,userData.getGoodNo());

        editor.putString(Constant.PREF_KEY_USER_HOMEADDRESS, userData.getUserHomeAddress());
        editor.putString(Constant.PREF_KEY_USER_COMPANYADDRESS, userData.getUserCompanyAddress());

        editor.putString(Constant.PREF_KEY_USER_DTLADDRESS, userData.getUserDetailAddress());
        editor.putString(Constant.PREF_KEY_USER_DTLADDRESS2, userData.getUserDetailAddress2());
        editor.putString(Constant.PREF_KEY_USER_ZIPCODE1, userData.getUserZipCode1());
        editor.putString(Constant.PREF_KEY_USER_ZIPCODE2, userData.getUserZipCode2());
        editor.putString(Constant.RK_USER_FROM, userData.getSelectedUserAlias());

        editor.putString(Constant.PREF_KEY_USER_TELEPHONE1, userData.getUserTelephone1());
        editor.putString(Constant.PREF_KEY_USER_TELEPHONE2, userData.getUserTelephone2());
        editor.putString(Constant.PREF_KEY_USER_NORMAL_TELEPHONE1, userData.getUserNormalTelephone1());
        editor.putString(Constant.PREF_KEY_USER_NORMAL_TELEPHONE2, userData.getUserNormalTelephone2());

        editor.putString(Constant.PREF_KEY_USER_ALIAS, userData.getUserAlias());
        editor.putString(Constant.PREF_KEY_USER_ALIAS2, userData.getUserAlias2());

        editor.putString(Constant.PREF_KEY_USER_NAME, userData.getUserName());
        editor.putString(Constant.PREF_KEY_USER_NAME2, userData.getUserName2());

        editor.putStringSet(Constant.PREF_KEY_USER_COOKIE, userData.getUserCookie());

        editor.apply();
        editor.commit();
    }
}
