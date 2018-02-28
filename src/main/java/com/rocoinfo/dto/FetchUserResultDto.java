package com.rocoinfo.dto;

import com.rocoinfo.entity.sale.account.User;

/**
 * <dl>
 * <dd>Description: 用来封装OA端获取用户信息返回结果的dto</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/10/27 17:04</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class FetchUserResultDto {

    private String code;

    private String msg;

    private User data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    /**
     * 将UserDto转成User对象
     *
     * @return
     */
//    public User getUser() {
//        if (data != null) {
//            User user = new User();
//            user.setName(data.getEmpName()); // 姓名
////            if ("男".equals(data.getGender())) { // 性别
////                user.setGender(Gender.MALE);
////            } else if ("女".equals(data.getGender())) {
////                user.setGender(Gender.FEMALE);
////            }
//            user.setGender(data.getGender()); //性别
//            user.setMobile(data.getMobile()); // 名称
//            user.setEmail(data.getEmail()); // 邮箱
//            user.setJobNo(data.getEmpCode()); // 工号
//            user.setDepartment(data.getDepartment()); // 部门
//            user.setPosition(data.getPosition());
//            user.setUsername(data.getEmail()); // 用户名
//            return user;
//        }
//        return null;
//    }
}
