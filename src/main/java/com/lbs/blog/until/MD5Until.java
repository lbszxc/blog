package com.lbs.blog.until;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Administrator
 * @date 2020/11/15 14:15
 * @description
 **/
public class MD5Until {

    public static String code(String str) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] byteDigest = messageDigest.digest();
            int i;
            StringBuffer stringBuffer = new StringBuffer("");
            for (int offset = 0;offset < byteDigest.length; offset++){
                i = byteDigest[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }

            //32位加密
            return stringBuffer.toString();
            //16位加密
            //return stringBuffer.toString().substring(4,28);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
