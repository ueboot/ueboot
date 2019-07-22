package com.ueboot.core.service;

/**
 * 对提交的json报文进行额外的统一校验，比如数据权限校验
 * @author yangkui
 * createTime:2019-07-2211:26
 */
public interface HttpRequestValidatorService {

    /**
     * 校验传入的json对象是否符合业务要求，如果不符合则直接抛出BusinessException异常
     * @param jsonStr 原始的jsonStr
     * @param tClass 需要转换的对象类
     */
    default void validator(String jsonStr,Class<? extends Object> tClass){

    }
}
