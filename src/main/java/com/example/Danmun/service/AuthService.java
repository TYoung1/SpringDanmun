package com.example.Danmun.service;

import com.example.Danmun.dto.ResponseDto;
import com.example.Danmun.dto.SignInDto;
import com.example.Danmun.dto.SignInResponseDto;
import com.example.Danmun.dto.SignUpDto;
import com.example.Danmun.entity.User;
import com.example.Danmun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {
    @Autowired
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseDto<?> join(SignUpDto dto) {
        String password = dto.getUserPw();
        String passwordChk = dto.getPasswordChk();
        try {
            if (userRepository.existsById(dto.getUserId())) {
                return ResponseDto.setFailed("중복된 아이디 입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!password.equals(passwordChk)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }
        User user = dto.toEntity();

        String encPassword = passwordEncoder.encode(password);
        user.setUserPw(encPassword);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDto.setSuccess("회원가입 성공",null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto){
        String id = dto.getUserId();
        String password= dto.getUserPw();

        User userentity = null;
        try {
            userentity = userRepository.finduserid(id);
//			잘못된 이메일
            if(userentity == null) return ResponseDto.setFailed("sign in failed");
//			잘못된 패스워드
            if(!passwordEncoder.matches(password,userentity.getUserPw())) {
                return ResponseDto.setFailed("sign in failed");
            }

        }catch(Exception error) {
            return ResponseDto.setFailed("Database Error");
        }
        userentity.setUserPw("");


        SignInResponseDto signInResponseDto = new SignInResponseDto(userentity);
        return ResponseDto.setSuccess("sign in success !",signInResponseDto);
    }
}
