package com.example.Danmun.service;

import com.example.Danmun.dto.*;
import com.example.Danmun.entity.User;
import com.example.Danmun.repository.MyWordRepository;
import com.example.Danmun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service

public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MyWordRepository myWordRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    회원가입
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
//    중복체크
    public boolean existsId(String UserId){
        boolean exists = userRepository.existsById(UserId);
        return exists;
    }
// 로그인
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
// 아이디 찾기
    public String findId(FindInfoDto dto){
        String name = dto.getUserName();
        int age = dto.getUserAge();
        String id ="";
        User user;
        try{
            user = userRepository.findByUserNameAndUserAge(name,age);
            id=user.getUserId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }
// 임시비밀번호 저장
    public void updateNewPassword(String password, FindInfoDto dto){
        String newPassword = passwordEncoder.encode(password);
        String userEmail = dto.getUserId();
        try {
            userRepository.updateNewPassword(newPassword, userEmail);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//    기존 비밀번호 찾기
    public String findPassword(FindInfoDto dto) {
        String id = dto.getUserId();
        String name = dto.getUserName();
        int age = dto.getUserAge();
        String password = "";
        User user;
        try {
            user = userRepository.findUserPwByUserIdAndUserNameAndUserAge(id, name, age);
            password = user.getUserPw();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
//    새로운 임시 비밀번호 생성
    public String getRandomPassword( int length ){

        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
        StringBuffer sb = new StringBuffer();
        Random rn = new Random();
        for( int i = 0 ; i < length ; i++ ){
            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
        }
        return sb.toString();
    }
//    회원탈퇴
    public void deleteAll(String id){
        userRepository.deleteByUserId(id);
        myWordRepository.deleteByUserId(id);
    }
}
