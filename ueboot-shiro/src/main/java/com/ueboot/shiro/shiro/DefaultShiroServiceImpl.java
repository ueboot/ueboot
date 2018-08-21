package com.ueboot.shiro.shiro;

import com.ueboot.shiro.entity.Role;
import com.ueboot.shiro.entity.User;
import com.ueboot.shiro.service.user.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 默认的shiro查询实现，如果业务系统需要自己定制相关类，可以定一个bean名称为shiroService的类即可，并且实现ShrioService接口
 * @author yangkui
 */
@Component
@ConditionalOnMissingBean(name="shiroService")
public class DefaultShiroServiceImpl implements ShiroService {

    @Resource
    private UserService userService;


    /**
     * 对shiro的FilterChainDefinitionMap 添加自定义的配置
     * 如：
     * <p>
     * {@code
     * Map<String, String> map = new HashMap<>(10);
     * //配置指定路径是否需要登录、或不需要登录,示例
     * <p>
     * //所有/public开头的路径都不需要登录即可访问
     * map.put("/public/", "anon");
     * //所有路径需要授权才可以访问，和上面的配置作为互补。
     * map.put("/", "authc");
     * <p>
     * }
     *
     * @return 定义好的FilterChainDefinition Map
     */
    @Override
    public Map<String, String> addFilterChainDefinition() {
        Map<String, String> map = new HashMap<>(10);

        return map;
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @param password 未加密的密码
     * @return 是否存在
     */
    @Override
    public boolean userExist(String username, String password) {
        User user =this.userService.findByUserNameAndPassword(username, password);
        return user!=null;
    }

    /**
     * 判断用户密码是否过期
     *
     * @param username 用户名
     * @param password 密码
     * @return true 过期,false 未过期
     */
    @Override
    public boolean isPassed(String username, String password) {
        return false;
    }

    /**
     * 获取用户的角色名称集合
     *
     * @param username 用户名
     * @return 用户角色列表
     */
    @Override
    public Set<String> getUserRoleNames(String username) {
        List<Role> roles = this.userRoleRepository.findByUsername(username, Boolean.FALSE);

        Set<String> names = new HashSet<>();
        for (Role role : roles) {
            names.add(role.getRole());
        }

        return names;
    }

    /**
     * 获取用户的权限列表
     *
     * @param username 用户名
     * @return 用户权限列表
     */
    @Override
    public Set<String> getUserPermission(String username) {
        return null;
    }

    /**
     * 获取用户的权限列表
     *
     * @param role 角色名称
     * @return 用户权限列表
     */
    @Override
    public List<String> getRolePermission(String role) {
        return null;
    }
}
