/**   
 * @Title: AbstractController.java
 * @Package com.ypkj.boss.controller
 * @Description: Controller公共组件
 * @date 2019年8月19日
 * @Copyright (c) 2019, 杭州映派科技有限公司 All Rights Reserved.
 */
package site.xunyi.cuckoo.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import cn.hutool.core.exceptions.ValidateException;
import site.xunyi.cuckoo.entity.Customer;

/**
 * @ClassName: AbstractController
 * @author chen
 * @date 2019年8月19日
 * @version 1.0.0
 */
public abstract class AbstractController {
	@Autowired
	private MessageSource messageSource;
	Locale locale = LocaleContextHolder.getLocale();

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Customer getUser() {
		return (Customer) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getId() {
		return getUser().getId();
	}

	protected String getUsername() {
		return getUser().getLogin();
	}

	protected String getMsg(String... key) {
	    StringBuilder sb = new StringBuilder();
	    for(String str : key) {
	        sb.append(messageSource.getMessage(str, null, locale));
	    }
	    
		return sb.toString();
	}

	protected HttpServletRequest request;

	protected HttpServletResponse response;
	
	@ModelAttribute
	protected void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	protected void paramWrong() {
	    throw new ValidateException(getMsg("common.paramWrong"));
    }
}
