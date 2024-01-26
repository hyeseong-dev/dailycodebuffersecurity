package com.dailycodebuffer.springsecurityclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 기본적인 웹 요청을 처리하는 컨트롤러.
 * '/hello' 경로로 들어오는 GET 요청을 처리합니다.
 */
@RestController
public class HelloController {

    /**
     * '/hello' 경로의 GET 요청에 대해 "hello world" 문자열을 반환합니다.
     * @return "hello world" 문자열
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
