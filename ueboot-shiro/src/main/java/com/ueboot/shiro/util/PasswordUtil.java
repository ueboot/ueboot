package com.ueboot.shiro.util;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

/**
 * 与shiro保持一致的加密方式，用于用户注册时对密码进行加密
 * 通过当前工具类加密的密码，与shiro登录时设置使用的加密策略一致
 * @author yangkui
 */
public class PasswordUtil {
    /**
     * 对密码进行sha512模式加密
     * @param userName 用户名（用来做salt）
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String  sha512(String userName,String password) {
        //以账号作为盐值
        ByteSource salt = ByteSource.Util.bytes(userName);
        //加密2次
        int hashIterations = 2;
        Sha512Hash hash = new Sha512Hash(password,salt,hashIterations);
        return hash.toString();
    }

}