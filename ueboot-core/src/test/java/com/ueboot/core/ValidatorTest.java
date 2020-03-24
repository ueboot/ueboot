package com.ueboot.core;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author yangkui
 * createTime:2020/3/1012:28
 */
public class ValidatorTest {

    @Test
    public void test(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ReqBody reqBody = new ReqBody();
        Set<ConstraintViolation<Object>> validRetval = validator.validate(reqBody);
        StringBuilder sb = new StringBuilder();
        // 校验失败
        if (!validRetval.isEmpty()) {
            for (ConstraintViolation<Object> t : validRetval) {
                sb.append(t.getPropertyPath()).append(t.getMessage()).append(",");
            }
        }
        String checkError = sb.toString();
        System.out.println(checkError);
    }
}
