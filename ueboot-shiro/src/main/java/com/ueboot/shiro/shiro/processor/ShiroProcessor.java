package com.ueboot.shiro.shiro.processor;


import com.ueboot.shiro.entity.UserInfo;
import com.ueboot.shiro.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static com.ueboot.shiro.shiro.ShiroBaseConfigure.CACHE_REDIS_KEY;


/**
 * @author yangkui
 */
@Slf4j
@Component
public class ShiroProcessor {

	public static final String LOCK_KEY="ueboot:user:lock:";

	@Resource
	private RedisTemplate<String,Object> redisTemplate;


	public void login(String username, String password) {

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
		//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
		//所以这一步在调用login(token)方法时,它会走到UserRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
		log.info("对用户[" + username + "]进行登录验证..验证开始");
		try{
			log.info("验证用户和密码开始...");
			currentUser.login(token);
			log.info("验证用户和密码结束...");
		}catch(UnknownAccountException e){
			log.error(e.getMessage(),e);
			throw new AuthenticationException("用户不存在");
		}catch(IncorrectCredentialsException e){
			log.error(e.getMessage());
			throw new IncorrectCredentialsException("用户的密码不正确");
		}catch(LockedAccountException e){
			log.error(e.getMessage());
			throw new LockedAccountException("您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理");
		}catch(ExpiredCredentialsException e){
			log.error(e.getMessage());
			throw new AuthenticationException("密码已过期，请联系管理员");
		}catch(ExcessiveAttemptsException e){
			log.error(e.getMessage());
			//Redis 记录锁记录
			redisTemplate.opsForValue().set(LOCK_KEY+username,new UserInfo(username,new Date()));
			throw new ExcessiveAttemptsException("登录信息已累计输错5次，您的用户名已被锁定，请在1小时后进行登录 或 请联系你的管理员进行处理");
		}

		log.info("对用户[" + username + "]进行登录验证..验证通过");
		//验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			token.clear();
			throw new AuthenticationException();
		}
		//清空缓存当中已经存放过的权限信息，避免缓存一直有效
		redisTemplate.delete(CACHE_REDIS_KEY+":"+username);
		log.info("用户[" + username + "]登录认证通过");
	}

	public void logout() {
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
	}

}
