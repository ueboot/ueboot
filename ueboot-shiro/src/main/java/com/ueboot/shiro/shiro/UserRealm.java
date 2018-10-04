package com.ueboot.shiro.shiro;

import com.ueboot.shiro.entity.User;
import com.ueboot.shiro.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * 权限认证相关服务
 *
 * @author yangkui
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    /**
     * 超级用户，可以对权限功能进行设置，无需赋权也可以，防止新系统第一次无法登陆进去进行操作。
     */
    public static final String SUPER_USER = "root";

    @Resource
    private ShiroService shiroService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) this.getAvailablePrincipal(principals);
        Set<String> roleNames = this.shiroService.getUserRoleNames(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        Set<String> permissions = this.shiroService.getRolePermission(roleNames);
        //如果是指定的超级用户，则默认有一个最高权限，可以访问所有的功能
        if(SUPER_USER.equals(username)){
            permissions.add("*:*");
        }
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
            throw new LockedAccountException("您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理！");
        }
        if (user.getCredentialExpiredDate() != null && new Date().compareTo(user.getCredentialExpiredDate()) > -1) {
            throw new AuthenticationException("密码已经过期，请联系你的管理员进行处理！");
        }
        username = user.getUserName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        //判断密码是否一致，会在父类里面执行 ,与数据库中用户名和密码进行比对，密码盐值加密，第4个参数传入realName
        return new SimpleAuthenticationInfo(username, user.getPassword(), credentialsSalt, this.getName());

    }

}
