package com.ueboot.shiro.shiro;

import com.ueboot.shiro.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 权限认证相关服务
 *
 * @author yangkui
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private ShiroService shiroService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) this.getAvailablePrincipal(principals);
        Set<String> roleNames = this.shiroService.getUserRoleCodes(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        Collection<String> permissions = this.shiroService.getRolePermission(roleNames);
        info.addStringPermissions(permissions);
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException("用户名不能为空！");
        }
        if (StringUtils.isEmpty(password)) {
            throw new AuthenticationException("密码不能为空！");
        }
        User user = this.shiroService.getUser(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (user.isLocked()) {
            throw new LockedAccountException("账号已经被锁定，无法操作！");
        }
        if (user.getCredentialExpiredDate() != null && new Date().compareTo(user.getCredentialExpiredDate()) > -1) {
            throw new AuthenticationException("密码已经过期，无法操作！");
        }
        //判断密码是否一致，会在父类里面执行
        return new SimpleAuthenticationInfo(username, password.toCharArray(), this.getName());

    }

}
