package com.example.Danmun.controller;

import com.example.Danmun.dto.*;
import com.example.Danmun.entity.User;
import com.example.Danmun.repository.MyWordRepository;
import com.example.Danmun.repository.UserRepository;
import com.example.Danmun.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;


//    회원가입
    @GetMapping("/signup")
    public String signupForm(){
        return "content/signup";
    }
    @PostMapping("/signup")
    public String singup(SignUpDto dto){
        authService.join(dto);
        return "redirect:/home";
    }
//    계정 중복체크
    @PostMapping("/existid")
    @ResponseBody
    public String existsId(@RequestParam("userid") String userId){
       boolean result =  authService.existsId(userId);
       if(userId==null||userId==""){
           return "true";
       }
       if(result == true){
           return "true";
       }else{
           return "false";
       }
    }
//    로그인
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
//   로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
//    회원서비스
    @GetMapping("/service")
    public String userService(@ModelAttribute("find") String find,@ModelAttribute("find2") String find2,Model model){
        model.addAttribute("find",find);
        model.addAttribute("find2",find2);
        return "content/service";
    }
    @PostMapping("/findid")
    public String findId(FindInfoDto dto,Model model){
        String id = authService.findId(dto);
        if(id==""|| id== null){
            model.addAttribute("find", "해당하는계정이 없습니다.");
            return "content/service";
        }
        model.addAttribute("find",id);
        return "content/service";
    }
    @PostMapping("/findpw")
    public String findPw(FindInfoDto dto, RedirectAttributes model){
        String password = authService.findPassword(dto);
        if(password=="" || password == null){
            model.addAttribute("find2", "해당하는계정이 없습니다.");
            return "redirect:/auth/service";
        }
        String newPw = authService.getRandomPassword(10);
        authService.updateNewPassword(newPw,dto);
        model.addAttribute("find2",newPw);
        return "redirect:/auth/service";
    }
//    회원탈퇴
    @GetMapping("/withdrawal")
    public String quit(HttpSession session){
        User us = (User) session.getAttribute("user");
        if(us == null )
            return "redirect:/home";
        return "content/withdrawal";
    }
    @PostMapping("/withdrawal")
    public String quitFunc(DeleteDto dto,HttpSession session){
        String id = dto.getId();
        authService.deleteAll(id);
        session.invalidate();
        return "redirect:/home";
    }
}
