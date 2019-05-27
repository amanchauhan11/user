package com.example.user.service;

import com.example.user.entity.UserInfo;
import com.example.user.model.AuthForm;
import com.example.user.model.SignupForm;

public interface UserService {
    public UserInfo userLogin(AuthForm loginForm);

    public UserInfo userSignup(SignupForm signupForm);

    public UserInfo getUserById(Integer uid);
}
