package com.ueboot.core.service;

import javax.validation.ConstraintViolation;
import java.util.Set;

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

    /**
     * 处理校验结果，对框架校验过的结果做额外的判断。
     * 如果返回true,则框架不再自动抛出异常，否则使用框架的异常处理逻辑（对外抛出500异常）
     * @param violations 所有校验结果
     * @return 是否由实现类处理，框架不再处理
     */
    default boolean doValidatorMsg(Set<ConstraintViolation<Object>> violations){
        return false;
    }


}
