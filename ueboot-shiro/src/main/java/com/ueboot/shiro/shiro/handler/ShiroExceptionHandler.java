package com.ueboot.shiro.shiro.handler;

import com.ueboot.core.http.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Richard on 16/8/17.
 * @author yangkui
 */
@Slf4j
@ControllerAdvice
public class ShiroExceptionHandler {

    @ExceptionHandler(UnknownAccountException.class)
    @Order(value = Integer.MIN_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(UnknownAccountException e) {
        log.error("进行登录验证..验证未通过,未知账户");
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,未知账户", null);
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    @Order(value = Integer.MIN_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(IncorrectCredentialsException e) {
        log.error("进行登录验证..验证未通过,错误的凭证");
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,错误的凭证", null);
    }
    @ExceptionHandler(LockedAccountException.class)
    @Order(value = Integer.MIN_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(LockedAccountException e) {
        log.error("进行登录验证..验证未通过,账户已锁定");
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,账户已锁定", null);
    }
    @ExceptionHandler(ExcessiveAttemptsException.class)
    @Order(value = Integer.MIN_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(ExcessiveAttemptsException e) {
        log.error("进行登录验证..验证未通过,错误次数过多");
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,错误次数过多", null);
    }

    @ExceptionHandler(AuthorizationException.class)
    @Order(value = Integer.MIN_VALUE)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response<Void> handleException(AuthorizationException e) {
        log.error("进行登录验证..验证未通过,堆栈轨迹如下");
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "当前用户无权限访问", null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @Order(value = Integer.MIN_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(UnauthenticatedException e) {
        log.debug("{} was thrown", e.getClass(), e);
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", e.getMessage(), null);
    }

}
