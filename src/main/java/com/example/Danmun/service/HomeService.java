package com.example.Danmun.service;

import com.example.Danmun.entity.MyWord;
import com.example.Danmun.entity.Word;
import com.example.Danmun.repository.MyWordRepository;
import com.example.Danmun.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    @Autowired
    WordRepository wordRepository;
    @Autowired
    MyWordRepository myWordRepository;
    public Word TodaysWord(){
       Word random =  wordRepository.findRandomWord();
        return random;
    }
    public List<Word>TopWord(){
        List<Word> top = wordRepository.findTop10ByOrderByCountDesc();
        return top;
    }
    public int MyWordCount(String user){
        int count = myWordRepository.countByUserId(user);
        return count;
    }
}
