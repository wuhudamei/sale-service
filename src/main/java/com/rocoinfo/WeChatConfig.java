package com.rocoinfo;

import com.rocoinfo.weixin.WeChatInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <dl>
 * <dd>Description: 微信初始化</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 上午11:32</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@Component
public class WeChatConfig {

    Logger logger = LoggerFactory.getLogger(WeChatConfig.class);

    /**
     * 使用默认的access_token和param_manager初始化微信
     */
    @PostConstruct
    public void init() {
        logger.info("start init wechat configuration......");
        WeChatInitializer init = new WeChatInitializer();
        init.init();
        logger.info("end init wechat configuration......");
    }
}
