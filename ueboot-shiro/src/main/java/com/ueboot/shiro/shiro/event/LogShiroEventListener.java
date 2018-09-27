package com.ueboot.shiro.shiro.event;

import com.ueboot.shiro.shiro.ShiroEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author yangkui
 * 记录所有权限相关操作到日志文件当中，用于审计
 */
@Slf4j
@Component
@ConditionalOnMissingBean(name = "shiroEventListener")
public class LogShiroEventListener implements ShiroEventListener {
    /**
     * 登录前记录
     *
     * @param userName 输入用户名
     * @param captcha  验证码
     */
    @Override
    public void beforeLogin(String userName, String captcha) {
        log.info("【审计日志】userName:{},captcha:{},开始登陆", userName, captcha);
    }

    /**
     * 登录后记录
     *
     * @param userName    输入用户名
     * @param loginStatus 登录状态；true：登录成功，false：登录失败
     * @param errorMsg    错误信息
     */
    @Override
    public void afterLogin(String userName, boolean loginStatus, String errorMsg) {
        log.info("【审计日志】userName:{},loginStatus:{},errorMsg:{},登陆操作", userName, loginStatus, errorMsg);
    }

    /**
     * 登出事件
     *
     * @param userName 用户名
     */
    @Override
    public void loginOutEvent(String userName) {
        log.info("【审计日志】userName:{}，退出系统！", userName);
    }

    /**
     * 更新密码事件
     *
     * @param userName 用户名
     */
    @Override
    public void updatePasswordEvent(String userName) {
        log.info("【审计日志】userName:{}，更新密码！", userName);

    }

    /**
     * 保存/修改 角色权限事件
     *
     * @param optUserName    操作人
     * @param roleId         操作人权限ID
     * @param oldResourceIds 原权限ID数组
     * @param newResourceIds 新权限ID数组
     */
    @Override
    public void saveRolePermissionEvent(String optUserName, Long roleId, Long[] oldResourceIds, Long[] newResourceIds) {
        log.info("【审计日志】操作人:{}，修改权限:{}，原权限资源ID列表:{},新权限资源ID列表:{}", optUserName, roleId, oldResourceIds, newResourceIds);

    }

    /**
     * 删除角色权限事件
     *
     * @param optUserName 操作人姓名
     * @param id          删除权限ID数组
     */
    @Override
    public void deleteRolePermissionEvent(String optUserName, Long[] id) {
        log.info("【审计日志】操作人:{}，删除权限数组:{}！", optUserName, id);

    }

    /**
     * 资源保存事件
     *
     * @param optUserName  操作人姓名
     * @param resourcesStr 资源对象JSON格式字符串
     */
    @Override
    public void saveResourceEvent(String optUserName, String resourcesStr) {
        log.info("【审计日志】操作人：userName:{}，资源名：{}！", optUserName, resourcesStr);

    }

    /**
     * 删除资源事件
     *
     * @param optUserName 操作人姓名
     * @param id          资源ID数组
     */
    @Override
    public void deleteResourceEvent(String optUserName, Long[] id) {
        log.info("【审计日志】操作人:{}，删除资源数组:{}！", optUserName, id);

    }

    /**
     * 保存/修改 角色事件
     *
     * @param optUserName 操作人姓名
     * @param roleStr     角色名
     */
    @Override
    public void saveRoleEvent(String optUserName, String roleStr) {
        log.info("【审计日志】操作人:{}，保存/修改角色名:{}！", optUserName, roleStr);

    }

    /**
     * 删除 角色事件
     *
     * @param optUserName 操作人姓名
     * @param id          角色数组
     */
    @Override
    public void deleteRoleEvent(String optUserName, Long[] id) {
        log.info("【审计日志】操作人:{}，删除角色数组{}！", optUserName, id);
    }

    /**
     * 保存/修改 用户事件
     *
     * @param optUserName 操作人姓名
     * @param userStr     用户名
     */
    @Override
    public void saveUserEvent(String optUserName, String userStr) {
        log.info("【审计日志】操作人:{}，保修/修改用户名:{}！", optUserName, userStr);

    }

    /**
     * 删除 用户事件
     *
     * @param optUserName 操作人姓名
     * @param id          用户ID数组
     */
    @Override
    public void deleteUserEvent(String optUserName, Long[] id) {
        log.info("【审计日志】操作人:{}，删除用户ID数组:{}！", optUserName, id);

    }

    /**
     * 保存/修改 用户角色事件
     *
     * @param optUserName 操作人姓名
     * @param userRoleStr 新用户角色名
     */
    @Override
    public void saveUserRoleEvent(String optUserName, String userRoleStr) {
        log.info("【审计日志】操作人:{}，用户角色名:{}！", optUserName, userRoleStr);

    }

    /**
     * 删除用户角色事件
     *
     * @param optUserName 操作人姓名
     * @param id          用户角色ID数组
     */
    @Override
    public void deleteUserRoleEvent(String optUserName, Long[] id) {
        log.info("【审计日志】操作人:{}，用户角色ID数组:{}！", optUserName, id);

    }
}
