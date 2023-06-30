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
import org.springframework.util.StringUtils;
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
//    홈화면
    @GetMapping("/home")
    public String home(@ModelAttribute("message") String message,Model model, HttpSession session){
//       오늘의 단어
       Word random =  homeService.TodaysWord();
        List<Word> topList = homeService.TopWord();
//        세션 체크
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
//    검색결과페이지
    @GetMapping("/search")
    public String search(@RequestParam("keyword")String keyword,Model model){
        List<Word> matchWord = wordRepository.matchWord(keyword);
        model.addAttribute("list",matchWord);
        return "content/search";
    }
//      자격증별 타입단어장
    @GetMapping("/typeword")
    public String typeWord(@RequestParam("type") int type,Model model){
        List<Word> typeList = homeService.getTypeWord(type);
        model.addAttribute("type",type);
        model.addAttribute("typelist",typeList);
        return "content/typeword";
    }
//    단어장에 버튼이용 한개씩 추가
    @PostMapping("/addatlist")
    public String addAtList(AddWordDto dto,Model model){
        ResponseDto<?> result = homeService.addWord(dto);
        System.out.println(dto.getKey());
//         검색키워드 유무 확인,단어 저장 결과 반환
        if(StringUtils.isEmpty(dto.getKey())) {
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
//    JavaScript 이용한 미니게임 (Wordle) 랜덤 단어 (답)
    @GetMapping("/minigame")
    public String mini(Model model){
        Word answer =homeService.randomAnswer();
        model.addAttribute("answer",answer);
        return "content/wordgame";
    }
//    내 단어장 페이지
    @GetMapping("/myword")
        public String myword(@ModelAttribute("message") String message,Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null )
            return "redirect:/home";
        String userid = user.getUserId();
//       pageable 이용 페이징
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
//    단어장 단어 삭제
    @PostMapping("/deletemine")
    public String deleteWord(DeleteDto dto){
        homeService.deleteMyWord(dto);
        return "redirect:/myword";
    }
//    단어장에서 단어입력으로 추가
    @PostMapping("/addone")
    public String addOneWord(AddOneDto dto, Model model, RedirectAttributes redirectAttributes){
        String word = dto.getAddWord();
        String userid = dto.getId();
//      db에 존재하지않는 단어 또는 단어장에 이미 추가된단어 판단
       String message = homeService.addOne(word,userid);
       redirectAttributes.addFlashAttribute("message",message);
       return "redirect:/myword";
    }
//    checkbox이용 list로 단어추가
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
