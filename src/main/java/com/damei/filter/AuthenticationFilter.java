package com.damei.filter;

import com.damei.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements Filter {
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
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
