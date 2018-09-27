package com.ueboot.shiro.shiro;

/**
 * Shiro组件当中，用户登录、授权等事件接口，默认实现为添加日志，可以自定义用于记录到数据库
 *
 * @author yangkui
 */
public interface ShiroEventListener {

    void beforeLogin(String userName, String captcha);

    void afterLogin(String userName, boolean loginStatus,String errorMsg);

    void loginOutEvent(String userName);

    void updatePasswordEvent(String userName);

    void saveRolePermissionEvent(String optUserName, Long roleId, Long[] oldResourceIds, Long[] newResourceIds);

    void deleteRolePermissionEvent(String optUserName, Long[] id);

    /**
     * 资源保存事件
     *
     * @param optUserName  操作
     * @param resourcesStr 资源对象JSON格式字符串
     */
    void saveResourceEvent(String optUserName, String resourcesStr);

    void deleteResourceEvent(String optUserName, Long[] id);

    void saveRoleEvent(String optUserName, String roleStr);

    void deleteRoleEvent(String optUserName, Long[] id);

    void saveUserEvent(String optUserName, String userStr);

    void deleteUserEvent(String optUserName, Long[] id);

    void saveUserRoleEvent(String optUserName, String userRoleStr);

    void deleteUserRoleEvent(String optUserName, Long[] id);

}
