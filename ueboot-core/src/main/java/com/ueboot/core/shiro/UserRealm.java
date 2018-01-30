package com.ueboot.core.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) this.getAvailablePrincipal(principals);
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
            throw new AuthenticationException("登陆密码已过期！");
        } else {
            return new SimpleAuthenticationInfo(username, password.toCharArray(), this.getName());
        }

    }


}
