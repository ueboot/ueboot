package com.ueboot.cryption.constants;

public class EncryptConstants {
    public static final String ENCRYPT_TYPE_AES = "AES";
    public static final String ENCRYPT_TYPE_RSA = "RSA";
    public static final String ENCRYPT_TYPE_AES_RSA = "AES&RSA";

    public static final String ENCRYPT_KEY = "abcdef0123456789";
    //参数分别代表 算法名称/加密模式/数据填充方式
    public static final String ALGORITHMS_TR = "AES/ECB/PKCS5Padding";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
}
