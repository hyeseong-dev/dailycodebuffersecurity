package com.dailycodebuffer.springsecurityclient.service;

import com.dailycodebuffer.springsecurityclient.entity.User;
import com.dailycodebuffer.springsecurityclient.entity.VerificationToken;
import com.dailycodebuffer.springsecurityclient.model.UserModel;
import com.dailycodebuffer.springsecurityclient.repository.UserRepository;
import com.dailycodebuffer.springsecurityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

/**
 * UserService 인터페이스의 구현 클래스.
 * 사용자 등록 및 인증 토큰 관리 기능을 제공합니다.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    /**
     * 사용자를 등록하는 메서드입니다.
     * 받은 UserModel 객체의 데이터를 사용하여 새로운 User 엔티티를 생성하고 저장합니다.
     * @param userModel 사용자 모델 객체
     * @return 저장된 User 엔티티 객체
     */
    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassworld(bcryptPasswordEncoder.encode(userModel.getPassword()));
        return userRepository.save(user);
    }

    /**
     * 사용자에 대한 인증 토큰을 저장합니다.
     * @param token 생성된 인증 토큰
     * @param user 사용자 엔티티 객체
     */
    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    /**
     * 주어진 인증 토큰의 유효성을 검증합니다.
     * 토큰이 유효하지 않거나 만료된 경우 해당하는 문자열을 반환합니다.
     * @param token 검증할 인증 토큰
     * @return 토큰의 상태를 나타내는 문자열 ("invalid", "expired" 또는 null)
     */
    @Override
    public String validateVerificationToken(String token) {
        Optional<VerificationToken> optionalToken  = verificationTokenRepository.findByToken(token);

        // 토큰이 존재하지 않는 경우
        if(!optionalToken.isPresent()){
            return "invalid";
        }

        VerificationToken verificationToken = optionalToken.get();
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        // 토큰이 만료된 경우
        if ( (verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime() ) <= 0) { // 토큰만료시간 - 현재서버시간 <= 0 : 만료된 토큰
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        // 토큰이 유효한 경우
        user.setEnabled(true);
        userRepository.save(user);

        return "valid";
    }
}
