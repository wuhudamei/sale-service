package com.rocoinfo.shiro;

import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.rocoinfo.entity.sale.account.User;

/**
 * <dl>
 * <dd>Description: 密码加密工具类</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/7 16:21</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public class PasswordUtil {
    private static final int SALT_SIZE = 8;
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_ITERATIONS = 1024;

    /**
     * 普通用户密码加密 生成随机的salt并经过1024次 sha-1 hash
     *
     * @param user 普通用户
     */
    public static void entryptUserPassword(User user) {
        user.setSalt(generateSalt());
        user.setPassWord(hashPassword(user.getPlainPassword(), user.getSalt()));
    }

    public static String generateSalt() {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        return Encodes.encodeHex(salt);
    }

    public static String hashPassword(String plainPassword, String salt) {
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_ITERATIONS);
        return Encodes.encodeHex(hashPassword);
    }

    /**
     * @param loginPassword 登录时，传入的明文密码
     */
    public static boolean isPasswordCorrect(String loginPassword, User user) {
        return PasswordUtil.hashPassword(loginPassword, user.getSalt()).equals(user.getPassWord());
    }

}
