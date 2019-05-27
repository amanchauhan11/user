package com.example.user.service.impl;

import com.example.user.entity.UserInfo;
import com.example.user.model.AuthForm;
import com.example.user.model.SignupForm;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public UserInfo userLogin(AuthForm loginForm){
        List<UserInfo> userInfoList = repository.getUserByUname(loginForm.getUname());
        Iterator iter = userInfoList.iterator();
        if(userInfoList.isEmpty())
            return null;
        else {
            UserInfo userInfo = (UserInfo) iter.next();
            if(userInfo.getPassword().equals(loginForm.getPassword()))
                return userInfo;
            else
                return null;
        }
    }

    @Override
    public UserInfo userSignup(SignupForm signupForm){
        List<UserInfo> userInfoList = repository.getUserByUname(signupForm.getUname());
        if(userInfoList.isEmpty()){
            UserInfo newUser = new UserInfo();
            BeanUtils.copyProperties(signupForm,newUser);
            UserInfo eResult = repository.save(newUser);
            return eResult;
        }
        else
            return null;
    }

    @Override
    public UserInfo getUserById(Integer uid){
        return repository.findOne(uid);
    }
}
