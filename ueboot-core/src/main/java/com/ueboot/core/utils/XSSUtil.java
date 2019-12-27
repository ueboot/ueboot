package com.ueboot.core.utils;

import jodd.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @author kui.yang
 * 过滤非法字符，防止XSS攻击和SQL注入，对于SQL注入直接抛出异常，对于XSS攻击会进行转义（防止误判）。
 * SQL注入示例：  key=' update '
 */
public class XSSUtil {
    private static Logger logger = LoggerFactory.getLogger(XSSUtil.class);
    private static String injStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
            "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
            "table|from|grant|use|group_concat|column_name|" +
            "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
            "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉

    /**
     * 校验 是否是非法XSS攻击字符
     *
     * @param value 需要校验的字符串
     * @return 校验后的字符串
     */
    public static String checkXssStr(String value) {
        if (StringUtil.isNotBlank(value) && sqlInject(value)) {
            logger.error("提交参数存在非法字符！,value:{}", value);
            throw new RuntimeException("提交参数存在非法字符！value:" + value);
        }
        value = scriptingFilter(value);
        value = StringEscapeUtils.escapeHtml(value);
        return value;
    }

    /**
     * SQL注入检测
     *
     * @param str 需要校验的字符串
     * @return 校验过后的字符串
     */
    private static boolean sqlInject(String str) {
        String[] injStra = injStr.split("\\|");

        for (String s : injStra) {
            if (str.toLowerCase().contains(" " + s + " ")) {
                return true;
            }
        }
        return false;
    }

    private static Pattern p1 = Pattern.compile("<script.*?>", Pattern.CASE_INSENSITIVE);
    private static Pattern p2 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern p3 = Pattern.compile("<iframe.*?>", Pattern.CASE_INSENSITIVE);
    private static Pattern p4 = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);

    /**
     * 脚本注入检测
     *
     * @param value 需要校验的字符串
     * @return 校验过后的字符串
     */
    private static String scriptingFilter(String value) {
        String str = p1.matcher(value).replaceAll("&lt;/script&gt;");
        str = p2.matcher(str).replaceAll("&lt;/script&gt;");
        str = p3.matcher(str).replaceAll("&lt;/iframe&gt;");
        str = p4.matcher(str).replaceAll("&lt;/iframe&gt;");
        return str;
    }


}

