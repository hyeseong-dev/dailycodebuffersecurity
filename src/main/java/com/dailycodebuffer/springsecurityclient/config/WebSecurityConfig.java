package com.dailycodebuffer.springsecurityclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Spring Security 설정을 위한 클래스.
 * CSRF 보호는 비활성화되어 있고, 세션 정책은 STATELESS로 설정됩니다.
 * 특정 경로(/hello, /register)는 인증 없이 접근이 가능하도록 설정합니다.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
        "/hello", "/register"
    };

    /**
     * BCryptPasswordEncoder를 빈으로 등록합니다.
     * 비밀번호 암호화에 사용됩니다.
     * @return BCryptPasswordEncoder 객체
     */
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    /**
     * Spring Security 필터 체인을 구성합니다.
     * CSRF 보호는 비활성화되어 있고, 세션 정책은 STATELESS로 설정됩니다.
     * 특정 경로(/hello, /register)는 인증 없이 접근이 가능하도록 설정합니다.
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain 객체
     * @throws Exception 예외 발생 시
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement)->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request ->request
                    .requestMatchers("/hello", "/register").permitAll()
                    .anyRequest().permitAll()
            );


        return http.build();
    }
}
