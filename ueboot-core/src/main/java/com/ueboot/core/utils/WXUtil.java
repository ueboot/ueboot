package com.ueboot.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 微信API接口相关工具类
 *
 * @author yangkui
 */
public class WXUtil {
    private static final Logger logger = LoggerFactory.getLogger(WXUtil.class);

    /**
     * 获取32位随机字符串
     * 作者: zhoubang
     * 日期：2015年6月26日 下午3:51:44
     *
     * @return 随机字符串
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    /**
     * 时间戳
     * 作者: zhoubang
     * 日期：2015年6月26日 下午3:52:08
     *
     * @return 时间戳字符串
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    private static String createTimeStamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


    /**
     * sign签名
     * 作者: zhoubang 日期：2015年6月10日 上午9:31:24
     *
     * @param characterEncoding 字符编码
     * @param parameters        参数列表
     * @param appSecret         微信支付商户平台当中自己设置的密钥
     * @return 签名串
     */
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters, String appSecret) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            /** 如果参数为key或者sign，则不参与加密签名 */
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        /** 支付密钥必须参与加密，放在字符串最后面 */
        sb.append("key=" + appSecret);
        logger.info("微信支付签名原始值:{}", sb.toString());
        /** 记得最后一定要转换为大写 */
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        logger.info("微信支付签名串:{}", sign);
        return sign;
    }

    /**
     * 生成JS SDK用到的签名
     *
     * @param nonce_str    随机字符串
     * @param timestamp    时间戳
     * @param jsapi_ticket 加密值
     * @param url          url地址
     * @return 签名串
     */
    public static String createJSSign(String nonce_str, String timestamp, String jsapi_ticket, String url) {
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        logger.info("生成JS SDK用到的签名,原始值:{}", string1);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.info("生成JS SDK用到的签名异常", e);
        } catch (UnsupportedEncodingException e) {
            logger.info("生成JS SDK用到的签名异常", e);
        }

        return signature;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 将请求参数转换为xml格式的string
     * 作者: zhoubang 日期：2015年6月10日 上午9:25:51
     *
     * @param parameters 参数列表
     * @return xml格式的字符串
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            String v = entry.getValue() + "";
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 发送xml格式数据到微信服务器 告知微信服务器回调信息已经收到。
     * 作者: zhoubang 日期：2015年6月10日 上午9:27:33
     *
     * @param return_code 返回代码
     * @param return_msg  返回内容
     * @return 返回给微信端的报文格式
     */
    public static String setResponseXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

}
