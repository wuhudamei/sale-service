package com.rocoinfo.shiro.session;

import com.rocoinfo.shiro.MemoryManager;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * <dl>
 * <dd>Description: 功能描述</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/7 19:10</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public class MemoryShiroSessionRepository implements ShiroSessionRepository {

    public static final String REDIS_SHIRO_SESSION = "shiro-session:";

    private static final long DEFAULT_SESSION_TIMEOUT = 1800000L;

    private long sessionTimeOut = DEFAULT_SESSION_TIMEOUT;

    //日志对象
    private final Logger logger = LoggerFactory.getLogger(MemoryShiroSessionRepository.class);

    /**
     * 缓存管理对象
     */
    private MemoryManager cacheManager;


    @Override
    public void saveSession(Session session) {
        if(session == null || session.getId() == null){
            throw new NullPointerException("session is empty");
        }
        logger.debug("save session {}", session.getId());

        session.setTimeout(sessionTimeOut);

//        WebUtils.threadSession.set(session);
        getCacheManager().saveValueByKey(buildRedisSessionKey(session.getId()), session, sessionTimeOut);
    }

    @Override
    public void deleteSession(Serializable sessionId) {

        if (sessionId == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            logger.debug("delete session {}", sessionId);
//            WebUtils.threadSession.remove();
            getCacheManager().deleteByKey(buildRedisSessionKey(sessionId));
        } catch (Exception e) {
            logger.error("delete session error", e);
        }
    }

    @Override
    public Session getSession(Serializable sessionId) {
        if (sessionId == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
//            session = (Session)WebUtils.threadSession.get();
            if (session==null){
//                logger.debug("get session {}", sessionId);
                session = getCacheManager().getValueByKey(buildRedisSessionKey(sessionId));
//                WebUtils.threadSession.set(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("get session error {}", sessionId);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        logger.debug("get all sessions");
        return null;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    public long getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(long sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public MemoryManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(MemoryManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
