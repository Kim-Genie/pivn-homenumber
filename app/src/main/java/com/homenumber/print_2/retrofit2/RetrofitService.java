package com.homenumber.print_2.retrofit2;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 통신 인터페이스
 * @author 나비이쁜이
 * @since 2019.04.22
 */
public interface RetrofitService {

    /**
     * 로그인
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("auth/login/basic")
    Call<RetrofitItemVo> requestLogin(
            @FieldMap HashMap<String, Object> params);

    /**
     * 자동 로그인
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("auth/login/auto")
    Call<RetrofitItemVo> requestAutoLogin(
            @FieldMap HashMap<String, String> params
    );

    /**
     * 로그아웃
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("auth/logout")
    Call<RetrofitItemVo> requestLogout(
            @FieldMap HashMap<String, String> params
    );

    /**
     * 홈넘버 이름 요청
     ************************************************************************************************************************************************/
    @GET("data/customer/name")
    Call<RetrofitItemVo> requestHomenumberName(
            @Query ("homeNo") String target_hn
    );

    /**
     * CVSNET 예약번호 생성
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("cvsnet/malls/homenumber/orders")
//    @POST("{projectCode}/malls/{mallCode}")
    Call<RetrofitItemVo> requestCVSNET(
            @FieldMap HashMap<String, Object> params
    );

    /**
     * CVSNET 예약번호 목록
     ************************************************************************************************************************************************/
    @GET("cvsnet/malls/homenumber/orders")
//    @POST("{projectCode}/malls/{mallCode}")
    Call<RetrofitItemVo> requestOrderList(
            @Query ("accId") String accId
    );


    /**
     * SMS 요청
     ************************************************************************************************************************************************/
    @GET("auth/smsValidation/request")
    Call<RetrofitItemVo> requesSMSRequset(
            @Query ("usrId") String target_pn);


    /**
     * SMS 인증
     ************************************************************************************************************************************************/
    @GET("auth/smsValidation/check")
    Call<RetrofitItemVo> requestHomenumberMemberJoinSMSCheck(
            @Query ("usrId") String target_pn,
            @Query ("authNo") String validation_code);


    /**
     * 도로명 주소 찾기
     ************************************************************************************************************************************************/
    @GET("data/address/roadname")
    Call<RetrofitItemVo> requestHomenumberMemberJoinDoro(
            @Query ("sido") String sido,
            @Query ("sigungu") String sigungu,
            @Query ("roadname") String roadname,
            @Query ("buildingName") String building_name);

    /**
     * 지번명 주소 찾기
     ************************************************************************************************************************************************/
    @GET("data/address/lotnumber")
    Call<RetrofitItemVo> requestHomenumberMemberJoinJibun(
            @Query ("sido") String sido,
            @Query ("sigungu") String sigungu,
            @Query ("lotname") String lotname,
            @Query ("buildingName") String building_name);

    /**
     * 회원가입
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("request/uesr/join/old")
    Call<RetrofitItemVo> requestHomenumberMemberJoin(
            @FieldMap HashMap<String, Object> params);


    /**
     * 회뭔 비밀번호 변경 문자 인증
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("settings/usrPwd/homeNo/mobile")
    Call<RetrofitItemVo> requestChangePwSMS(
            @FieldMap HashMap<String, String> params
    );

    /**
     * 회뭔 비밀번호 변경
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("settings/usrPwd/newPwd")
    Call<RetrofitItemVo> requestChangePW(
            @FieldMap HashMap<String, Object> params
    );
    /**
     * 내정보 가져오기
     ************************************************************************************************************************************************/
    @GET("settings/usrHNinfo/request")
    Call<RetrofitItemVo> requestUserHnInfo(

    );
    /**
     * 골드홈넘버 중복체크
     ************************************************************************************************************************************************/
    @GET("request/gh/duplicate")
    Call<RetrofitItemVo> requestDuplicate(
            @Query ("smtAddr") String smtAddr
    );

    /**
     * 내정보수정
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("settings/usrHNinfo/update")
    Call<RetrofitItemVo> updateUserInfo(
            @FieldMap HashMap<String,ArrayList<JSONObject>> params
    );

    /**
     * 홈넘버 삭제
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("settings/usrHNinfo/delete")
    Call<RetrofitItemVo> deleteHn(
            @FieldMap HashMap<String, String> params
    );

    /**
     * 보안키
     ************************************************************************************************************************************************/
    @PATCH("hninfo/secretkey")
    Call<RetrofitItemVo> modifyAuthKey(
            @Query ("homeNumber") String homeNumber,
            @Query ("homeNumberPassword") String homeNumberPassword
    );

    /**
     * 추천인 조회
     ************************************************************************************************************************************************/
    @GET("data/rcmn/people")
    Call<RetrofitItemVo> recommendPeople(
            @Query ("usrId") String usrId
    );

    /**
     * 단축 URL조회
     ************************************************************************************************************************************************/
    @FormUrlEncoded
    @POST("data/rcmn/url")
    Call<RetrofitItemVo> requestUrl(
            @FieldMap HashMap<String, String> params
    );

    @GET("data/myhn")
    Call<RetrofitItemVo> myHomeNumbers(
            @Query ("usrId") String usrId
    );



}
