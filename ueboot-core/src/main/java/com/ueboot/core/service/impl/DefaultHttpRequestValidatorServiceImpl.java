package com.ueboot.core.service.impl;

import com.ueboot.core.service.HttpRequestValidatorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 默认为空
 * @author yangkui
 * createTime:2019-07-2211:32
 */
@ConditionalOnMissingBean(name = "httpRequestValidatorService")
@Component
public class DefaultHttpRequestValidatorServiceImpl implements HttpRequestValidatorService {
}
