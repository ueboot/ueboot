package com.ueboot.shiro.shiro;

import com.ueboot.core.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;

/**
 * 权限认证相关服务
 *
 * @author yangkui
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    private ShiroService shiroService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) this.getAvailablePrincipal(principals);
        if(!this.checkShiroService()){
            return null;
        }
        Set<String> roleNames = this.shiroService.getUserRoleNames(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        for (String role : roleNames) {
            Collection<String> permissions = this.shiroService.getRolePermission(role);
            info.addStringPermissions(permissions);
        }
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(!this.checkShiroService()){
            return null;
        }
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException("用户名不能为空！");
        } else if (StringUtils.isEmpty(password)) {
            throw new AuthenticationException("密码不能为空！");
        } else if (!this.shiroService.userExist(username, password)) {
            throw new AuthenticationException("用户名或密码不正确！");
        } else if (this.shiroService.isPassed(username, password)) {
            throw new AuthenticationException("登录密码已过期！");
        } else {
            return new SimpleAuthenticationInfo(username, password.toCharArray(), this.getName());
        }

    }

    private boolean checkShiroService() {
        if (this.shiroService == null) {
            shiroService = SpringContextUtils.getBeanByClass(ShiroService.class);
            if (shiroService == null) {
                log.error("！！！shiro权限认证功能启用失败！！！");
                log.error("shiroService接口未找到相应的实现类，请实现com.ueboot.core.shiro.ShiroService接口，并定义成spring bean");
                return false;
            }
        }
        return true;
    }


}
