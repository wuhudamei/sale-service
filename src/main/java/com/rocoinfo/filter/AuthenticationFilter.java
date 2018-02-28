package com.rocoinfo.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.rocoinfo.utils.WebUtils;

/**
 * <dl>
 * <dd>Description: 认证过滤器</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/11/10 13:57</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class AuthenticationFilter implements Filter {
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    //不需要登录的路
    private static String[] testUrl = {"/mdni/login","/login","/"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String ctx = StringUtils.trimToEmpty(req.getContextPath());
        String path = req.getRequestURI().substring(ctx.length());
        
        List<String> list=Arrays.asList(testUrl);
        if (StringUtils.isNotEmpty(path) && 
        		(path.startsWith("/static/") || path.contains("/favicon.ico") || list.contains(path))) {
            chain.doFilter(request, response);
        } else {
//        	String reqHeader = req.getHeader("Accept");
            if (!WebUtils.isLogin(req)) {
            	request.getRequestDispatcher("/login").forward(req, resp);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

	@Override
    public void destroy() {

    }
}
