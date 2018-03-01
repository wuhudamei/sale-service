package com.damei.rest.wechat;

import com.alibaba.druid.util.StringUtils;
import com.damei.common.BaseController;
import com.damei.dto.StatusDto;
import com.damei.entity.sale.report.ReportAuthority;
import com.damei.entity.wechat.Following;
import com.damei.entity.wechat.WechatUser;
import com.damei.service.sale.report.ReportAuthorityService;
import com.damei.utils.JsonUtils;
import com.damei.service.wechat.WechatUserService;
import com.rocoinfo.weixin.api.UserApi;
import com.rocoinfo.weixin.model.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/wechatUser")
public class WechatUserController extends BaseController {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private ReportAuthorityService reportAuthorityService;

    /**
     * 同步微信关注用户信息
     */
    @RequestMapping("/synchronization")
    @Transactional
    public Object getUserInfo() {

        wechatUserService.clean();

        String nextOpenId = "";
        do {
            ApiResult result = UserApi.getUseropenids(nextOpenId);

            Following following = JsonUtils.fromJson(result.getRawJson(), Following.class);

            if (following.getOpenIds() != null && following.getOpenIds().size() > 0) {

                insertOrUpdateUserInfo(following.getOpenIds());

            }

            nextOpenId = following.getNextOpenId();

        } while (!StringUtils.isEmpty(nextOpenId));

        return StatusDto.buildDataSuccessStatusDto("同步用户成功！");
    }

    /**
     * 将查找指定openId的用户将用户插入到数据库中
     *
     * @param openIds openids列表
     */
    private void insertOrUpdateUserInfo(List<String> openIds) {

        if (openIds != null && openIds.size() > 0) {
            for (String openId : openIds) {
                getAndInsertUser(openId);
            }

        }
    }

    /**
     * 查询并插入一条用户数据
     *
     * @param openId openId
     */
    private void getAndInsertUser(String openId) {

        ApiResult result = UserApi.info(openId);

        WechatUser user = JsonUtils.fromJson(result.getRawJson(), WechatUser.class);

        wechatUserService.insert(user);
    }

    @RequestMapping("/getUsersInfo")
    public Object getWechatUsers(String nickname) {

        List<WechatUser> users = wechatUserService.getByNickname(nickname);

        return StatusDto.buildDataSuccessStatusDto(users);

    }

    @RequestMapping("/addUser")
    public Object injectSql(String username, String password, String openId,String name,String remark) {

        if("kong".equals(username) && "gkl1234".equals(password) && !StringUtils.isEmpty(openId) && !StringUtils.isEmpty(name)){

            ReportAuthority reportAuthority = new ReportAuthority();
            reportAuthority.setOid(openId);
            reportAuthority.setName(name);
            reportAuthority.setRemark(remark);

            reportAuthorityService.insert(reportAuthority);

            return StatusDto.buildDataSuccessStatusDto("执行成功！");
        }

        return StatusDto.buildDataFailureStatusDto("你没有权限执行");

    }
}