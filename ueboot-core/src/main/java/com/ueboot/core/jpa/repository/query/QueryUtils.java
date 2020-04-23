package com.ueboot.core.jpa.repository.query;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author xiangli.ma
 * @since 1.0
 */
public class QueryUtils {

    private static final String COUNT_QUERY_STRING = "select count(%s) from %s x";
    private static final String ORDER_BY = "order by ";
    private static final String GROUP_BY = "group by ";


    private static String getQueryString(String template, String entityName) {

        Assert.hasText(entityName, "Entity name must not be null or empty!");

        return String.format(template, entityName);
    }

    public static String getCountQueryString(String entityName) {

        String countQuery = String.format(COUNT_QUERY_STRING, "x", "%s");
        return getQueryString(countQuery, entityName);
    }

    public static String genCountQueryString(String queryString) {
        //count 时需要去掉order by ，防止子查询方式count会报错
        queryString = removeOrders(queryString);
        //group by 的时候需要使用子查询
        if(queryString.contains("union ") || queryString.contains(GROUP_BY) || queryString.contains(GROUP_BY.toUpperCase())){
            return "select count(1) from ("+queryString+") a";
        }
        return "select count(*) " + removeSelect(queryString);
    }


    /**
     * <pre>
     * 去除JPQL语句前的select部分，用来生成查询总记录条数的HQL语句。
     *
     * <strong>程序范例：</strong>
     * String queryCountString = "select count(*) " + QueryUtils.removeSelect(queryString);
     *
     * </pre>
     * @param queryString  查询语句
     * @return  查询语句
     */
    public static String removeSelect(String queryString) {
        Assert.notNull(queryString);
        queryString = removeFetch(queryString);
        queryString = removeOrderBy(queryString);
        int beginPos = queryString.toLowerCase().indexOf(" from ");
        Assert.isTrue(beginPos != -1, " the jpql : " + queryString + " must has a keyword 'from'");
        return queryString.substring(beginPos);
    }

    /**
     * 去掉orderBy语句,，用来生成查询总记录条数的HQL语句。
     * @param queryString  查询语句
     * @return  查询语句
     */
    public static String removeOrderBy(String queryString) {
        Assert.notNull(queryString);
        queryString = removeFetch(queryString);
        int beginPos = queryString.toLowerCase().indexOf("order by ");
        if(beginPos==-1){
            return queryString;
        }
        return queryString.substring(0,beginPos);
    }
    private static final  Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);

    /**
     * <pre>
     * 去除HQL语句后的order by部分
     *
     * <strong>程序范例：</strong>
     * queryCountString = HqlUtils.removeOrders(queryCountString);
     *
     * </pre>
     * @param queryString  查询语句
     * @return  查询语句
     */
    public static String removeOrders(String queryString) {
        Matcher matcher = pattern.matcher(queryString);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "");
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * <pre>
     * 去除JPQL语句内的fetch部分
     *
     * <strong>程序范例：</strong>
     * queryString = removeFetch(queryString);
     *
     * </pre>
     * @param queryString  查询语句
     * @return  查询语句
     */
    public static String removeFetch(String queryString) {
        Assert.notNull(queryString);
        return StringUtils.delete(queryString, " fetch");
    }

}
