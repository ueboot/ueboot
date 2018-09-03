package com.ueboot.core.exception;

import com.ueboot.core.http.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

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
public class WebExceptionHandler {


    @ExceptionHandler({BusinessException.class})
    @Order(1)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleBusinessException(BusinessException e) {
        return new Response<>(e.getCode() == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() + "" : e.getCode(), e.getMessage(), null);
    }


    @ExceptionHandler(value = {IllegalArgumentException.class})
    @Order(2)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleIllegalArgumentExceptions(final Exception e, final WebRequest req) {
        return new Response<>(HttpStatus.BAD_REQUEST.value() + "", e.getMessage(), null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @Order(value = Integer.MIN_VALUE+1)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response<Void> handleException(UnauthenticatedException e) {
        log.debug("{} was thrown", e.getClass(), e);
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", "当前用户未登录", null);
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
    @Order(Integer.MIN_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Response<Void> handleOtherExceptions(final Exception e, final WebRequest req) {
        //记录日志
        log.error(e.getMessage(), e);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "服务器系统异常", null);
    }
}
