package com.rocoinfo.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.net.ssl.*;
import javax.servlet.ServletContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * 在ServletContext中设置全局变量。可以在JSP中用EL获取。例如:${ctx}
 *
 * @author liuwei
 */
@Component
@Lazy(false)
public class PropertyHolder implements ServletContextAware, ApplicationContextAware {

	public static ApplicationContext appCtx;
	private static ServletContext servletContext;
	private static String uploadBaseUrl;
	private static String uploadDir;
	private static String baseurl;
	private static String orderServiceMd5;
	/**
	 * 推工单的 公司门店id  部门id
	 */
	private static String orderserviceCompanyIds;
	private static String orderserviceDepartmentIds;
	/**
	 * 对接产业工人的 url
	 */
	private static String orderserviceUrl;

	/**----------------认证中心相关--------------**/
	/** 认证中心URL **/
	private static String oauthCenterUrl;
	/** 认证中心appid **/
	private static String oauthCenterAppid;
	/** 认证中心secret **/
	private static String oauthCenterSecret;
	/** 认证中心 获取token url **/
	private static String oAuthAppTokenUrl;
	/** 认证中心 同步用户的 url **/
	private static String oAuthAppUserUrl;



	/** 美得你综管平台url **/
	private static String oaBaseUrl;


	/** 美得你综管平台的微信公众号appid **/
	private static String oaAppid;

	/** 美得你综管平台的系统的认证 appid **/
	private static String oaAuthAppid;

	/** 美得oa 的md5**/
	private static String oaMd5;

	static {
		disableSslVerification();
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext ctx) {
		PropertyHolder.servletContext = ctx;
		ctx.setAttribute("ctx", ctx.getContextPath());
	}

	/**
	 * 此BaseUrl 就是 StaticContentServlet 的url-pattern中访问路径 例如：/file/
	 */
	public static String getUploadBaseUrl() {
		return PropertyHolder.uploadBaseUrl;
	}

	@Value("${upload.base.url}")
	public void setUploadBaseUrl(String uploadBaseUrl) {
		PropertyHolder.uploadBaseUrl = uploadBaseUrl;
	}

	public static String getUploadDir() {
		return PropertyHolder.uploadDir;
	}

	@Value("${upload.dir}")
	public void setUploadDir(String uploadDir) {
		PropertyHolder.uploadDir = uploadDir;
	}

	public static String getBaseurl() {
		return PropertyHolder.baseurl;
	}

	@Value("${base.url}")
	public void setBaseurl(String baseurl) {
		PropertyHolder.baseurl = baseurl;
	}

	/** 获取 认证中心url **/
	public static String getOauthCenterUrl() {
		return PropertyHolder.oauthCenterUrl;
	}

	/** 设置 认证中心url **/
	@Value("${oauth.center.url}")
	public void setOauthCenterUrl(String oauthCenterUrl) {
		PropertyHolder.oauthCenterUrl = oauthCenterUrl;
	}

	/** 获取 认证中心appid **/
	public static String getOauthCenterAppid() {
		return PropertyHolder.oauthCenterAppid;
	}

	/** 设置 认证中心appid **/
	@Value("${oauth.center.appid}")
	public void setOauthCenterAppid(String oauthCenterAppid) {
		PropertyHolder.oauthCenterAppid = oauthCenterAppid;
	}

	/** 获取 认证中心secret **/
	public static String getOauthCenterSecret() {
		return PropertyHolder.oauthCenterSecret;
	}

	/** 设置 认证中心secret **/
	@Value("${oauth.center.secret}")
	public void setOauthCenterSecret(String oauthCenterSecret) {
		PropertyHolder.oauthCenterSecret = oauthCenterSecret;
	}
	
    public static String getOrderServiceMd5() {
        return orderServiceMd5;
    }
    
    @Value("${order.service.md5}")
    public void setOrderServiceMd5(String orderServiceMd5) {
        PropertyHolder.orderServiceMd5 = orderServiceMd5;
    }

	public static String getOrderserviceCompanyIds() {
		return orderserviceCompanyIds;
	}
	@Value("${order.service.companyIds}")
	public  void setOrderserviceCompanyIds(String orderserviceCompanyIds) {
		PropertyHolder.orderserviceCompanyIds = orderserviceCompanyIds;
	}
	public static String getOrderserviceDepartmentIds() {
		return orderserviceDepartmentIds;
	}
	@Value("${order.service.departmentIds}")
	public  void setOrderserviceDepartmentIds(String orderserviceDepartmentIds) {
		PropertyHolder.orderserviceDepartmentIds = orderserviceDepartmentIds;
	}

	public static String getOrderserviceUrl() {
		return orderserviceUrl;
	}
	@Value("${order.service.url}")
	public  void setOrderserviceUrl(String orderserviceUrl) {
		PropertyHolder.orderserviceUrl = orderserviceUrl;
	}

	public static String getOaBaseUrl() {
		return oaBaseUrl;
	}
	@Value("${oa.base.url}")
	public void setOaBaseUrl(String oaBaseUrl) {
		PropertyHolder.oaBaseUrl = oaBaseUrl;
	}

	public static String getoAuthAppTokenUrl() {
		return oAuthAppTokenUrl;
	}
	@Value("${oauth.appToken.url}")
	public void setoAuthAppTokenUrl(String oAuthAppTokenUrl) {
		PropertyHolder.oAuthAppTokenUrl = oAuthAppTokenUrl;
	}

	public static String getoAuthAppUserUrl() {
		return oAuthAppUserUrl;
	}
	@Value("${oauth.appUser.url}")
	public void setoAuthAppUserUrl(String oAuthAppUserUrl) {
		PropertyHolder.oAuthAppUserUrl = oAuthAppUserUrl;
	}

	public static String getOaAppid() {
		return oaAppid;
	}

	@Value("${oaAppid}")
	public void setOaAppid(String oaAppid) {
		PropertyHolder.oaAppid = oaAppid;
	}

	public static String getOaAuthAppid() {
		return oaAuthAppid;
	}

	@Value("${oa.oauth.center.appid}")
	public void setOaAuthAppid(String oaAuthAppid) {
		PropertyHolder.oaAuthAppid = oaAuthAppid;
	}

	public static String getOaMd5() {
		return oaMd5;
	}
	@Value("${oa.md5}")
	public  void setOaMd5(String oaMd5) {
		PropertyHolder.oaMd5 = oaMd5;
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCtx = applicationContext;
	}

}