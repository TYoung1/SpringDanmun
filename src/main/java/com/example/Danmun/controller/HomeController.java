package com.example.Danmun.controller;

import com.example.Danmun.dto.AddWordDto;
import com.example.Danmun.entity.User;
import com.example.Danmun.entity.Word;
import com.example.Danmun.repository.WordRepository;
import com.example.Danmun.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    WordRepository wordRepository;
    @Autowired HomeService homeService;
    @GetMapping("/home")
    public String home(Model model, HttpSession session){
       Word random =  homeService.TodaysWord();
        List<Word> topList = homeService.TopWord();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int count = homeService.MyWordCount(user.getUserId());
            model.addAttribute("count",count);
        }
       model.addAttribute("random",random);
       model.addAttribute("top",topList);
       return "content/home";
    }
    @GetMapping("/search")
    public String search(@RequestParam("keyword")String keyword,Model model){
        List<Word> matchWord = wordRepository.matchWord(keyword);
        model.addAttribute("list",matchWord);
        return "content/search";
    }
    @PostMapping("/addatlist")
    public String addAtList(AddWordDto dto){

    }

}
