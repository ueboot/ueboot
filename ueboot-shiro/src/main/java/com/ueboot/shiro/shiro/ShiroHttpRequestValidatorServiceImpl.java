package com.ueboot.shiro.shiro;

import com.ueboot.core.service.HttpRequestValidatorService;
import lombok.extern.slf4j.Slf4j;
import net.hasor.dataway.spi.ApiInfo;
import net.hasor.utils.future.BasicFuture;
import org.apache.shiro.SecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 使用shiro做权限判断
 * @author yangkui
 * createTime:2019-07-2211:32
 */
@Component
@Slf4j
public class ShiroHttpRequestValidatorServiceImpl implements HttpRequestValidatorService {
    @Override
    public void preExecute(ApiInfo apiInfo, BasicFuture<Object> future) {
        log.info("开始对{}路径做权限校验",apiInfo.getApiPath());
        String apiPath = apiInfo.getApiPath();
        //TODO 校验当前登录用户是否有权限访问当前apiPath
//        SecurityUtils.getSubject().checkPermission(apiPath);
    }
}
