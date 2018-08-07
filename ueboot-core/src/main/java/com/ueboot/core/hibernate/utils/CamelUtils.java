package com.ueboot.core.hibernate.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库字段转驼峰命令
 *
 * @author xiangli.ma
 * @date 2018/8/6
 */
public class CamelUtils {

    private static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        param = param.toLowerCase();
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static Pattern pattern = Pattern.compile("_");

    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        param = param.toLowerCase();
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = pattern.matcher(param);

        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(underlineToCamel("a_b_c"));
        System.out.println(underlineToCamel2("a_b_c"));
        System.out.println(underlineToCamel("A_b_c"));
        System.out.println(underlineToCamel("NAME"));
        System.out.println(underlineToCamel2("A_b_c"));
        System.out.println(underlineToCamel2("NAME"));
    }
}
