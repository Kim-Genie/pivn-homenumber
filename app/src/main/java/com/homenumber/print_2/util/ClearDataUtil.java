package com.homenumber.print_2.util;

import android.content.Context;

import com.homenumber.print_2.userdata.UserData;
import com.homenumber.print_2.userdata.UserDataManager;

public class ClearDataUtil {
    
    public static void ClearUserData(Context context){
        UserData userData = new UserData();
        UserDataManager userDataManager = new UserDataManager(context);
        userData.setUserToken(null);
        userData.setUserAccid(null);
        userData.setItemName(null);
        userData.setItemPlusInfo(null);
        userData.setItemPrice(null);
        userData.setItemNumber(null);
        userData.setItemKg(null);
        userData.setItemMessage(null);
        userData.setItemMemo(null);
        userData.setItemHow(null);
        userData.setAccept_gs_1(false);
        userData.setAccept_gs_2(false);
        userData.setAccept_gs_3(false);
        userData.setSelectedUserAlias(null);
        userData.setUserNormalTelephone1(null);
        userData.setUserNormalTelephone2(null);
        userDataManager.setUserData(userData);
    }
}
