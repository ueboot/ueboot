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
    @Override
    public void beforeLogin(String userName, String captcha) {
        log.info("【审计日志】userName:{},captcha:{},开始登陆", userName, captcha);
    }

    @Override
    public void afterLogin(String userName, boolean loginStatus, String errorMsg) {
        log.info("【审计日志】userName:{},loginStatus:{},errorMsg:{},登陆操作", userName, loginStatus, errorMsg);
    }

    @Override
    public void loginOutEvent(String userName) {
        log.info("【审计日志】userName:{}，退出系统！",userName);
    }

    @Override
    public void updatePasswordEvent(String userName) {
        log.info("【审计日志】userName:{}，退出系统！",userName);

    }

    @Override
    public void saveRolePermissionEvent(String optUserName, Long roleId, Long[] oldResourceIds, Long[] newResourceIds) {
        log.info("【审计日志】操作人:{}，修改权限，原权限资源ID列表:{},新权限资源ID列表:{}",optUserName,oldResourceIds,newResourceIds);

    }

    @Override
    public void deleteRolePermissionEvent(String optUserName, Long[] id) {
        log.info("【审计日志】操作人:{}，删除权限！",optUserName);

    }

    /**
     * 资源保存事件
     *
     * @param optUserName  操作
     * @param resourcesStr 资源对象JSON格式字符串
     */
    @Override
    public void saveResourceEvent(String optUserName, String resourcesStr) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void deleteResourceEvent(String optUserName, Long[] id) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void saveRoleEvent(String optUserName, String roleStr) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void deleteRoleEvent(String optUserName, Long[] id) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void saveUserEvent(String optUserName, String userStr) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void deleteUserEvent(String optUserName, Long[] id) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void saveUserRoleEvent(String optUserName, String userRoleStr) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }

    @Override
    public void deleteUserRoleEvent(String optUserName, Long[] id) {
        log.info("【审计日志】userName:{}，退出系统！",optUserName);

    }
}
