package com.ueboot.shiro.shiro.handler;

import com.ueboot.core.http.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 这里的异常拦截要比ueboot-core里面的异常拦截高一个级别，防止无法拦截自定义异常
 * Created by Richard on 16/8/17.
 * @author yangkui
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.ueboot.shiro.controller"})
public class ShiroExceptionHandler {

    @ExceptionHandler(UnknownAccountException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(UnknownAccountException e) {
        log.error("进行登录验证..验证未通过,未知账户 {}",e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,未知账户", null);
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(IncorrectCredentialsException e) {
        log.error("用户名或密码错误:{}",e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "用户名或密码错误", null);
    }
    @ExceptionHandler(LockedAccountException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(LockedAccountException e) {
        log.error("进行登录验证..验证未通过,账户已锁定 {}",e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,账户已锁定", null);
    }
    @ExceptionHandler(ExcessiveAttemptsException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(ExcessiveAttemptsException e) {
        log.error("进行登录验证..验证未通过,错误次数过多 {}",e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "登录失败,密码错误次数过多", null);
    }

    @ExceptionHandler(AuthorizationException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(AuthorizationException e) {
        log.error("进行登录验证..验证未通过 {}",e.getMessage());
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "当前用户无权限访问", null);
    }

    @ExceptionHandler(AuthenticationException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(AuthenticationException e) {
        log.error(e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", e.getMessage(), null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleException(UnauthenticatedException e) {
        log.debug("{} was thrown", e.getClass(), e);
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "当前用户未登录或者无权限访问", null);
    }

}
