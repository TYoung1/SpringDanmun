package com.example.Danmun.service;

import com.example.Danmun.dto.AddWordDto;
import com.example.Danmun.dto.DeleteDto;
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
//    오늘의 단어
    public Word TodaysWord(){
       Word random =  wordRepository.findRandomWord();
        return random;
    }
//    검색이 가장많이된 단어 탑 10 
    public List<Word>TopWord(){
        List<Word> top = wordRepository.findTop10ByOrderByCountDesc();
        return top;
    }
//    내 단어장 단어 개수 
    public int MyWordCount(String user){
        int count = myWordRepository.countByUserId(user);
        return count;
    }
//    단어추가 
    public ResponseDto<?> addWord(AddWordDto dto){
        MyWord myword = dto.toEntity();
        try{
            boolean exists = myWordRepository.existsByUserIdAndSeq(myword.getUserId(),myword.getSeq());
            if(!exists){
                myWordRepository.save(myword);
            }else{
                return ResponseDto.setFailed("이미 존재하는 단어입니다.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseDto.setSuccess("저장 성공",null);
    }
//      타입별 단어장
    public List<Word> getTypeWord(int type){
            List<Word> list = wordRepository.findByType(type);
        return list;
    }
//    랜덤 미니게임 답안
    public Word randomAnswer(){
        Word word = wordRepository.findAnswer();
        return word;
    }
//    내단어장 페이징
    public Page<Word> myList(String userId, Pageable pageable){
        return wordRepository.findMyList(userId,pageable);
    }
//    내단어장 list 이용 단어 삭제
    public void deleteMyWord(DeleteDto dto){
        List<Integer> type = dto.getDeleteWord();
        String id = dto.getId();
        try{
            myWordRepository.deleteByUserIdAndSeqIn(id,type);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
//    단어추가
    public String addOne(String word,String userId){
        try{
            int seq = wordRepository.findSequence(word);
            boolean exists = myWordRepository.existsBySeq(seq);
            if(exists){
                return "이미 존재하는 단어 또는 존재하지않는 단어입니다.";
            }else{
                myWordRepository.saveWord(userId,seq);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "이미 존재하는 단어 또는 존재하지않는 단어입니다.";
        }
            return "";
    }
//    list이용 단어추가
    public String addList(List<Integer>list,String userid){
        for (Integer word : list) {
            if(myWordRepository.existsByUserIdAndSeq(userid,word)){
                return "이미 저장되어있는 단어가 포함되었습니다.";
            }else{
                try{
                    System.out.println(word==1);
                    myWordRepository.saveWord(userid,word);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }
        return "";
    }
}
