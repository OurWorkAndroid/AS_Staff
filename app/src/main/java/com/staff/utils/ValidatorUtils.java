package com.staff.utils;

import java.util.regex.Pattern;

/**
 * 正则表达校验器工具类
 */
public class ValidatorUtils {

    private ValidatorUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";//包括14 和17号段

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{17}[0-9,X,x])";


    /**
     * 验证码正则表达式
     */
    private static final String VERIFY_CODE="(^[a-z0-9A-Z]{6}$)";

    /***
     * 验证交易密码
     */

    private static  final  String  TRANS_PSW="^\\d{6}$";

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }


    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 验证是否是正确的验证码格式
     * @param verifyCode
     * @return
     */
    public static boolean isVerifyCode(String verifyCode){ return  Pattern.matches(VERIFY_CODE,verifyCode);}


    public  static  boolean isTransPassWord(String  passWord){
        return  Pattern.matches(TRANS_PSW,passWord);
    }

}
