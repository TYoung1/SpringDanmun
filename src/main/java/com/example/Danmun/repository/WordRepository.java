package com.example.Danmun.repository;

import com.example.Danmun.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word,Long> {
    @Query(value = "select * from word order by rand() limit 1",nativeQuery = true)
    public Word findRandomWord();

    public List<Word>findTop10ByOrderByCountDesc();

    @Query(value = "select * from word where _word like %:keyword%",nativeQuery = true)
    public List<Word> matchWord(@Param("keyword")String keyword);

    public List<Word> findByType(int type);

    @Query(value = "select * from word where length(_word) = 5 order by rand() limit 1",nativeQuery = true)
    public Word findAnswer();

    @Query(value = "select * from word where _seq in (select _seq from myword where _userid = ?1)",nativeQuery = true)
    public Page<Word> findMyList(String userid, Pageable pageable);


    @Transactional
    @Query(value = "select _seq from word where _word = ?1",nativeQuery = true)
    public int findSequence(String word);
}
