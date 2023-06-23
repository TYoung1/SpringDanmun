package com.example.Danmun.dto;

import com.example.Danmun.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String userId;
    private String userPw;
    private String passwordChk;
    private String userName;
    private int userAge;
    private String userGender;

    public User toEntity() {
        User entity = new User();
        entity.setUserId(this.userId);
        entity.setUserPw(this.userPw);
        entity.setUserName(this.userName);
        entity.setUserAge(this.userAge);
        entity.setGender(this.userGender);
        return entity;
    }
}

