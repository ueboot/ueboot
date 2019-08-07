package com.ueboot.core.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ueboot.core.annotation.XSSNotCheck;
import com.ueboot.core.exception.BusinessException;
import com.ueboot.core.service.HttpRequestValidatorService;
import com.ueboot.core.utils.XSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by yangkui on 16/12/19.
 * 拦截所有json格式提交的请求与返回数据打印日志
 *
 * @author yangkui
 * @version 1.0
 * - 增加对象格式校验
 * - 防止xss攻击，sql注入
 */
public class Log4jFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public final static Charset UTF8 = Charset.forName("UTF-8");

    private Charset charset = UTF8;
    private HttpRequestValidatorService httpRequestValidatorService;

    private SerializerFeature[] features =new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect};

    public Log4jFastJsonHttpMessageConverter(HttpRequestValidatorService httpRequestValidatorService) {
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
        this.httpRequestValidatorService = httpRequestValidatorService;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];
        for (; ; ) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
        String jsonStr = new String(bytes);
        //防止xss攻击，sql注入
        XSSNotCheck notCheck = clazz.getAnnotation(XSSNotCheck.class);
        //加了注解则不仅限xss字段拦截
        if(notCheck==null){
            jsonStr = XSSUtil.checkXssStr(jsonStr);
        }else{
            log.warn("当前类:{}标注无需进行xss字段拦截，注意安全!",clazz.getName());
        }
        bytes = jsonStr.getBytes();
        log.info("Request Class:{},json:{}", clazz.getName(), jsonStr);

        Object reqBody = JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> validRetval = validator.validate(reqBody);
        StringBuilder sb = new StringBuilder();
        // 校验失败
        if (!validRetval.isEmpty()) {
            for (ConstraintViolation<Object> t : validRetval) {
                sb.append(t.getPropertyPath()).append(t.getMessage()).append(",");
            }
        }
        String checkError = sb.toString();
        if (!isEmpty(checkError)) {
            checkError = "请求参数格式校验不通过：" + checkError;
            throw new BusinessException(checkError);
        }
        httpRequestValidatorService.validator(jsonStr,clazz);

        return reqBody;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, features);
        if(!isEmpty(text)&&text.length()>1000){
            text = text.substring(0,1000)+"...";
        }
        log.info("Response:{}", text);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
    }

    private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}
