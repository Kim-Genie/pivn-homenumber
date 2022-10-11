package com.homenumber.print_2.retrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Retrofit2 - Result ItemVo
 * @author 나비이쁜이
 * @since 2019.08.19
 */
@Data                       // @Getter, @Setter, @RequiredArgsConstructor, @EqualsAndHashCode, @ToString를 모두 선언한다.
@NoArgsConstructor          // 멤버 필드들이 모두 파라미터로 지정된 생성자와 빈 생성자를 만들어 줍니다.
@AllArgsConstructor         // 멤버 필드들이 모두 파라미터로 지정된 생성자와 빈 생성자를 만들어 줍니다.
public class RetrofitItemVo {

    @SerializedName("rsp_code") @Expose
    private Integer rspCode;

    @SerializedName("success") @Expose
    private Boolean success;

    @SerializedName("error_code") @Expose
    private String errorCode;

    @SerializedName("error_msg") @Expose
    private String errorMsg;

    @SerializedName("debug_msg") @Expose
    private String debug_msg;

    @SerializedName("alert_msg") @Expose
    private String alert_msg;

    @SerializedName("data") @Expose
    private Data data;

    /**
     * Data - Return Header
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Data {

        @SerializedName("usrId")
        @Expose
        private String userId;

        @SerializedName("usrNm")
        @Expose
        private String userNm;

        @SerializedName("acc_id")
        @Expose
        private String acc_id;

        @SerializedName("goodNo")
        @Expose
        private String goodNo;

        @SerializedName("duplicateResult")
        @Expose
        private boolean duplicateResult;

        @SerializedName("device_uuid")
        @Expose
        private String deviceUuid;

        @SerializedName("access_token")
        @Expose
        private String accessToken;

        @SerializedName("at_issue_date")
        @Expose
        private Long atIssueDate;

        @SerializedName("at_expiration_date")
        @Expose
        private Long atExpirationDate;

        @SerializedName("customer_name")
        @Expose
        private String customer_name;

        @SerializedName("bytes_list")
        @Expose
        private List<String> bytesList = null;

        @SerializedName("code_expiration_date")
        @Expose
        private Long code_expiration_date;

        @SerializedName("validation_timestamp")
        @Expose
        private Long validation_timestamp;

        @SerializedName("item_exists")
        @Expose
        private boolean item_exists;

        @SerializedName("address_list")
        @Expose
        private List<RetrofitAddressVo> address_list = null;

        @SerializedName("print_history")
        @Expose
        private List<RetrofitPrintLogVo> print_history = null;

        @SerializedName("result") @Expose
        private Result result;

        @SerializedName("returnDate") @Expose
        private String RETURN_DT;

        @SerializedName("returnTime") @Expose
        private String RETURN_TIME;

        @SerializedName("USER_ID") @Expose
        private String USER_ID;

        @SerializedName("userNm") @Expose
        private String USER_NM;

        @SerializedName("userEmail") @Expose
        private String USER_EMAIL;

        @SerializedName("userTel") @Expose
        private String USER_TEL;

        @SerializedName("userMobileTel") @Expose
        private String USER_MOBILE_TEL;

        @SerializedName("alias") @Expose
        private String ALIAS;

        @SerializedName("userBaseAddress") @Expose
        private String USER_BASE_ADDRESS;

        @SerializedName("userDtlAddress") @Expose
        private String USER_DTL_ADDRESS;

        @SerializedName("userZipCd") @Expose
        private String USER_ZIP_CD;

        @SerializedName("updateSecretKeyCount") @Expose
        private int UPDATE_SECRET_KEY_COUNT;

        @SerializedName("userDetailInfo") @Expose
        private List<RetrofitUserDataVo> USER_DTL_INFO_BY_HN = null;

        @SerializedName("orderList") @Expose
        public List<RetrofitOrderNumberList> orderList;

        @SerializedName("recommendeeList") @Expose
        private List<RecommendVo> recommendVoList =null;

        @SerializedName("homenumberList") @Expose
        private List<MyHomeNumberVo> homenumberVoList =null;


        @SerializedName("rcmnUrl") @Expose
        private String rcmnUrl =null;

        public String getRcmnUrl() {
            return rcmnUrl;
        }

        public void setRcmnUrl(String rcmnUrl) {
            this.rcmnUrl = rcmnUrl;
        }

        public int getUPDATE_SECRET_KEY_COUNT() {
            return UPDATE_SECRET_KEY_COUNT;
        }

        public void setUPDATE_SECRET_KEY_COUNT(int UPDATE_SECRET_KEY_COUNT) {
            this.UPDATE_SECRET_KEY_COUNT = UPDATE_SECRET_KEY_COUNT;
        }

        public Long getValidation_timestamp() {
            return validation_timestamp;
        }

        public void setValidation_timestamp(Long validation_timestamp) {
            this.validation_timestamp = validation_timestamp;
        }

        public Long getCode_expiration_date() {
            return code_expiration_date;
        }

        public void setCode_expiration_date(Long code_expiration_date) {
            this.code_expiration_date = code_expiration_date;
        }

        public List<RetrofitOrderNumberList> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<RetrofitOrderNumberList> orderList) {
            this.orderList = orderList;
        }


        public List<RetrofitUserDataVo> getUSER_DTL_INFO_BY_HN() {
            return USER_DTL_INFO_BY_HN;
        }

        public void setUSER_DTL_INFO_BY_HN(List<RetrofitUserDataVo> USER_DTL_INFO_BY_HN) {
            this.USER_DTL_INFO_BY_HN = USER_DTL_INFO_BY_HN;
        }

//        public String getResult() {
//            return result;
//        }
//
//        public void setResult(String result) {
//            this.result = result;
//        }


        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public String getRETURN_DT() {
            return RETURN_DT;
        }

        public void setRETURN_DT(String RETURN_DT) {
            this.RETURN_DT = RETURN_DT;
        }

        public String getRETURN_TIME() {
            return RETURN_TIME;
        }

        public void setRETURN_TIME(String RETURN_TIME) {
            this.RETURN_TIME = RETURN_TIME;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getUSER_NM() {
            return USER_NM;
        }

        public void setUSER_NM(String USER_NM) {
            this.USER_NM = USER_NM;
        }

        public String getUSER_EMAIL() {
            return USER_EMAIL;
        }

        public void setUSER_EMAIL(String USER_EMAIL) {
            this.USER_EMAIL = USER_EMAIL;
        }

        public String getUSER_TEL() {
            return USER_TEL;
        }

        public void setUSER_TEL(String USER_TEL) {
            this.USER_TEL = USER_TEL;
        }

        public String getUSER_MOBILE_TEL() {
            return USER_MOBILE_TEL;
        }

        public void setUSER_MOBILE_TEL(String USER_MOBILE_TEL) {
            this.USER_MOBILE_TEL = USER_MOBILE_TEL;
        }

        public String getALIAS() {
            return ALIAS;
        }

        public void setALIAS(String ALIAS) {
            this.ALIAS = ALIAS;
        }

        public String getUSER_BASE_ADDRESS() {
            return USER_BASE_ADDRESS;
        }

        public void setUSER_BASE_ADDRESS(String USER_BASE_ADDRESS) {
            this.USER_BASE_ADDRESS = USER_BASE_ADDRESS;
        }

        public String getUSER_DTL_ADDRESS() {
            return USER_DTL_ADDRESS;
        }

        public void setUSER_DTL_ADDRESS(String USER_DTL_ADDRESS) {
            this.USER_DTL_ADDRESS = USER_DTL_ADDRESS;
        }

        public String getUSER_ZIP_CD() {
            return USER_ZIP_CD;
        }

        public void setUSER_ZIP_CD(String USER_ZIP_CD) {
            this.USER_ZIP_CD = USER_ZIP_CD;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserNm() {
            return userNm;
        }

        public void setUserNm(String userNm) {
            this.userNm = userNm;
        }

        public String getDeviceUuid() {
            return deviceUuid;
        }

        public void setDeviceUuid(String deviceUuid) {
            this.deviceUuid = deviceUuid;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Long getAtIssueDate() {
            return atIssueDate;
        }

        public void setAtIssueDate(Long atIssueDate) {
            this.atIssueDate = atIssueDate;
        }

        public Long getAtExpirationDate() {
            return atExpirationDate;
        }

        public void setAtExpirationDate(Long atExpirationDate) {
            this.atExpirationDate = atExpirationDate;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public List<String> getBytesList() {
            return bytesList;
        }

        public void setBytesList(List<String> bytesList) {
            this.bytesList = bytesList;
        }


        public boolean isItem_exists() {
            return item_exists;
        }

        public void setItem_exists(boolean item_exists) {
            this.item_exists = item_exists;
        }

        public List<RetrofitAddressVo> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<RetrofitAddressVo> address_list) {
            this.address_list = address_list;
        }

        public List<RetrofitPrintLogVo> getPrint_history() {
            return print_history;
        }

        public void setPrint_history(List<RetrofitPrintLogVo> print_history) {
            this.print_history = print_history;
        }

        public String getAcc_id() {
            return acc_id;
        }

        public void setAcc_id(String acc_id) {
            this.acc_id = acc_id;
        }

        public List<RecommendVo> getRecommendVoList() {
            return recommendVoList;
        }

        public void setRecommendVoList(List<RecommendVo> recommendVoList) {
            this.recommendVoList = recommendVoList;
        }

        public List<MyHomeNumberVo> getHomenumberVoList() {
            return homenumberVoList;
        }

        public void setHomenumberVoList(List<MyHomeNumberVo> homenumberVoList) {
            this.homenumberVoList = homenumberVoList;
        }
    }


    /**
     * RoleAuthorities - Return Header
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RoleAuthorities {

        @SerializedName("authority") @Expose
        private String authority;

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }

    /**
     * RetrofitCreateOrderNumber- 예약번호생
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {

        @SerializedName("mallCode") @Expose
        public String mallCode;

        @SerializedName("orderNo") @Expose
        public String orderNo;

        @SerializedName("serviceType") @Expose
        public String serviceType;

        @SerializedName("sender") @Expose
        public SenderReceiver sender;

        @SerializedName("receiver") @Expose
        public SenderReceiver receiver;

        @SerializedName("payment") @Expose
        public Payment payment;

        @SerializedName("mallOrderInformation") @Expose
        public MallOrderInformation mallOrderInformation;

        @SerializedName("_links") @Expose
        public LinkSelf linkSelf;

        public String getMallCode() {
            return mallCode;
        }

        public void setMallCode(String mallCode) {
            this.mallCode = mallCode;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public SenderReceiver getSender() {
            return sender;
        }

        public void setSender(SenderReceiver sender) {
            this.sender = sender;
        }

        public SenderReceiver getReceiver() {
            return receiver;
        }

        public void setReceiver(SenderReceiver receiver) {
            this.receiver = receiver;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

        public MallOrderInformation getMallOrderInformation() {
            return mallOrderInformation;
        }

        public void setMallOrderInformation(MallOrderInformation mallOrderInformation) {
            this.mallOrderInformation = mallOrderInformation;
        }

        public LinkSelf getLinkSelf() {
            return linkSelf;
        }

        public void setLinkSelf(LinkSelf linkSelf) {
            this.linkSelf = linkSelf;
        }
    }

    /**
     * RetrofitCreateOrderNumber- 예약번호목록
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrofitOrderNumberList {

        @SerializedName("orderInfoNo") @Expose
        public String orderInfoNo;

        @SerializedName("logId") @Expose
        public String logId;

        @SerializedName("mallCode") @Expose
        public String mallCode;

        // 필요한거 1
        @SerializedName("orderNo") @Expose
        public String orderNo;

        @SerializedName("serviceType") @Expose
        public String serviceType;

        @SerializedName("senderHn") @Expose
        public String senderHn;

        @SerializedName("senderNm") @Expose
        public String senderNm;

        @SerializedName("senderZipcode") @Expose
        public String senderZipcode;

        @SerializedName("senderBaseAddress") @Expose
        public String senderBaseAddress;

        @SerializedName("senderDtlAddress") @Expose
        public String senderDtlAddress;

        @SerializedName("senderPriTel") @Expose
        public String senderPriTel;

        @SerializedName("senderSecTel") @Expose
        public String senderSecTel;

        // 필요한거 2
        @SerializedName("receiverHn") @Expose
        public String receiverHn;

        // 필요한거 3
        @SerializedName("receiverNm") @Expose
        public String receiverNm;

        @SerializedName("receiverZipcode") @Expose
        public String receiverZipcode;

        @SerializedName("receiverBaseAddress") @Expose
        public String receiverBaseAddress;

        @SerializedName("receiverDtlAddress") @Expose
        public String receiverDtlAddress;

        @SerializedName("receiverPriTel") @Expose
        public String receiverPriTel;

        @SerializedName("receiverSecTel") @Expose
        public String receiverSecTel;

        @SerializedName("paymentCharge") @Expose
        public int paymentCharge;

        @SerializedName("paymentMethod") @Expose
        public String paymentMethod;

        // 필요한거 4
        @SerializedName("orderInfoDate") @Expose
        public long orderInfoDate;

        @SerializedName("orderInfoKeyproductNm") @Expose
        public String orderInfoKeyproductNm;

        @SerializedName("orderInfoProductInfo") @Expose
        public String orderInfoProductInfo;

        @SerializedName("orderInfoProductTotPrice") @Expose
        public String orderInfoProductTotPrice;

        @SerializedName("orderInfoProductTotQty") @Expose
        public int orderInfoProductTotQty;

        @SerializedName("orderInfoProductLimitWeight") @Expose
        public int orderInfoProductLimitWeight;

        @SerializedName("orderInfoCustMsg") @Expose
        public String orderInfoCustMsg;

        @SerializedName("orderInfoMisc") @Expose
        public String orderInfoMisc;

        @SerializedName("accId") @Expose
        public String accId;

        // 필요한거 5
        @SerializedName("courierCode") @Expose
        public String courierCode;

        public String getOrderInfoNo() {
            return orderInfoNo;
        }

        public void setOrderInfoNo(String orderInfoNo) {
            this.orderInfoNo = orderInfoNo;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getMallCode() {
            return mallCode;
        }

        public void setMallCode(String mallCode) {
            this.mallCode = mallCode;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getSenderHn() {
            return senderHn;
        }

        public void setSenderHn(String senderHn) {
            this.senderHn = senderHn;
        }

        public String getSenderNm() {
            return senderNm;
        }

        public void setSenderNm(String senderNm) {
            this.senderNm = senderNm;
        }

        public String getSenderZipcode() {
            return senderZipcode;
        }

        public void setSenderZipcode(String senderZipcode) {
            this.senderZipcode = senderZipcode;
        }

        public String getSenderBaseAddress() {
            return senderBaseAddress;
        }

        public void setSenderBaseAddress(String senderBaseAddress) {
            this.senderBaseAddress = senderBaseAddress;
        }

        public String getSenderDtlAddress() {
            return senderDtlAddress;
        }

        public void setSenderDtlAddress(String senderDtlAddress) {
            this.senderDtlAddress = senderDtlAddress;
        }

        public String getSenderPriTel() {
            return senderPriTel;
        }

        public void setSenderPriTel(String senderPriTel) {
            this.senderPriTel = senderPriTel;
        }

        public String getSenderSecTel() {
            return senderSecTel;
        }

        public void setSenderSecTel(String senderSecTel) {
            this.senderSecTel = senderSecTel;
        }

        public String getReceiverHn() {
            return receiverHn;
        }

        public void setReceiverHn(String receiverHn) {
            this.receiverHn = receiverHn;
        }

        public String getReceiverNm() {
            return receiverNm;
        }

        public void setReceiverNm(String receiverNm) {
            this.receiverNm = receiverNm;
        }

        public String getReceiverZipcode() {
            return receiverZipcode;
        }

        public void setReceiverZipcode(String receiverZipcode) {
            this.receiverZipcode = receiverZipcode;
        }

        public String getReceiverBaseAddress() {
            return receiverBaseAddress;
        }

        public void setReceiverBaseAddress(String receiverBaseAddress) {
            this.receiverBaseAddress = receiverBaseAddress;
        }

        public String getReceiverDtlAddress() {
            return receiverDtlAddress;
        }

        public void setReceiverDtlAddress(String receiverDtlAddress) {
            this.receiverDtlAddress = receiverDtlAddress;
        }

        public String getReceiverPriTel() {
            return receiverPriTel;
        }

        public void setReceiverPriTel(String receiverPriTel) {
            this.receiverPriTel = receiverPriTel;
        }

        public String getReceiverSecTel() {
            return receiverSecTel;
        }

        public void setReceiverSecTel(String receiverSecTel) {
            this.receiverSecTel = receiverSecTel;
        }

        public int getPaymentCharge() {
            return paymentCharge;
        }

        public void setPaymentCharge(int paymentCharge) {
            this.paymentCharge = paymentCharge;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public long getOrderInfoDate() {
            return orderInfoDate;
        }

        public void setOrderInfoDate(long orderInfoDate) {
            this.orderInfoDate = orderInfoDate;
        }

        public String getOrderInfoKeyproductNm() {
            return orderInfoKeyproductNm;
        }

        public void setOrderInfoKeyproductNm(String orderInfoKeyproductNm) {
            this.orderInfoKeyproductNm = orderInfoKeyproductNm;
        }

        public String getOrderInfoProductInfo() {
            return orderInfoProductInfo;
        }

        public void setOrderInfoProductInfo(String orderInfoProductInfo) {
            this.orderInfoProductInfo = orderInfoProductInfo;
        }

        public String getOrderInfoProductTotPrice() {
            return orderInfoProductTotPrice;
        }

        public void setOrderInfoProductTotPrice(String orderInfoProductTotPrice) {
            this.orderInfoProductTotPrice = orderInfoProductTotPrice;
        }

        public int getOrderInfoProductTotQty() {
            return orderInfoProductTotQty;
        }

        public void setOrderInfoProductTotQty(int orderInfoProductTotQty) {
            this.orderInfoProductTotQty = orderInfoProductTotQty;
        }

        public int getOrderInfoProductLimitWeight() {
            return orderInfoProductLimitWeight;
        }

        public void setOrderInfoProductLimitWeight(int orderInfoProductLimitWeight) {
            this.orderInfoProductLimitWeight = orderInfoProductLimitWeight;
        }

        public String getOrderInfoCustMsg() {
            return orderInfoCustMsg;
        }

        public void setOrderInfoCustMsg(String orderInfoCustMsg) {
            this.orderInfoCustMsg = orderInfoCustMsg;
        }

        public String getOrderInfoMisc() {
            return orderInfoMisc;
        }

        public void setOrderInfoMisc(String orderInfoMisc) {
            this.orderInfoMisc = orderInfoMisc;
        }

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getCourierCode() {
            return courierCode;
        }

        public void setCourierCode(String courierCode) {
            this.courierCode = courierCode;
        }
    }

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SenderReceiver {
        @SerializedName("SENDER_HN") @Expose
        public String SENDER_HN;

        @SerializedName("RECEIVER_HN") @Expose
        public String RECEIVER_HN;

        @SerializedName("name") @Expose
        public String name;

        @SerializedName("zipCode") @Expose
        public String zipCode;

        @SerializedName("baseAddress") @Expose
        public String baseAddress;

        @SerializedName("detailAddress") @Expose
        public String detailAddress;

        @SerializedName("primaryTel") @Expose
        public String primaryTel;

        @SerializedName("secondaryTel") @Expose
        public String secondaryTel;
    }


    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
        @SerializedName("charge") @Expose
        public int charge;
        @SerializedName("discount") @Expose
        public int discount;
        @SerializedName("paymentMethod") @Expose
        public String paymentMethod;

    }

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MallOrderInformation {
        @SerializedName("orderNo") @Expose
        public String orderNo;
        @SerializedName("orderDate") @Expose
        public String orderDate;

        @SerializedName("product") @Expose
        public Product product;

        @SerializedName("customerMessage") @Expose
        public String customerMessage;

        @SerializedName("misc") @Expose
        public String misc;

    }

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        @SerializedName("keyProductName") @Expose
        public String keyProductName;
        @SerializedName("information") @Expose
        public String information;
        @SerializedName("totalQty") @Expose
        public int totalQty;
        @SerializedName("totalPrice") @Expose
        public int totalPrice;
        @SerializedName("limitWeight") @Expose
        public int limitWeight;

    }

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkSelf {
        @SerializedName("orderDetail") @Expose
        public String orderDetail;
        @SerializedName("href") @Expose
        public String href;
    }



    /**
     * RetrofitUserDataVo - 유저 정보
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrofitUserDataVo {

        @SerializedName("ACC_ID") @Expose
        public String ACC_ID;

        @SerializedName("SMT_SN") @Expose
        public int SMT_SN;

        @SerializedName("USER_DTL_ADDRESS") @Expose
        public String USER_DTL_ADDRESS;

        @SerializedName("ALIAS") @Expose
        public String ALIAS;

        @SerializedName("USER_BASE_ADDRESS") @Expose
        public String USER_BASE_ADDRESS;

        @SerializedName("HOME_NO") @Expose
        public String HOME_NO;

        @SerializedName("USER_MOBILE_TEL") @Expose
        public String USER_MOBILE_TEL;

        @SerializedName("RCV_NM") @Expose
        public String RCV_NM;

        @SerializedName("USER_TEL") @Expose
        private String USER_TEL;

        @SerializedName("ZIP_CD") @Expose
        private String ZIP_CD;

        @SerializedName("ADDR_REF") @Expose
        private String ADDR_REF;

        public String getADDR_REF() {
            return ADDR_REF;
        }

        public void setADDR_REF(String ADDR_REF) {
            this.ADDR_REF = ADDR_REF;
        }

        public String getZIP_CD() {
            return ZIP_CD;
        }

        public void setZIP_CD(String ZIP_CD) {
            this.ZIP_CD = ZIP_CD;
        }

        public String getUSER_TEL() {
            return USER_TEL;
        }

        public void setUSER_TEL(String USER_TEL) {
            this.USER_TEL = USER_TEL;
        }

        public String getUSER_MOBILE_TEL() {
            return USER_MOBILE_TEL;
        }

        public void setUSER_MOBILE_TEL(String USER_MOBILE_TEL) {
            this.USER_MOBILE_TEL = USER_MOBILE_TEL;
        }

        public String getRCV_NM() {
            return RCV_NM;
        }

        public void setRCV_NM(String RCV_NM) {
            this.RCV_NM = RCV_NM;
        }

        public int getSMT_SN() {
            return SMT_SN;
        }

        public void setSMT_SN(int SMT_SN) {
            this.SMT_SN = SMT_SN;
        }

        public String getUSER_DTL_ADDRESS() {
            return USER_DTL_ADDRESS;
        }

        public void setUSER_DTL_ADDRESS(String USER_DTL_ADDRESS) {
            this.USER_DTL_ADDRESS = USER_DTL_ADDRESS;
        }

        public String getALIAS() {
            return ALIAS;
        }

        public void setALIAS(String ALIAS) {
            this.ALIAS = ALIAS;
        }

        public String getUSER_BASE_ADDRESS() {
            return USER_BASE_ADDRESS;
        }

        public void setUSER_BASE_ADDRESS(String USER_BASE_ADDRESS) {
            this.USER_BASE_ADDRESS = USER_BASE_ADDRESS;
        }

        public String getHOME_NO() {
            return HOME_NO;
        }

        public void setHOME_NO(String HOME_NO) {
            this.HOME_NO = HOME_NO;
        }

        public String getACC_ID() {
            return ACC_ID;
        }

        public void setACC_ID(String ACC_ID) {
            this.ACC_ID = ACC_ID;
        }
    }


    /**
     * RetrofitAddressVo - 주소
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrofitAddressVo {

        @SerializedName("base_roadname_addr") @Expose
        public String baseRoadnameAddr;

        @SerializedName("roadname_zip_code") @Expose
        public String roadnameZipCode;

        @SerializedName("full_roadname_addr") @Expose
        public String fullRoadnameAddr;

        @SerializedName("full_lotnumber_addr") @Expose
        public String fullLotnumberAddr;

        @SerializedName("address_ref") @Expose
        public String addressRef;

        public String getBaseRoadnameAddr() {
            return baseRoadnameAddr;
        }

        public void setBaseRoadnameAddr(String baseRoadnameAddr) {
            this.baseRoadnameAddr = baseRoadnameAddr;
        }

        public String getRoadnameZipCode() {
            return roadnameZipCode;
        }

        public void setRoadnameZipCode(String roadnameZipCode) {
            this.roadnameZipCode = roadnameZipCode;
        }

        public String getFullRoadnameAddr() {
            return fullRoadnameAddr;
        }

        public void setFullRoadnameAddr(String fullRoadnameAddr) {
            this.fullRoadnameAddr = fullRoadnameAddr;
        }

        public String getFullLotnumberAddr() {
            return fullLotnumberAddr;
        }

        public void setFullLotnumberAddr(String fullLotnumberAddr) {
            this.fullLotnumberAddr = fullLotnumberAddr;
        }

        public String getAddressRef() {
            return addressRef;
        }

        public void setAddressRef(String addressRef) {
            this.addressRef = addressRef;
        }
    }



    /**
     * RetrofitPrintLogVo - 출력내역
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrofitPrintLogVo {

        @SerializedName("timestamp") @Expose
        public Long timestamp;

        @SerializedName("courier_code") @Expose
        public String courier_code;

        @SerializedName("customer_name") @Expose
        public String customer_name;

        @SerializedName("target_hn") @Expose
        public String target_hn;

        @SerializedName("is_favorite") @Expose
        public boolean is_favorite;

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public String getCourier_code() {
            return courier_code;
        }

        public void setCourier_code(String courier_code) {
            this.courier_code = courier_code;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getTarget_hn() {
            return target_hn;
        }

        public void setTarget_hn(String target_hn) {
            this.target_hn = target_hn;
        }

        public boolean isIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(boolean is_favorite) {
            this.is_favorite = is_favorite;
        }
    }


    public Integer getRspCode() {
        return rspCode;
    }

    public void setRspCode(Integer rspCode) {
        this.rspCode = rspCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAlert_msg() {
        return alert_msg;
    }

    public void setAlert_msg(String alert_msg) {
        this.alert_msg = alert_msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getAccessToken() {
        return data.accessToken;
    }

    public void setAccessToken(String accessToken) {
        data.accessToken = accessToken;
    }

    public String getAccId() {
        return data.acc_id;
    }

    public void setAccId(String acc_id) {
        data.acc_id = acc_id;
    }

    public String getDebug_msg() {
        return debug_msg;
    }

    public void setDebug_msg(String debug_msg) {
        this.debug_msg = debug_msg;
    }

    /**
     * RecommendVo
     */
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendVo {
        @SerializedName("usrNm") @Expose
        public String usrNm;

        @SerializedName("accTp") @Expose
        public String accTp;

        @SerializedName("usrId") @Expose
        public String usrId;

        @SerializedName("creDt") @Expose
        public String creDt;

        public String getUsrNm() {
            return usrNm;
        }

        public void setUsrNm(String usrNm) {
            this.usrNm = usrNm;
        }

        public String getAccTp() {
            return accTp;
        }

        public void setAccTp(String accTp) {
            this.accTp = accTp;
        }

        public String getUsrId() {
            return usrId;
        }

        public void setUsrId(String usrId) {
            this.usrId = usrId;
        }

        public String getCreDt() {
            return creDt;
        }

        public void setCreDt(String creDt) {
            this.creDt = creDt;
        }
    }


    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyHomeNumberVo {
        @SerializedName("nomalHn") @Expose
        public String nomalHn;

        @SerializedName("goldHn") @Expose
        public String goldHn;

        @SerializedName("blngHn") @Expose
        public String blngHn;

        public String getNomalHn() {
            return nomalHn;
        }

        public void setNomalHn(String nomalHn) {
            this.nomalHn = nomalHn;
        }

        public String getGoldHn() {
            return goldHn;
        }

        public void setGoldHn(String goldHn) {
            this.goldHn = goldHn;
        }

        public String getBlngHn() {
            return blngHn;
        }

        public void setBlngHn(String blngHn) {
            this.blngHn = blngHn;
        }
    }

}