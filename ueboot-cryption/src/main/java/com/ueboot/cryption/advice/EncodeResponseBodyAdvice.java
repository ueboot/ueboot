package com.ueboot.cryption.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ueboot.cryption.annotation.AesSecurityParameter;
import com.ueboot.cryption.annotation.RsaSecurityParameter;
import com.ueboot.cryption.annotation.SecurityParameter;
import com.ueboot.cryption.utils.EncryptAesUtils;
import com.ueboot.cryption.utils.EncryptRsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * 响应数据对象加密
 */
@ControllerAdvice
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(EncodeResponseBodyAdvice.class);

    @Value("${cryption.rsa.public.key}")
    private String CLIENT_PUBLIC_KEY;

    @Value("${cryption.aes.private.key}")
    private String AES_PRIVATE_KEY;

    @Override
    public boolean supports(MethodParameter methodParameter, Class clazz) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class clazz, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(AesSecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            AesSecurityParameter serializedField = methodParameter.getMethodAnnotation(AesSecurityParameter.class);
            //出参是否需要加密
            assert serializedField != null;
            if (serializedField.outEncode()) {
                logger.debug("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
                return encodeAes(methodParameter, body);
            }
        }
        if (methodParameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            RsaSecurityParameter serializedField = methodParameter.getMethodAnnotation(RsaSecurityParameter.class);
            //出参是否需要加密
            assert serializedField != null;
            if (serializedField.outEncode()) {
                logger.debug("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
                return encodeRsa(methodParameter, body);
            }
        }
        if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            SecurityParameter serializedField = methodParameter.getMethodAnnotation(SecurityParameter.class);
            //出参是否需要加密
            assert serializedField != null;
            if (serializedField.outEncode()) {
                logger.debug("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
                return encodeAesRsa(methodParameter, body);
            }
        }
        return body;
    }


    /**
     * AES加密
     *
     * @param methodParameter 方法参数
     * @param body            操作对象体
     * @return AES加密结果对象
     */
    private Object encodeAes(MethodParameter methodParameter, Object body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            // aes加密
            String requestData = EncryptAesUtils.encrypt(result, AES_PRIVATE_KEY);
            Map<String, String> map = new HashMap<>();
            map.put("requestData", requestData);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + Objects.requireNonNull(methodParameter.getMethod()).getName() + "】返回数据进行解密出现异常：" + e.getMessage());
        }
        return body;
    }

    /**
     * RSA加密
     *
     * @param methodParameter 方法参数
     * @param body            操作对象体
     * @return RSA加密结果对象
     */
    private Object encodeRsa(MethodParameter methodParameter, Object body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            // rsa加密
            String requestData = EncryptRsaUtils.encryptedDataOnJava(result, CLIENT_PUBLIC_KEY);
            Map<String, String> map = new HashMap<>();
            map.put("requestData", requestData);
            return map;
        } catch (Exception e) {
            logger.error("对方法method :【" + Objects.requireNonNull(methodParameter.getMethod()).getName() + "】返回数据进行解密出现异常：" + e.getMessage());
        }
        return body;
    }

    /**
     * 混合加密
     *
     * @return AES加密结果对象
     */
    private Object encodeAesRsa(MethodParameter methodParameter, Object body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            // 生成aes秘钥
            String aseKey = getRandomString(16);
            // rsa加密
            String encrypted = EncryptRsaUtils.encryptedDataOnJava(aseKey, CLIENT_PUBLIC_KEY);
            // aes加密
            String requestData = EncryptAesUtils.encrypt(result, aseKey);
            Map<String, String> map = new HashMap<>();
            map.put("encrypted", encrypted);
            map.put("requestData", requestData);
            return map;
        } catch (Exception e) {
            logger.error("对方法method :【{}】返回数据进行解密出现异常：{}", Objects.requireNonNull(methodParameter.getMethod()).getName(), e);
        }
        return body;
    }


    /**
     * 创建指定位数的随机字符串
     *
     * @param length 表示生成字符串的长度
     * @return 字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}