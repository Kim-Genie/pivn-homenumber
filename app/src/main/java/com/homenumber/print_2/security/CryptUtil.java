package com.homenumber.print_2.security;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * 암호화 방식
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class CryptUtil {

	public static void main(String[] args) {
		CryptUtil cryptUtil = new CryptUtil();

		// 추후에 하고싶은 일을 작성하시오.
	}

    /**
     * 메시지를 SHA-256 알고리즘으로 해쉬한다.
     * @param message 원본메시지
     * @return 해쉬된 문자열
     ************************************************************************************************************************************************/
    public static String hashSHA256(String message) {
        return hash(message, "SHA-256");
    }

    public static String hashSHA256(String message, String salt) {
        return hash(message, salt, "SHA-256");
    }


    /**
     * 메시지를 주어진 알고리즘으로 해쉬한다.
     * @param message 원본메시지
     * @param algorithm 해쉬 알고리즘
     * @return 해쉬된 문자열
     ************************************************************************************************************************************************/
    private static String hash(String message, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();
            return new String(Hex.encodeHex(md.digest(message.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 메시지를 솔트를 사용하여 주어진 알고리즘으로 해쉬한다.
     * @param message 원본메시지
     * @param salt 솔트값
     * @param algorithm 해쉬 알고리즘
     * @return 해쉬된 문자열
     ************************************************************************************************************************************************/
    private static String hash(String message, String salt, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(salt.getBytes());
            return new String(Hex.encodeHex(md.digest(message.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
