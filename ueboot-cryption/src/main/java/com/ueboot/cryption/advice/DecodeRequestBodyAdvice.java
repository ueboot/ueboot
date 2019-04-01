package com.ueboot.cryption.advice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ueboot.core.exception.BusinessException;
import com.ueboot.cryption.annotation.AesSecurityParameter;
import com.ueboot.cryption.annotation.RsaSecurityParameter;
import com.ueboot.cryption.annotation.SecurityParameter;
import com.ueboot.cryption.utils.EncryptAesUtils;
import com.ueboot.cryption.utils.EncryptRsaUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 服务请求数据对象解密
 */
@ControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

    @Value("${cryption.rsa.private.key}")
    private String SERVER_PRIVATE_KEY;

    @Value("${cryption.aes.private.key}")
    private String AES_PRIVATE_KEY;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = Objects.requireNonNull(methodParameter.getMethod());
        try {
            if (method.isAnnotationPresent(AesSecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                AesSecurityParameter serializedField = methodParameter.getMethodAnnotation(AesSecurityParameter.class);
                //入参是否需要解密
                assert serializedField != null;
                if (serializedField.inDecode()) {
                    logger.debug("注解AesSecurityParameter,对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
                    return new AesHttpInputMessage(inputMessage);
                }
            }
            if (methodParameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                RsaSecurityParameter serializedField = methodParameter.getMethodAnnotation(RsaSecurityParameter.class);
                //入参是否需要解密
                assert serializedField != null;
                if (serializedField.inDecode()) {
                    logger.debug("注解RsaSecurityParameter,对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
                    return new RsaHttpInputMessage(inputMessage);
                }
            }
            if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                SecurityParameter serializedField = methodParameter.getMethodAnnotation(SecurityParameter.class);
                //入参是否需要解密
                assert serializedField != null;
                if (serializedField.inDecode()) {
                    logger.debug("注解SecurityParameter,对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
                    return new MyHttpInputMessage(inputMessage);
                }
            }
            return inputMessage;
        } catch (Exception e) {
            logger.error("对方法method :【{}】返回数据进行解密出现异常：{}", method.getName(), e.getMessage());
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(easpString(IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        public String easpString(String requestData) {
            if (requestData != null && !requestData.equals("")) {
                Map<String, String> requestMap = new Gson().fromJson(requestData, new TypeToken<Map<String, String>>() {
                }.getType());
                // 密文
                String data = requestMap.get("requestData");
                // 加密的ase秘钥
                String encrypted = requestMap.get("encrypted");
                if (StringUtils.isEmpty(data) || StringUtils.isEmpty(encrypted)) {
                    throw new BusinessException("参数【requestData】缺失异常！");
                } else {
                    String content;
                    String aseKey;
                    try {
                        aseKey = EncryptRsaUtils.decryptDataOnJava(encrypted, SERVER_PRIVATE_KEY);
                    } catch (Exception e) {
                        throw new BusinessException("参数【aseKey】解析异常！");
                    }
                    try {
                        content = EncryptAesUtils.decrypt(data, aseKey);
                    } catch (Exception e) {
                        throw new BusinessException("参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content) || StringUtils.isEmpty(aseKey)) {
                        throw new BusinessException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new BusinessException("参数【requestData】不合法异常！");
        }
    }

    class RsaHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public RsaHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(easpString(IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        public String easpString(String requestData) {
            Optional<String> optional = Optional.ofNullable(requestData);
            if (optional.isPresent()) {
                Map<String, String> requestMap = new Gson().fromJson(optional.get(), new TypeToken<Map<String, String>>() {
                }.getType());
                // 密文
                String data = requestMap.get("requestData");
                if (StringUtils.isEmpty(data)) {
                    throw new BusinessException("参数【requestData】缺失异常！");
                } else {
                    String content;
                    try {
                        content = EncryptRsaUtils.decryptDataOnJava(data, SERVER_PRIVATE_KEY);
                    } catch (Exception e) {
                        throw new BusinessException("参数【rsaKey】解析异常！");
                    }
                    if (StringUtils.isEmpty(content)) {
                        throw new BusinessException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new BusinessException("参数【requestData】不合法异常！");
        }
    }

    class AesHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;


        public AesHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(easpString(IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        public String easpString(String requestData) {
            Optional<String> optional = Optional.ofNullable(requestData);
            if (optional.isPresent()) {
                Map<String, String> requestMap = new Gson().fromJson(optional.get(), new TypeToken<Map<String, String>>() {
                }.getType());
                // 密文
                String data = requestMap.get("requestData");
                if (StringUtils.isEmpty(data)) {
                    throw new BusinessException("参数【requestData】缺失异常！");
                } else {
                    String content;
                    try {
                        content = EncryptAesUtils.decrypt(data, AES_PRIVATE_KEY);
                    } catch (Exception e) {
                        throw new BusinessException("参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content)) {
                        throw new BusinessException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new BusinessException("参数【requestData】不合法异常！");
        }
    }
}