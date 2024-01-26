package com.dailycodebuffer.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 정보를 나타내는 엔티티 클래스.
 */
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @Column(length = 60)
    private String passworld;

    private String role;
    private boolean enabled = false;

}
