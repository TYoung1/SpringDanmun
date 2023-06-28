package com.example.Danmun.service;

import com.example.Danmun.entity.User;
import com.example.Danmun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired AuthService authService;
    public List<User> searchUsersByType(String type,String search){
        List<User> userList;
        if (type.equals("userId")) {
            userList = userRepository.findUserIdType(search);
        } else if (type.equals("userName")) {
            userList = userRepository.findUserNameType(search);
        } else{
            userList = userRepository.findGenderType(search);
        }
        return userList;
    }
    public void doAction(String action,String id){
        if(action.equals("ban")){
            authService.deleteAll(id);
        }else if(action.equals("grant")){
            userRepository.grantUser(id);
        }else{
            userRepository.revokeUser(id);
        }
    }
}
