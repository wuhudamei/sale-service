import com.rocoinfo.utils.TemplateUtils;

/**
 * Created by 闪电侠 on 2017/8/31.
 */
public class TemplateTest {
    public static void main(String[] args) {


        test1();


        //test2();
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa3b36bda18546de8&redirect_uri=http%3A%2F%2Fsso.rocoinfo.cn%2Foauth%2Fmenu%2Fcode%3Fappid%3D285a2fbf8f0b0969ad%26redirect_url%3Dhttp%3A%2F%2Foatest.mdni.net.cn%2FoauthCallBack&response_type=code&scope=snsapi_base&state=workorder/workOrderInfo?workOrderId=4590#wechat_redirect
    }
    public static void test1(){

        /*String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxAppid&redirect_uri=http%3A%2F%2FauthUrl%2Foauth%2Fmenu%2Fcode%3Fappid%3DauthAppid%26redirect_url%3Dhttp%3A%2F%2FdomainName%2FoauthCallBack&response_type=code&scope=snsapi_base&state=stateUrl#wechat_redirect";

        String encode = URLEncoder.encode("id=123&ids=34");

        System.out.println(encode);
        System.out.println(URLDecoder.decode(encode));
        System.out.println(oauthUrl.replaceAll("wxAppid", "wxf1d3c37befc6d7a8")
                //认证中心url
                .replaceAll("authUrl", "http://login.mdni.net.cn".replaceAll("http://", ""))
                //authAppid 认证中心的appid
                .replaceAll("authAppid", "285a2fbf8f0b0969ad")
                //domainName 认证通过后的跳转域名(当前项目域名)
                .replaceAll("domainName", "http://mm.mdni.net.cn".replaceAll("http://", ""))
                //state 需要访问的连接名(不含系统域名)
                .replaceAll("stateUrl", "/admin/sign"));*/

        TemplateUtils.sendWorkOrderStageTemplate("工单处理通知",
                "b86af666-afc7-42b4-94ed-77c72d780bd1",
                TemplateUtils.getOauthUrl("/workorder/workOrderInfo?workOrderId=123456789"),
                "工单code:2930","备注", "这是工单状态2930",
                "操作人!!!");

        /*TemplateUtils.sendWorkOrderStageTemplate("工单处理通知",
                "8cde5f43-c36c-49b4-a509-f128a600426d",
                TemplateUtils.getOauthUrl("/workorder/workOrderInfo?workOrderId=2930"),
                "工单code:2930","备注111", "这是工单状态2930",
                "操作人!!!");*/
    }

    public static void test2(){
        /**
         * String head, String jobNo, String url, String Applicant,
         String AppDepartment, String appContent, String appDate
         */
        TemplateUtils.sendApprovalTemplate("工单处理通知",
                "b86af666-afc7-42b4-94ed-77c72d780bd1", "http://mdnss.rocozxb.cn/workorder/approval?id=14446", "福田",
                "备注", "工单延期审批",
                "操作人!!!");
    }

}
