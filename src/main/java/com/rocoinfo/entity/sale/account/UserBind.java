package com.rocoinfo.entity.sale.account;

import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 本地用户与其他平台用户绑定的pojo</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 下午12:49</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class UserBind extends IdEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserBind() {
        super();
    }

    public UserBind(User user, String oid, Platfrom platform, Date bindTime) {
        this.user = user;
        this.oid = oid;
        this.platform = platform;
        this.bindTime = bindTime;
    }

    /**
     * 本地用户
     */
    private User user;

    /**
     * 其他平台的id
     */
    private String oid;

    /**
     * 平台
     */
    private Platfrom platform;

    /**
     * 绑定时间
     */
    private Date bindTime;

    public enum Platfrom {

        WECHAT("微信");

        private String label;

        Platfrom(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Platfrom getPlatform() {
        return platform;
    }

    public void setPlatform(Platfrom platform) {
        this.platform = platform;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }
}
