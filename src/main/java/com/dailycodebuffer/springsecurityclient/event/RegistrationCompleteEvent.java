package com.dailycodebuffer.springsecurityclient.event;

import com.dailycodebuffer.springsecurityclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 사용자 등록 완료 시 발행되는 이벤트 클래스.
 * 사용자 정보와 애플리케이션 URL을 포함하며, 이메일 인증 등의 후속 처리에 사용됩니다.
 */
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;          // 등록된 사용자 정보
    private String applicationUrl;  // 애플리케이션 URL

    /**
     * 사용자 등록 완료 이벤트 생성자.
     * @param user 등록된 사용자 객체
     * @param applicationUrl 애플리케이션의 URL, 사용자 인증 링크 생성에 사용됨
     */
    public RegistrationCompleteEvent(User user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
