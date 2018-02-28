
package com.rocoinfo;

/**
 * <dl>
 * <dd>描述: </dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>@创建时间：2016/2/17 21:22</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class Constants {

    private Constants() {
    }
    
    /**
     * 密碼加密默认的盐值
     */
    public static final String SALT = "f978e6ef0163578b";

    public static final String USER_LIST = "rocoinfo";

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD = "yyyy-MM-dd";

    /**
     * 邮箱后缀
     */
    public static final String EMAIL_SUFFIX = "@rocoinfo.com";

    public static final String EMPTY_STR = "";
    /**
     * 接口响应状态码
     */
    public static final String RESP_STATUS_CODE_SUCCESS = "1";

    public static final String RESP_STATUS_CODE_FAIL = "0";

    public static final String SESSION_ATTR_LOGIN_USER = "login_user";

    /**
     * 分页相关参数
     */
    public static final String PAGE_OFFSET = "offset";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_SORT = "sort";
    /** 认证中心修改密码url**/
    public static final String OAUTH_PASSWORD_URL = "/oauth/password";
    /**
     * 微信指定的平台ID
     */
    public static final String PLATFORM = "3000";

    /**
     * 平台码
     */
    public static final String APPID = "g9345BIUR34h092yb";

    /**
     * 平台秘钥
     */
    public static final String SECRET = "g5n4307bv4HB34562FPBUdfh";

//    /**
//     * 微信端校验的url
//     */
//    public static final String VALIDATE_URL = "http://weixin.rocoinfo.cn/wechat/platform/valid";
//
//    /**
//     * 获取accesstoken的url
//     */
//    public static final String GET_TOKEN_URL = "http://weixin.rocoinfo.cn/wechat/platform/token";
//
//    /**
//     * 获取用户信息的url
//     */
//    public static final String GET_USER_INFO_URL = "http://weixin.rocoinfo.cn/wechat/userInfo";

    /**
     * 微信端校验的url
     */
    public static final String VALIDATE_URL = "http://rocolpf.rocozxb.cn/platform/valid";

    /**
     * 获取accesstoken的url
     */
    public static final String GET_TOKEN_URL = "http://rocolpf.rocozxb.cn/platform/token";

    /**
     * 获取用户信息的url
     */
    public static final String GET_USER_INFO_URL = "http://rocolpf.rocozxb.cn/userInfo";
    
    /** 工时系统对外访问域名 key**/
    public static final String TECH_WORK_URL_KEY = "techworkUrl";
    /** 工时系统对外访问域名 value**/
    public static String TECH_WORK_URL = "";
    
    /*************************************	单点登录相关 start *****************************************************/
	/** 认证中心获取code url**/
	public static final String OAUTH_CENTER_CODE_URL = "/oauth/code";
	/** 认证中心获取token url**/
	public static final String OAUTH_CENTER_TOKEN_URL = "/oauth/token";
	/** 认证中心登出url**/
	public static final String OAUTH_LOGOUT_URL = "/oauth/logout";	
	/** 认证中心回调 url**/
	public static final String OAUTH_CALL_BACK = "/oauthCallBack";
	/*************************************	单点登录相关 end *****************************************************/
	
	/** 售后2.0中美得你集团 公司id **/
	public static final Long MDI_GROUP_ID = 98L;


    /*************************************	微信模板消息 start *****************************************************/
    /**	工单进度通知 模板消息发送	url	**/
    public static final String WORK_ORDER_STAGE_URL = "/api/wx/template/sendWorkOrderStageTemplate";
    /**	通知审批 模板消息发送	url	**/
    public static final String APPROVAL_URL = "/api/wx/template/sendApprovalTemplate";
    /*************************************	微信模板消息 end *****************************************************/


    /** 调用 产业工人的 推工单的地址**/
    public static final String ORDER_SERVICE_PUSH = "/a/api/BizSynDateSendAndReceive/receiveJsonStr";
    /** 调用 产业工人的 修改工单状态的地址**/
    public static final String ORDER_SERVICE_UPDATE = "";


    /** 日清报表 工单的截止时间 下午4点**/
    public static final int WORK_ORDER_TIME = 16;

    /** 日清报表 工单的截止时间 下午5点 小时**/
    public static final int WORK_ORDER_OPERATE_TIME_HOUR = 17;

    /** 日清报表 工单的截止时间 下午5点 30 分**/
    public static final int WORK_ORDER_OPERATE_TIME_MINUTE = 30;
}
