package com.ueboot.shiro.shiro.credential;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class RetryCount implements Serializable {

    private AtomicInteger retryCount;

    public RetryCount() {
        this.retryCount =new AtomicInteger(0);
    }

    public RetryCount(AtomicInteger retryCount) {
        this.retryCount = retryCount;
    }

    public RetryCount incrementAndGet(){
        retryCount.incrementAndGet();
        return this;
    }
}
