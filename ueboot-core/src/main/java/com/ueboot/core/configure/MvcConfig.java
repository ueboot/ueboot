package com.ueboot.core.configure;

import com.ueboot.core.http.annotation.PageableLimits;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 增加自定义注解PageableLimits的支持
 * 在Controller类当中的方法上增加注解即可实现@PageableLimits(maxSize = 10000)
 * @author yangkui
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver() {

            @Override
            public Pageable resolveArgument(MethodParameter methodParameter,  ModelAndViewContainer mavContainer,
                                            NativeWebRequest webRequest,  WebDataBinderFactory binderFactory) {
                Pageable p = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
                return getLimitsFromAnnotation(p, methodParameter);
            }

            private Pageable getLimitsFromAnnotation(Pageable p, MethodParameter methodParameter) {

                PageableLimits limits = methodParameter.getParameterAnnotation(PageableLimits.class);

                if (limits == null) {
                    return p;
                }
                if (p.getPageSize() > limits.maxSize()) {
                    return  PageRequest.of(p.getPageNumber(), limits.maxSize(), p.getSort());
                } else if (p.getPageSize() < limits.minSize()) {
                    return  PageRequest.of(p.getPageNumber(), limits.minSize(), p.getSort());
                }

                return p;
            }
        };
        //这里设置为最大值，目的是让默认的spring最大值校验通过。实则在生成Pageable对象时再改为设置的最大值
        resolver.setMaxPageSize(Integer.MAX_VALUE);
        argumentResolvers.add(resolver);
    }
}