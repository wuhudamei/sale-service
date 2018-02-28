package com.rocoinfo.shiro.listener;

import com.rocoinfo.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class CustomSessionListener implements SessionListener {

    private ShiroSessionRepository shiroSessionRepository;

    @Override
    public void onStart(Session session) {
    }

    @Override
    public void onStop(Session session) {
        //TODO
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}
