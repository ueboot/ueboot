package com.ueboot.cryption.utils;

import com.ueboot.cryption.constants.EncryptConstants;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * 对称加密/解密工具类
 */
public class EncryptAesUtils {
    /**
     * encrypt 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     * @return base64加密结果字符串
     * @throws Exception 未知运行时异常
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(EncryptConstants.ENCRYPT_TYPE_AES);
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(EncryptConstants.ALGORITHMS_TR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), EncryptConstants.ENCRYPT_TYPE_AES));
        byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);

    }

    /**
     * decrypt 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return 解密结果保持原有对象结果
     * @throws Exception 未知运行时异常
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(EncryptConstants.ENCRYPT_TYPE_AES);
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(EncryptConstants.ALGORITHMS_TR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), EncryptConstants.ENCRYPT_TYPE_AES));
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, EncryptConstants.ENCRYPT_KEY);
    }

    public static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, EncryptConstants.ENCRYPT_KEY);
    }
}