package com.ueboot.shiro.shiro.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShiroProcessor {

	public void login(String username, String password, String captcha) {
		log.info("/login  username: {}  password: {}  captcha: {}", username, password, captcha);

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
		//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
		//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
		log.info("对用户[" + username + "]进行登录验证..验证开始");
		currentUser.login(token);
		log.info("对用户[" + username + "]进行登录验证..验证通过");
		//验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			token.clear();
			throw new AuthenticationException();
		}
		log.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
	}

	public void logout() {
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
	}

}
