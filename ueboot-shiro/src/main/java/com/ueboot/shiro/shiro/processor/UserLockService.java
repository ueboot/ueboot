package com.ueboot.shiro.shiro.processor;

import com.ueboot.shiro.entity.UserInfo;

/**
 * 锁住用户登录，用于限定登录错误指定次数后，对账号进行锁定
 *
 * @author yangkui
 * createTime:2020/2/2721:34
 */
public interface UserLockService {
    public static final String LOCK_KEY="ueboot:user:lock:";

    void lockUser(String username);

    UserInfo getLockUserInfo(String username);
}
