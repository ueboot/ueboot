package com.ueboot.core.annotation;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author felix
 */
@Slf4j
public class UeLogAspect {

    /**
     * 定义切点 @Pointcut
     * 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.ueboot.core.annotation.UeLog)")
    public void logPointCut() {
    }

    /**
     * 切面 配置通知
     *
     * @param joinPoint 连接点
     */
    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取请求的类名
        String methodInfo = joinPoint.getTarget().getClass().getName()+"#" + method.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        //TODO 根据需求进行日志处理 可调用service保存SysLog实体类到数据库
        log.info("....logPointCut....{}", methodInfo);
    }
}
