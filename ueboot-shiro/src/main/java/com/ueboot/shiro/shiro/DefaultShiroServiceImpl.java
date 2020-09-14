package com.ueboot.shiro.shiro;

import com.ueboot.shiro.entity.User;
import com.ueboot.shiro.entity.UserRole;
import com.ueboot.shiro.repository.permission.PermissionRepository;
import com.ueboot.shiro.repository.permission.bo.PermissionBo;
import com.ueboot.shiro.repository.userrole.UserRoleRepository;
import com.ueboot.shiro.service.user.UserService;
import org.apache.shiro.util.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 默认的shiro查询实现，如果业务系统需要自己定制相关类，可以定一个bean名称为shiroService的类即可，并且实现ShrioService接口
 *
 * @author yangkui
 */
@Component
@ConditionalOnMissingBean(name = "shiroService")
public class DefaultShiroServiceImpl implements ShiroService {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private PermissionRepository permissionRepository;

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
        map.put("/swagger-resources/", "anon");
        map.put("/swagger-ui.html", "anon");
        return map;
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public User getUser(String username) {
        return this.userService.findByUserName(username);
    }

    /**
     * 根据用户名查询登录成功后的返回结果
     *
     * @param username 用户名
     * @return 自定义返回任意数据内容
     */
    @Override
    public Map<String, Object> getLoginSuccessInfo(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("from", "ueboot-framework");
        return map;
    }

    /**
     * 获取用户的角色名称集合
     *
     * @param userName 用户名
     * @return 用户角色列表
     */
    @Override
    public Set<String> getUserRoleNames(String userName) {
        List<UserRole> roles = this.userRoleRepository.findByUserUserName(userName);
        Set<String> names = new HashSet<>();
        for (UserRole role : roles) {
            names.add(role.getRole().getName());
        }
        return names;
    }

    /**
     * 获取用户的权限列表
     *
     * @param roleNames 角色名称集合
     * @return 用户权限列表
     */
    @Override
    public Set<String> getRolePermission(String username, Set<String> roleNames) {

        List<PermissionBo> permissionList = this.permissionRepository.findPermissionsByRoleNameIn(roleNames);

        Set<String> names = new HashSet<>();
        for (PermissionBo p : permissionList) {
            if (StringUtils.hasText(p.getPermission())) {
                //支持一个权限标识里面有多个权限标识操作，使用,分割
                if (p.getPermission().contains(",")) {
                    String[] strs = p.getPermission().split(",");
                    Collections.addAll(names, strs);
                } else {
                    names.add(p.getPermission());
                }
            }
        }
        return names;

    }

    /**
     * 获取密码过期月份数。新用户和修改密码后，会自动将当前过期时间设置为当前时间往后几个月
     *
     * @return 过期月份数
     */
    @Override
    public int getPasswordExpiredMonth() {
        return 6;
    }
}
