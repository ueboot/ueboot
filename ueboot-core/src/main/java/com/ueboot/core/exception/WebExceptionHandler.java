package com.ueboot.core.exception;

import com.ueboot.core.http.response.Response;
import lombok.extern.slf4j.Slf4j;
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
 * @author yangkui
 * 全局异常拦截
 */
@Slf4j
@ControllerAdvice
public class WebExceptionHandler {


    @ExceptionHandler({BusinessException.class})
    @Order(1)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleBusinessException(BusinessException e) throws IOException {
        return new Response<>(e.getCode() == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() + "" : e.getCode(), e.getMessage(), null);
    }



    @ExceptionHandler(value = {IllegalArgumentException.class})
    @Order(2)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleIllegalArgumentExceptions(final Exception e, final WebRequest req) {
        return new Response<>(HttpStatus.BAD_REQUEST.value() + "", e.getMessage(), null);
    }

    /**
     * 全局处理Exception
     * 错误的情况下返回500
     * @param e
     * @param req
     * @return
     */
    /*@ExceptionHandler(value = {Exception.class})
    @Order(100)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Void> handleOtherExceptions(final Exception e, final WebRequest req) {
        //记录日志
        log.error(e.getMessage(), e);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "请求异常", null);
    }*/
}
