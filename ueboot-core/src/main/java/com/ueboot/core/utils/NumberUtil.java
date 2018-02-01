/*
 * Copyright (c)  2016, 
 * All rights reserved.
 * NumberUtil.java 2016-05-09 下午7:47
 */

package com.ueboot.core.utils;

import jodd.datetime.JDateTime;

/**
 * 描述:数字字符格式化工具，用于生成固定长度的数字字符
 *
 * @author yangkui create on 2016-05-09.
 * @since 1.0
 */
public class NumberUtil {

    /**
     * 生成指定的位数编号,不足补零
     *
     * @param businessTypeStr 业务类型，可以为空
     * @param number 数字
     * @return 格式化后的数字字符串，默认为8位长度
     */
    public static String getStringNumber(String businessTypeStr, Long number) {
        String randomNoStr = String.format("%08d", number);
        return businessTypeStr + randomNoStr;
    }

    public static String getOrderNo(Long number) {
        String date = new JDateTime().toString("YYYYMMDD");
        String randomNoStr = String.format("%010d", number);
        return  date + randomNoStr;
    }

    /**
     * 使用日期+补位+数字的方式格式化获取字符串
     * @param number  需要补全数字
     * @param digit   保证为数
     * @param pattern 日期格式
     * @return 格式化后的字符串
     */
    public static String getOrderNo(Long number, Integer digit, String pattern) {
        String date = new JDateTime().toString(pattern);
        String randomNoStr = String.format("%0" + digit + "d", number);
        String orderNo = date + randomNoStr;
        return orderNo;
    }


}