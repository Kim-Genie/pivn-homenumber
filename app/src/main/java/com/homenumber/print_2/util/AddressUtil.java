package com.homenumber.print_2.util;

/**
 * 시도
 */
public class AddressUtil {


	/**
	 * return address
	 ************************************************************************************************************************************************/
	public static String sido(String str) {
		String originalSido = str;
		String sido = str.substring(0,2);
		String sigungu = str.substring(2);
		if(sido.equals("서울")){
			originalSido = sido + "특별시"+sigungu;
		}else if(sido.equals("제주") || sido.equals("세종") ){

		}else if(sido.equals("부산") ||sido.equals("대구") || sido.equals("인천") || sido.equals("광주") ||sido.equals("대전") || sido.equals("울산")){
			originalSido = sido + "광역시"+sigungu;
		}else if(sido.equals("경기") ||sido.equals("강원")  ){
			originalSido = sido + "도"+sigungu;
		}else if(sido.equals("충북")){
			originalSido = "충청북도"+sigungu;
		}else if(sido.equals("충남")){
			originalSido = "충청남도"+sigungu;
		}else if(sido.equals("전북")){
			originalSido = "전라북도"+sigungu;
		}else if(sido.equals("전남")){
			originalSido = "전라남도"+sigungu;
		}else if(sido.equals("경북")){
			originalSido = "경상북도"+sigungu;
		}else if(sido.equals("경남")){
			originalSido = "경상남도"+sigungu;
		}
		return  originalSido;
	}

	public static String bcodeNo(String str) {
		str = str.substring(str.length() -2, str.length());

		return  str;
	}
	
}
