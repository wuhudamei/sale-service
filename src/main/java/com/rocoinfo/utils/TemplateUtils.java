package com.rocoinfo.utils;

import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.entity.template.WeChatWorkOrderStageTemplate;
import com.rocoinfo.entity.template.WechatApprovalTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <dl>
 * <dd>Description: 微信模板消息工具类:
 *          提供各类微信模板消息
 * </dd>
 * <dd>Company: 美得你</dd>
 * <dd>@date：2017-08-30 15:57:18</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
public class TemplateUtils {

    private static Logger logger = LoggerFactory.getLogger(TemplateUtils.class);//日志

    //微信认证url
    public static String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxAppid&redirect_uri=http%3A%2F%2FauthUrl%2Foauth%2Fmenu%2Fcode%3Fappid%3DauthAppid%26redirect_url%3Dhttp%3A%2F%2FdomainName%2FoauthCallBack&response_type=code&scope=snsapi_base&state=stateUrl#wechat_redirect";


    /**
     * 发送	提醒通知消息 模板
     * @param jobNo	员工号(发给谁)
     * @param url	点击查看详情url ,不跳转详情页传 null
     * @param remark  模板尾部tail,备注  不传则默认: 请点击查看详情
     * @param workOrderCode	工单号
     * @param orderStatus	工单进度
     * @param operationUser	工单处理人
     * @author：Paul
     */
    public static void sendWorkOrderStageTemplate(String head, String jobNo, String url, String workOrderCode,
                                  String remark, String orderStatus, String operationUser) {
        WeChatWorkOrderStageTemplate template = new WeChatWorkOrderStageTemplate();
        template.setHead(head);
        template.setUrl(url);
        template.setJobNo(jobNo);
        //工单号
        template.setKeyword1(workOrderCode);
        //工单进度
        template.setKeyword2(orderStatus);
        //工单处理人
        template.setKeyword3(operationUser);
        //备注 详情
        template.setRemark(remark);

        String result = HttpUtils.postJson(PropertyHolder.getOaBaseUrl() + Constants.WORK_ORDER_STAGE_URL,
                JsonUtils.toJson(template));

        if(result != null){
            //发送成功
            logger.info("微信模板消息发送成功!" + template.toString());
        }

    }

    /**
     * 发送	审批模板
     * @param jobNo	    员工号(发给谁)
     * @param url	点击查看详情url
     * @param Applicant	申请人
     * @param AppDepartment	申请人部门
     * @param appContent	待审批事项
     * @param appDate	申请提交时间
     * @author：Paul
     */
    public static void sendApprovalTemplate(String head, String jobNo, String url, String Applicant,
                                String AppDepartment, String appContent, String appDate) {
        WechatApprovalTemplate template = new WechatApprovalTemplate();
        template.setHead(head);
        template.setUrl(url);
        template.setJobNo(jobNo);
        //申请人
        template.setKeyword1(Applicant);
        //申请人部门
        template.setKeyword2(AppDepartment);
        //待审批事项
        template.setKeyword3(appContent);
        //申请提交时间
        template.setKeyword4(appDate);

        String result = HttpUtils.postJson(PropertyHolder.getOaBaseUrl() + Constants.APPROVAL_URL,
                JsonUtils.toJson(template));

        if(result != null){
            //发送成功
            logger.info("微信模板消息发送成功!" + template.toString());
        }
    }


    /**
     *  获取当前微信模板消息中查看详情的 认证url;
     * @param url 详情url(不含项目域名)
     * @return 认证url
     */
    public static String getOauthUrl(String url){
        //wxAppid: 微信公众号appid
        return oauthUrl.replaceAll("wxAppid", PropertyHolder.getOaAppid())
                    //认证中心url
                    .replaceAll("authUrl", PropertyHolder.getOauthCenterUrl().replaceAll("http://", ""))
                    //authAppid oa系统认证中心的appid
                    .replaceAll("authAppid", PropertyHolder.getOaAuthAppid())
                    //domainName 认证通过后的跳转域名(当前项目域名)
                    .replaceAll("domainName", PropertyHolder.getBaseurl().replaceAll("http://", ""))
                    //state 需要访问的连接名(不含系统域名)
                    .replaceAll("stateUrl", url);

    }
}
