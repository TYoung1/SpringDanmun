package com.example.Danmun.repository;

import com.example.Danmun.entity.MyWord;
import com.example.Danmun.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MyWordRepository extends JpaRepository<MyWord,Long> {
    public int countByUserId(String userid);
    boolean existsByUserIdAndSeq(String userId, int seq);
    @Query(value = "select B.* from myword as A inner join word as B on A._seq = B._seq where A._userid = ?1",
            countQuery = "select count(B.*) from myword as A inner join word as B on A._seq = B._seq where A._userid = ?1",
            nativeQuery = true)
    public Page<Word> findMyList(String userid, Pageable pageable);
}
