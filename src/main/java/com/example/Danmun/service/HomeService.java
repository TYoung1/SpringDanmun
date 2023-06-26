package com.example.Danmun.service;

import com.example.Danmun.dto.AddWordDto;
import com.example.Danmun.dto.ResponseDto;
import com.example.Danmun.entity.MyWord;
import com.example.Danmun.entity.Word;
import com.example.Danmun.repository.MyWordRepository;
import com.example.Danmun.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseDto<?> addWord(AddWordDto dto){
        MyWord myword = dto.toEntity();
        try{
            boolean exists = myWordRepository.existsByUserIdAndSeq(myword.getUserId(),myword.getSeq());
            if(exists == false){
                myWordRepository.save(myword);
            }else{
                return ResponseDto.setFailed("이미 존재하는 단어입니다.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseDto.setSuccess("저장 성공",null);
    }

    public List<Word> getTypeWord(int type){
        List<Word> list = wordRepository.findByType(type);
        return list;
    }
    public Word randomAnswer(){
        Word word = wordRepository.findAnswer();
        return word;
    }
    public Page<Word> myList(String userId, Pageable pageable){
        System.out.println(userId);
        return myWordRepository.findMyList(userId,pageable);

    }
}
