package com.homenumber.print_2.userdata;

import java.util.Set;

/**
 * 유저의 스마트폰에서 저장되어 사용될 Data입니다. 중요하게 다루어지므로 확인하십시오.
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class UserData {

    private String userID;
    private String userIDSave;
    private String userToken;
    private String userKeypadOption;
    private String userAccid;

    private String orderDate;       // 날짜시간
    private String orderLogo;       // 택배사 로고 종류
    private String orderNumber;     // 예약번호
    private String orderHomenumber; // 수신자 홈넘버

    private String userHN;          // 유저 홈넘버
    private String userHN2;          // 유저 홈넘버
    private String userHomeAddress;          // 유저 집주소
    private String userDetailAddress;          // 유저 집주소 상세1
    private String userDetailAddress2;          // 유저 집주소 상세 2
    private String userZipCode1;          // 유저 우편코드1
    private String userZipCode2;          // 유저 우편코드2
    private String userCompanyAddress;          // 유저 회사주소
    private String userTelephone1;          // 유저 전화번호 1
    private String userTelephone2;          // 유저 전화번호 2
    private String userNormalTelephone1;          // 유저 일반전화 1
    private String userNormalTelephone2;          // 유저 일반전화 2
    private String goodNo;             // 일반/골드 홈넘버


    private String userAlias;          // 유저 집
    private String userAlias2;          // 유저 회사
    private String selectedUserAlias;          // 선택한 유저 Alias

    private String userName;          // 유저 홈넘버
    private String userName2;          // 유저 홈넘버

    private String itemName;                // 대표 상품명
    private String itemPlusInfo;            // 추가정보
    private String itemCharge;            // 추가정보
    private String itemPrice;            // 물품 가액
    private String itemNumber;          // 물품 수량
    private String itemKg;              // 무게
    private String itemMessage;         // 메세지
    private String itemMemo;            // 메모
    private String itemHow;             // 지불방법
    private int resourceImage;             // 이미지

    private boolean accept_gs_1;
    private boolean accept_gs_2;
    private boolean accept_gs_3;

    public String getUserZipCode1() {
        return userZipCode1;
    }

    public void setUserZipCode1(String userZipCode1) {
        this.userZipCode1 = userZipCode1;
    }

    public String getUserZipCode2() {
        return userZipCode2;
    }

    public void setUserZipCode2(String userZipCode2) {
        this.userZipCode2 = userZipCode2;
    }

    public String getUserName2() {
        return userName2;
    }

    public void setUserName2(String userName2) {
        this.userName2 = userName2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Set<String> userCookie;             // 쿠키

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAlias2() {
        return userAlias2;
    }

    public void setUserAlias2(String userAlias2) {
        this.userAlias2 = userAlias2;
    }

    public String getUserDetailAddress() {
        return userDetailAddress;
    }

    public void setUserDetailAddress(String userDetailAddress) {
        this.userDetailAddress = userDetailAddress;
    }

    public String getUserDetailAddress2() {
        return userDetailAddress2;
    }

    public void setUserDetailAddress2(String userDetailAddress2) {
        this.userDetailAddress2 = userDetailAddress2;
    }

    public String getUserHN2() {
        return userHN2;
    }

    public void setUserHN2(String userHN2) {
        this.userHN2 = userHN2;
    }

    public Set<String> getUserCookie() {
        return userCookie;
    }

    public void setUserCookie(Set<String> userCookie) {
        this.userCookie = userCookie;
    }

    public String getItemCharge() {
        return itemCharge;
    }

    public void setItemCharge(String itemCharge) {
        this.itemCharge = itemCharge;
    }

    public String getUserHN() {
        return userHN;
    }

    public void setUserHN(String userHN) {
        this.userHN = userHN;
    }

    public String getUserHomeAddress() {
        return userHomeAddress;
    }

    public void setUserHomeAddress(String userHomeAddress) {
        this.userHomeAddress = userHomeAddress;
    }

    public String getUserCompanyAddress() {
        return userCompanyAddress;
    }

    public void setUserCompanyAddress(String userCompanyAddress) {
        this.userCompanyAddress = userCompanyAddress;
    }

    public String getUserTelephone1() {
        return userTelephone1;
    }

    public void setUserTelephone1(String userTelephone1) {
        this.userTelephone1 = userTelephone1;
    }

    public String getUserTelephone2() {
        return userTelephone2;
    }

    public void setUserTelephone2(String userTelephone2) {
        this.userTelephone2 = userTelephone2;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderLogo() {
        return orderLogo;
    }

    public void setOrderLogo(String orderLogo) {
        this.orderLogo = orderLogo;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderHomenumber() {
        return orderHomenumber;
    }

    public void setOrderHomenumber(String orderHomenumber) {
        this.orderHomenumber = orderHomenumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserIDSave() {
        return userIDSave;
    }

    public void setUserIDSave(String userIDSave) {
        this.userIDSave = userIDSave;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserKeypadOption() {
        return userKeypadOption;
    }

    public void setUserKeypadOption(String userKeypadOption) {
        this.userKeypadOption = userKeypadOption;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPlusInfo() {
        return itemPlusInfo;
    }

    public void setItemPlusInfo(String itemPlusInfo) {
        this.itemPlusInfo = itemPlusInfo;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemKg() {
        return itemKg;
    }

    public void setItemKg(String itemKg) {
        this.itemKg = itemKg;
    }

    public String getItemMessage() {
        return itemMessage;
    }

    public void setItemMessage(String itemMessage) {
        this.itemMessage = itemMessage;
    }

    public String getItemMemo() {
        return itemMemo;
    }

    public void setItemMemo(String itemMemo) {
        this.itemMemo = itemMemo;
    }

    public String getItemHow() {
        return itemHow;
    }

    public void setItemHow(String itemHow) {
        this.itemHow = itemHow;
    }

    public boolean isAccept_gs_1() {
        return accept_gs_1;
    }

    public void setAccept_gs_1(boolean accept_gs_1) {
        this.accept_gs_1 = accept_gs_1;
    }

    public boolean isAccept_gs_2() {
        return accept_gs_2;
    }

    public void setAccept_gs_2(boolean accept_gs_2) {
        this.accept_gs_2 = accept_gs_2;
    }

    public boolean isAccept_gs_3() {
        return accept_gs_3;
    }

    public void setAccept_gs_3(boolean accept_gs_3) {
        this.accept_gs_3 = accept_gs_3;
    }

    public String getUserAccid() {
        return userAccid;
    }

    public void setUserAccid(String userAccid) {
        this.userAccid = userAccid;
    }
    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }
    public String getSelectedUserAlias() {
        return selectedUserAlias;
    }

    public void setSelectedUserAlias(String selectedUserAlias) {
        this.selectedUserAlias = selectedUserAlias;
    }

    public String getUserNormalTelephone1() {
        return userNormalTelephone1;
    }

    public void setUserNormalTelephone1(String userNormalTelephone1) {
        this.userNormalTelephone1 = userNormalTelephone1;
    }

    public String getUserNormalTelephone2() {
        return userNormalTelephone2;
    }

    public void setUserNormalTelephone2(String userNormalTelephone2) {
        this.userNormalTelephone2 = userNormalTelephone2;
    }

    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }
}
