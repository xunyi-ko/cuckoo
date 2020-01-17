/**   
 * @Title: MyShiroAuthcFilter.java
 * @Package com.ypkj.boss.shiro
 * @Description: shiro权限过滤类
 * @date 2019年8月19日
 * @Copyright (c) 2019, 杭州映派科技有限公司 All Rights Reserved.
 */
package site.xunyi.cuckoo.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.alibaba.fastjson.JSONObject;

import site.xunyi.cuckoo.util.Result;
import site.xunyi.cuckoo.util.ResultUtil;

/**
 * @ClassName: MyShiroAuthcFilter
 * @author chen
 * @date 2019年8月19日
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class MyShiroAuthcFilter extends FormAuthenticationFilter {
	/**
	 * XMLHttpRequest
	 */
	private final static String XML_HTTP_REQUEST = "XMLHttpRequest";
	
	 /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isAjax(request)) {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
			Result result = ResultUtil.error(-1, "登录超时");
            httpServletResponse.getWriter().write(JSONObject.toJSON(result).toString());
        } else {
            //saveRequestAndRedirectToLogin(request, response);
            /**
             * @Mark 非ajax请求重定向为登录页面
             */
            httpServletResponse.sendRedirect("/login");
        }
        return false;
    }

    private boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if(XML_HTTP_REQUEST.equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
		
}
