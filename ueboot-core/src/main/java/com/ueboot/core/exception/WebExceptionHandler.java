package com.ueboot.core.exception;

import com.ueboot.core.http.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by Richard on 16/8/17.
 * 所有异常在http层面的状态返回都是200，但是返回报文体里面的code为异常代码如：401，500，403等
 *
 * @author yangkui
 * 全局异常拦截
 */
@Slf4j
@ControllerAdvice
@ConditionalOnMissingBean(name = "customWebExceptionHandler")
public class WebExceptionHandler {

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public Response<Void> handleException(UnknownAccountException e) {
        log.error("进行登录验证..验证未通过,未知账户 {}",e.getMessage());
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "验证未通过,未知账户", null);
    }

    @ExceptionHandler(DisabledAccountException.class)
    @ResponseBody
    public Response<Void> handleException(DisabledAccountException e) {
        log.error("进行登录验证..用户已被禁用{}",e.getMessage());
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "该账号已被禁用，请联系管理员", null);
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public Response<Void> handleException(IncorrectCredentialsException e) {
        log.error("用户名或密码错误:{}",e.getMessage());
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "用户名或密码错误", null);
    }
    @ExceptionHandler(LockedAccountException.class)
    @ResponseBody
    public Response<Void> handleException(LockedAccountException e) {
        log.error("进行登录验证..验证未通过,账户已锁定 {}",e.getMessage());
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value()+ "", "您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理", null);
    }
    @ExceptionHandler(ExcessiveAttemptsException.class)
    @ResponseBody
    public Response<Void> handleException(ExcessiveAttemptsException e) {
        log.error("进行登录验证..验证未通过,错误次数过多 {}",e.getMessage());
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "登录信息已累计输错5次，您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理", null);
    }
    //无权限的请求，返回403，前端会进行页面提示无权限访问
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response<Void> handleException(AuthorizationException e) {
        log.error("权限验证未通过 {}",e.getMessage());
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "当前用户无权限访问", null);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(AuthenticationException e) {
        log.error(e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", e.getMessage(), null);
    }
    //无权限的请求，返回401，前端会进行页面跳转到登录页面
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(UnauthenticatedException e) {
        log.debug("{} was thrown", e.getClass(), e);
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "当前用户未登录", null);
    }


    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleBusinessException(BusinessException e) {
        return new Response<>(e.getCode() == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() + "" : e.getCode(), e.getMessage(), null);
    }


    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<Void> handleIllegalArgumentExceptions(final Exception e, final WebRequest req) {
        return new Response<>(HttpStatus.BAD_REQUEST.value() + "", e.getMessage(), null);
    }


    /**
     * 全局处理Exception
     * 错误的情况下返回500
     *
     * @param e   异常信息
     * @param req 请求信息
     * @return 全局的错误提示
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleOtherExceptions(final Exception e, final WebRequest req) {
        //记录日志
        log.error(req.getContextPath()+":"+e.getMessage(), e);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "服务器系统异常", null);
    }
}
