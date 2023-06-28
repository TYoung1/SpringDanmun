package com.example.Danmun.controller;

import com.example.Danmun.entity.User;
import com.example.Danmun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("/admin")
    public String adminPage(@ModelAttribute("list") ArrayList<User> list, Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        int grant = user.getGrant();
        if(grant < 1){
            return "redirect:/home";
        }
        model.addAttribute("list",list);
        return "content/admin";
    }
    @PostMapping("/typesearch")
    public String userSearch(@RequestParam("type") String type, @RequestParam("search") String search, Model model){
        List<User> list= adminService.searchUsersByType(type, search);
        model.addAttribute("list",list);
        return "content/admin";
    }
    @PostMapping("/action")
    public String someAction(@RequestParam("action") String action,@RequestParam("chk") String chk, Model model){
        adminService.doAction(action,chk);
        return "redirect:/admin";
    }
}
