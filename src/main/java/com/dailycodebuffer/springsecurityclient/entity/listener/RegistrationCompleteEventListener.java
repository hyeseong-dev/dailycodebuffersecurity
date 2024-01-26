package com.dailycodebuffer.springsecurityclient.entity.listener;

import com.dailycodebuffer.springsecurityclient.entity.User;
import com.dailycodebuffer.springsecurityclient.event.RegistrationCompleteEvent;
import com.dailycodebuffer.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 사용자 등록 완료 이벤트 리스너.
 * 사용자가 등록되면 실행되는 이벤트를 처리합니다.
 */
@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    /**
     * 사용자 등록 완료 이벤트 발생 시 호출됩니다.
     * 사용자에게 인증 토큰이 포함된 이메일 발송 로직을 구현할 수 있습니다.
     * @param event 사용자 등록 완료 이벤트
     */
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the Verification Token for the User with Link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // Send Mail to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token="+token;

        // sendVerificationEmail()
        log.info("Clik the link to verify your account: {}", url);
    }
}
