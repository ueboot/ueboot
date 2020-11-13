package com.ueboot.shiro.util;

import com.alibaba.druid.filter.config.ConfigTools;
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

    /**
     * 获取druid数据库密码加密值
     * @param dbPassword 需要加密的数据库密码
     * @return  数组下标0 privateKey 无用
     *          数组下标1 publicKey 用于 spring.datasource.druid.connection-properties:当中的config.decrypt.key
     *          数组下标2 password  用于 spring.datasource.password属性
     */
    public static String[] dbPassword(String dbPassword){
        try {
            String[] arr = ConfigTools.genKeyPair(512);
            return  new String[]{arr[0],arr[1],ConfigTools.encrypt(arr[0], dbPassword)};
        }catch (Exception e){
            //忽略
        }
        return null;
    }

}