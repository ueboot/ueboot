package com.ueboot.core.http.response;

import org.springframework.http.HttpStatus;


/**
 * Created by Neel on 2016/12/10.
 */
public class Response<T> {

    private String code;

    private String message;

    private String updateTime;

    private T body;


    public Response code(String code) {
        this.code = code;
        return this;
    }

    public Response body(T body) {
        this.body = body;
        return this;
    }

    public Response message(String message) {
        this.message = message;
        return this;
    }

    public Response time(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * 该构造方法默认code 为200
     */
    public Response() {
        this(HttpStatus.OK.name(), null);
    }

    /**
     * 该构造方法默认code 为200
     * @param
     */
    public Response(T body) {
        this(HttpStatus.OK.name(), body);
    }

    public Response(String code, T body) {
        this(code, null, body);
    }

    public Response(String code, String message, T body) {
        this.code = code;
        this.body = body;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}