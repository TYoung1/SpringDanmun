package com.example.Danmun.controller;

import com.example.Danmun.dto.*;
import com.example.Danmun.entity.User;
import com.example.Danmun.entity.Word;
import com.example.Danmun.repository.WordRepository;
import com.example.Danmun.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    WordRepository wordRepository;
    @Autowired HomeService homeService;
    @GetMapping("/home")
    public String home(@ModelAttribute("message") String message,Model model, HttpSession session){
       Word random =  homeService.TodaysWord();
        List<Word> topList = homeService.TopWord();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int count = homeService.MyWordCount(user.getUserId());
            model.addAttribute("count",count);
        }
       model.addAttribute("message",message);
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

    @GetMapping("/typeword")
    public String typeWord(@RequestParam("type") int type,Model model){
        List<Word> typeList = homeService.getTypeWord(type);
        model.addAttribute("type",type);
        model.addAttribute("typelist",typeList);
        return "content/typeword";
    }
    @PostMapping("/addatlist")
    public String addAtList(AddWordDto dto,Model model){
        ResponseDto<?> result = homeService.addWord(dto);
        System.out.println(dto.getKey());
        if(dto.getKey() == null ||  dto.getKey() =="") {
            if (!result.isResult()) {
                model.addAttribute("alert", result.getMessage());
                return "redirect:/typeword?type=" + dto.getType();
            }
            return "redirect:/typeword?type=" + dto.getType();
        }else{
            if (!result.isResult()) {
                model.addAttribute("alert", result.getMessage());
                return "redirect:/search?keyword=" + dto.getKey();
            }
            return "redirect:/search?keyword="+dto.getKey();
        }

    }
    @GetMapping("/minigame")
    public String mini(Model model){
        Word answer =homeService.randomAnswer();
        model.addAttribute("answer",answer);
        return "content/wordgame";
    }
    @GetMapping("/myword")
        public String myword(@ModelAttribute("message") String message,Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,HttpSession session){
        User user = (User) session.getAttribute("user");
        String userid = user.getUserId();
        Page<Word> list=homeService.myList(userid,pageable);
        int nowPag = list.getPageable().getPageNumber() + 1;
        int startPag = Math.max(nowPag - 4, 1);
        int endPag = Math.min(nowPag + 5, list.getTotalPages());
        int count = homeService.MyWordCount(user.getUserId());
        model.addAttribute("message",message);
        model.addAttribute("count",count);
        model.addAttribute("myList",list);
        model.addAttribute("nowpage", nowPag);
        model.addAttribute("startpage", startPag);
        model.addAttribute("endpage", endPag);
        return "content/myword";
    }
    @PostMapping("/deletemine")
    public String deleteWord(DeleteDto dto){
        homeService.deleteMyWord(dto);
        return "redirect:/myword";
    }
    @PostMapping("/addone")
    public String addOneWord(AddOneDto dto, Model model, RedirectAttributes redirectAttributes){
        String word = dto.getAddWord();
        String userid = dto.getId();
       String message = homeService.addOne(word,userid);
       redirectAttributes.addFlashAttribute("message",message);
       return "redirect:/myword";
    }
    @PostMapping("/addlist")
    public String addList(AddListDto dto,RedirectAttributes redirectAttributes){
        List<Integer> list = dto.getSave();
        String userid = dto.getId();
        String message = homeService.addList(list,userid);
        if(message.length()>3){
            redirectAttributes.addFlashAttribute("message",message);
        }
        return "redirect:/home";
    }
}
