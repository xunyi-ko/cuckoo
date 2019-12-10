/**   
 * @Title: SysLoginController.java
 * @Package com.ypkj.boss.controller
 * @Description: 登录相关Controller类
 * @date 2019年8月19日
 * @Copyright (c) 2019, 杭州映派科技有限公司 All Rights Reserved.
 */
package site.xunyi.cuckoo.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import site.xunyi.cuckoo.shiro.ShiroUtils;
import site.xunyi.cuckoo.utils.Result;
import site.xunyi.cuckoo.utils.ResultUtil;

/**
 * @ClassName: SysLoginController
 * @author chen
 * @date 2019年8月19日
 * @version 1.0.0
 */
@Controller
@SuppressWarnings({ "rawtypes" })
public class LoginController extends AbstractController{
	/**
	 * 登录
	 * 
	 * @Title: login 
	 * @param username
	 * @param password
	 * @throws Exception
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(String username, String password) throws Exception {
		Result result = ResultUtil.success();
		try {
			Subject subject = ShiroUtils.getSubject();
			subject.logout();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.logout();
			subject.login(token);
		} catch (UnknownAccountException e) {
			result = ResultUtil.error("账号或密码错误");
		} catch (IncorrectCredentialsException e) {
			result = ResultUtil.error("账号或密码错误");
		} catch (LockedAccountException e) {
			result = ResultUtil.error("账号锁定");
		} catch (AuthenticationException e) {
			result = ResultUtil.error("账户授权失败");
		}

		return result;
	}
	
	/**
	 * 退出
	 * 
	 * @Title: logout 
	 * @return String
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}
}
