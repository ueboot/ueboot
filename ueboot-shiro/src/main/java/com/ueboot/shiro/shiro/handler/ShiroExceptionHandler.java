package com.ueboot.shiro.shiro.handler;

import com.ueboot.core.http.response.Response;
import com.ueboot.shiro.shiro.ShiroEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;

/**
 * 这里的异常拦截要比ueboot-core里面的异常拦截高一个级别，防止无法拦截自定义异常
 *  返回401状态时，页面不重新跳转到登录页面
 *  返回403状态时，页面会自动跳转到登录页面
 * Created by Richard on 16/8/17.
 * @author yangkui
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.ueboot.shiro.controller"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

    private static ThreadLocal<String> currentUserName = new ThreadLocal<>();

    public static void set(String userName){
        currentUserName.set(userName);
    }

    public static String get(){
        return  currentUserName.get();
    }

    public static void remove(){
        currentUserName.remove();
    }
    @Resource
    private  ShiroEventListener shiroEventListener;

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(UnknownAccountException e) {
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        ShiroExceptionHandler.remove();
        log.error("进行登录验证..验证未通过,未知账户 {}",e.getMessage());
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "验证未通过,未知账户", null);
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(IncorrectCredentialsException e) {
        log.error("用户名或密码错误:{}",e.getMessage());
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        ShiroExceptionHandler.remove();
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "用户名或密码错误", null);
    }
    @ExceptionHandler(LockedAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(LockedAccountException e) {
        log.error("进行登录验证..验证未通过,账户已锁定 {}",e.getMessage());
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        ShiroExceptionHandler.remove();
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理", null);
    }
    @ExceptionHandler(ExcessiveAttemptsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(ExcessiveAttemptsException e) {
        log.error("进行登录验证..验证未通过,错误次数过多 {}",e.getMessage());
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        ShiroExceptionHandler.remove();
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", "登录信息已累计输错5次，您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理", null);
    }
    //无权限的请求，返回403，前端会进行页面跳转到登录页面
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response<Void> handleException(AuthorizationException e) {
        log.error("权限验证未通过 {}",e.getMessage());
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        ShiroExceptionHandler.remove();
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "当前用户无权限访问", null);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<Void> handleException(AuthenticationException e) {
        log.error(e.getMessage());
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        ShiroExceptionHandler.remove();
        return new Response<>(HttpStatus.UNAUTHORIZED.value() + "", e.getMessage(), null);
    }
    //无权限的请求，返回403，前端会进行页面跳转到登录页面
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response<Void> handleException(UnauthenticatedException e) {
        log.debug("{} was thrown", e.getClass(), e);
        ShiroExceptionHandler.remove();
        shiroEventListener.afterLogin(currentUserName.get(),false,e.getMessage());
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "当前用户未登录", null);
    }

}
