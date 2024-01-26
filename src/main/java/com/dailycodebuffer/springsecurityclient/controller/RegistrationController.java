package com.dailycodebuffer.springsecurityclient.controller;

import com.dailycodebuffer.springsecurityclient.entity.User;
import com.dailycodebuffer.springsecurityclient.event.RegistrationCompleteEvent;
import com.dailycodebuffer.springsecurityclient.model.UserModel;
import com.dailycodebuffer.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 등록을 처리하는 컨트롤러.
 * '/register' 경로로 들어오는 POST 요청을 처리합니다.
 */
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 사용자 등록을 위한 POST 요청을 처리합니다.
     * UserModel 객체를 받아 사용자를 등록하고, 등록 이벤트를 발행합니다.
     * @param userModel 사용자 등록 데이터를 담은 객체
     * @param request HttpServletRequest 객체
     * @return 등록 성공 시 "Success" 문자열 반환
     */
    @PostMapping("/register")
    @ResponseBody
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);

        publisher.publishEvent(new RegistrationCompleteEvent(user,
                                                            this.applicationUrl(request)));
        return "Success";
    }

    /**
     * 사용자 인증 토큰을 검증하는 GET 요청을 처리합니다.
     * @param token 인증 토큰
     * @return 사용자 검증 결과 문자열
     */
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User Verifies Successfully";
        }
        return "Bad User";
    }

    /**
     * 현재 애플리케이션의 URL을 반환합니다.
     * 이메일 인증 링크 생성 등에 사용됩니다.
     * @param request HttpServletRequest 객체
     * @return 현재 애플리케이션의 URL
     */
    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort();
    }

}
