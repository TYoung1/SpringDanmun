package com.example.Danmun.controller;

import com.example.Danmun.dto.ResponseDto;
import com.example.Danmun.dto.SignInDto;
import com.example.Danmun.dto.SignInResponseDto;
import com.example.Danmun.dto.SignUpDto;
import com.example.Danmun.entity.User;
import com.example.Danmun.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @GetMapping("/signup")
    public String signupForm(){
        return "content/signup";
    }
    @PostMapping("/signin")
    public String signin(SignInDto dto,HttpSession session){
        ResponseDto<SignInResponseDto> result = authService.signIn(dto);
        if(result.isResult()){
            User user = result.getData().getUser();
            System.out.println(user);
            session.setAttribute("user",user);
            return "redirect:/home";
        }
        return "redirect:/home";

    }
    @PostMapping("/signup")
    public String singup(SignUpDto dto){
        authService.join(dto);
        return "redirect:/home";
    }
}
