package com.staff.utils;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 */
public class StringUtils {

    private StringUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断字符串是否为空
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    /**
     * 通过{n},格式化.
     *
     * @param src
     * @param objects
     * @return
     */
    public static String format(String src, Object... objects) {
        int k = 0;
        for (Object obj : objects) {
            src = src.replace("{" + k + "}", obj.toString());
            k++;
        }
        return src;
    }

    /**
     * parse String to int
     *
     * @param str
     * @param defaultValue
     * @return int
     */
    public static int parseInt(String str, int defaultValue) {
        int value = defaultValue;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * parse String to float
     *
     * @param str
     * @param defaultValue
     * @return float
     */
    public static float parseFloat(String str, float defaultValue) {
        float value = defaultValue;
        try {
            value = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     *
     * float保留小数点位数函数
     *
     * @param value
     * @param format
     *            保留小数格式
     * @return
     */
    /**
     * float保留小数点位数函数
     *
     * @param value
     * @param decimal
     * @return
     */
    public static String decimalFormat(float value, String decimal) {
        return new DecimalFormat(decimal).format(value);
    }

    /**
     * 格式化金额显示，小数点前面数字每隔3位添加“,”号
     *
     * @param value
     * @return
     */
    public static String moneyFormat(String value) {
        String str = "0.00";
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            BigDecimal bigDecimal = new BigDecimal(value.replace(",", ""));
            str = decimalFormat.format(bigDecimal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 格式化金额显示，整数前面数字每隔3位添加“,”号
     *
     * @param value
     * @return
     */
    public static String moneyIntFormat(String value) {
        String str = "0";
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            BigDecimal bigDecimal = new BigDecimal(value.replace(",", ""));
            str = decimalFormat.format(bigDecimal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 替换target中[start, end]区间的所有字符，用*代替: 掩码
     *
     * @param target
     * @param start
     * @param end
     * @return
     */
    public static String stringMask(String target, int start, int end) {
        if (TextUtils.isEmpty(target) || start < 0 || end - start < 0 || end >= target.length()) {
            // 如果区间不合法，返回原字符串
            return target;
        }
        StringBuffer sb = new StringBuffer(target);
        for (int i = 0; i < end - start + 1; i++) {
            sb.replace(start + i, start + i + 1, "*");
        }
        return sb.toString();
    }

    /**
     * 根据width 和 Paint 分割字符串
     *
     * @param content
     * @param p
     * @param width
     * @return
     */
    public static String[] autoSplit(String content, Paint p, float width) {
        int length = content.length();
        float textWidth = p.measureText(content);
        if (textWidth <= width) {
            return new String[]{content};
        }

        int start = 0, end = 1, i = 0;
        int lines = (int) Math.ceil(textWidth / width);
        String[] lineTexts = new String[lines];
        while (start < length) {
            if (p.measureText(content, start, end) > width) {
                lineTexts[i++] = (String) content.subSequence(start, end);
                start = end;
            }
            if (end == length) {
                lineTexts[i] = (String) content.subSequence(start, end);
                break;
            }
            end += 1;
        }
        return lineTexts;
    }


    /**
     * 根据Paint 获取字符高度
     *
     * @param paint
     * @return
     */
    public static int getStringHeight(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * URL拼接参数
     *
     * @param map
     * @return
     */
    public static String convertToUrlParams(HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    /**
     * 添加千位分隔符 + 小数点右上标
     *
     * @param param
     * @return
     */
    public static SpannableString convertToSuperscript(double param) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String temp = df.format(param);
        if (temp == null) {
            return null;
        }
        if (temp.length() < 4) {
            temp = "0" + temp;
        }

        int index = temp.lastIndexOf(".") + 1;
        temp = temp.substring(0, index + 2);

        SpannableString spannableString = new SpannableString(temp);
        spannableString.setSpan(new RelativeSizeSpan(0.58f), index, temp.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new SuperscriptSpan(), index, temp.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 将字符串转换成数值+单位下标的形式
     *
     * @param str 字符串
     * @return
     */
    public static SpannableString convertToSubscript(String str) {

        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(str);
        int index = -1;
        while (matcher.find()) {
            index = matcher.end();
        }
        if (index > 0) {
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new RelativeSizeSpan(0.58f), index, str.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        } else {
            return new SpannableString(str);
        }

    }

    /**
     * 获取一定长度的随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        int index;
        for (int i = 0; i < length; i++) {
            index = (int) (Math.round(Math.random() * (len - 1)));
            sb.append(str.charAt(index));
        }
        return sb.toString();
    }
}
