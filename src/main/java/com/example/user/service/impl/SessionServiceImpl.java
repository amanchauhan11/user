package com.example.user.service.impl;

import com.example.user.entity.Session;
import com.example.user.repository.SessionRepository;
import com.example.user.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository repository;

    @Override
    public Session setSession(Session session){
        return repository.save(session);
    }

    @Override
    public void deleteSession(String session_id){
        repository.delete(session_id);
    }

    @Override
    public Session updateSession(Session session){
        return setSession(session);
    }

    @Override
    public Session readSession(String session_id){
        return repository.findOne(session_id);
    }
}
