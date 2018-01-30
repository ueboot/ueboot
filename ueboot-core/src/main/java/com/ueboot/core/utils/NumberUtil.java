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
     * @param businessTypeStr
     * @param number
     * @return
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
     * @param number  不全数字
     * @param digit   保证为数
     * @param pattern 日期格式
     * @return
     */
    public static String getOrderNo(Long number, Integer digit, String pattern) {
        String date = new JDateTime().toString(pattern);
        String randomNoStr = String.format("%0" + digit + "d", number);
        String orderNo = date + randomNoStr;
        return orderNo;
    }


}