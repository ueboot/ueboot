package com.ueboot.shiro.shiro;


import java.util.Map;
import java.util.Set;

/**
 * Shiro权限认证服务类，代码集成时只需要实现该接口当中的所有方法即可
 * <p>
 * 必须有类实现该接口。且bean的名称为“shiroService”
 *
 * @author yangkui
 */
public interface ShiroService {


    /**
     * 对shiro的FilterChainDefinitionMap 添加自定义的配置
     * 如：
     *
     {@code
      Map<String, String> map = new HashMap<>(10);
      //配置指定路径是否需要登录、或不需要登录,示例
     
      //所有/public开头的路径都不需要登录即可访问
      map.put("/public/", "anon");
      //所有路径需要授权才可以访问，和上面的配置作为互补。
      map.put("/", "authc");

     }
     * @return 定义好的FilterChainDefinition Map
     */
    Map<String, String> addFilterChainDefinition();


    /**
     * 根据用户名获取用户信息，要求返回的对象当中必须包含如下属性名：
     * 1:userName
     * 2:password
     * 3:locked
     * 4:credentialExpiredDate
     *
     * @param username 用户名
     * @return 用户对象，返回的对象需要有固定的几个属性，用于判断密码、是否过期等
     */
    Object getUser(String username);

    /**
     * 根据用户名查询登录成功后的返回结果给前端登录成功请求
     * @param username 用户名
     * @return 自定义返回任意数据内容
     */
    Map<String,Object> getLoginSuccessInfo(String username);


    /**
     * 获取用户的角色名称集合(要求角色名称唯一）
     *
     * @param userName 用户名
     * @return 用户角色列表
     */
    Set<String> getUserRoleNames(String userName);

    /**
     * 获取用户的权限列表
     *
     * @param roleCodes 角色代码列表
     * @return 用户权限列表
     */
    Set<String> getRolePermission(String userName,Set<String> roleCodes);

    /**
     * 获取密码过期月份数。新用户和修改密码后，会自动将当前过期时间设置为当前时间往后几个月
     * @return 过期月份数
     */
    int getPasswordExpiredMonth();

}
