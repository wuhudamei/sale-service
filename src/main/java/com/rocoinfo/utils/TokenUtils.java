package com.rocoinfo.utils;

import com.google.common.collect.Maps;
import com.rocoinfo.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenUtils {

    /**
     * 过期时间
     */
    private static final int EXPIRATION_TIME = 1700;

    /**
     * 最近一次抓起token的时间
     */
    private static volatile long FETCH_TIME = 0L;

    /**
     * 使用map做cache缓存token
     */
    private static Map<String, String> tokenCache = Maps.newConcurrentMap();

    /**
     * key
     */
    private static final String TOKEN = "token";


    /**
     * 获取Token
     *
     * @return
     */
    public static synchronized String getToken() {
        if (StringUtils.isBlank(tokenCache.get(TOKEN)) || System.currentTimeMillis() - FETCH_TIME >= EXPIRATION_TIME) {
            tokenCache.put(TOKEN, refreshToken());
            FETCH_TIME = System.currentTimeMillis();
        }
        return tokenCache.get(TOKEN);
    }

    /**
     * 请求获取token
     */
    private static String refreshToken() {
        Map<String, String> params = new HashMap<>();

        params.put("appid", Constants.APPID);
        params.put("platform", Constants.PLATFORM);
        params.put("secret", Constants.SECRET);

        String res = HttpUtils.post(Constants.GET_TOKEN_URL, params);
        Map<String, Object> resultMap = JsonUtils.fromJsonAsMap(res, String.class, Object.class);
        if ("1".equals(resultMap.get("code").toString())) {
            Map<String, Object> accessMap = convertToResultMap(resultMap);
            return accessMap.get("token").toString();
        } else {
            return "";
        }
    }

    public static Map<String, Object> convertToResultMap(Map<String, Object> resultMap) {
        return (Map<String, Object>) resultMap.get("result");
    }

}
