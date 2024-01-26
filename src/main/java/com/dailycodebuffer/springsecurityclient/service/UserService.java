package com.dailycodebuffer.springsecurityclient.service;

import com.dailycodebuffer.springsecurityclient.entity.User;
import com.dailycodebuffer.springsecurityclient.model.UserModel;


/**
 * 사용자 관련 서비스를 정의하는 인터페이스.
 */
public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);
}
