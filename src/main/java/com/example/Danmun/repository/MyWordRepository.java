package com.example.Danmun.repository;

import com.example.Danmun.entity.MyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyWordRepository extends JpaRepository<MyWord,Long> {
    public int countByUserId(String userid);
}
