package com.example.user.service;

import com.example.user.entity.Session;

public interface SessionService {

    public Session setSession(Session session);
    public void deleteSession(String session_id);
    public Session readSession(String session_id);
    public Session updateSession(Session session);
}
