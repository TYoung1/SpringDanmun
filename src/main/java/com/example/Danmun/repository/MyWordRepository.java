package com.example.Danmun.repository;

import com.example.Danmun.entity.MyWord;
import com.example.Danmun.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MyWordRepository extends JpaRepository<MyWord,Long> {
    public int countByUserId(String userid);
    boolean existsByUserIdAndSeq(String userId, int seq);


    @Transactional
    boolean existsBySeq(int seq);
    @Transactional
    @Modifying
    public void deleteByUserIdAndSeqIn(String userId, List<Integer> wordSeqList);


    @Transactional
    @Modifying
    public void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "insert into myword (_userid,_seq) values(?1,?2)",nativeQuery = true)
    public void saveWord(String userid,int seq);



}
