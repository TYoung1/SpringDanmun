package com.example.Danmun.repository;

import com.example.Danmun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query(value = "select * from user where _userid = ?1",nativeQuery = true)
    public User finduserid(String id);
}
