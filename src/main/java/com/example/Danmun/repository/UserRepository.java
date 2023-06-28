package com.example.Danmun.repository;

import com.example.Danmun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query(value = "select * from user where _userid = ?1",nativeQuery = true)
    public User finduserid(String id);
    public User findByUserNameAndUserAge(String name,int age);
    public User findUserPwByUserIdAndUserNameAndUserAge(String id,String name,int age);

    @Modifying
    @Transactional
    @Query(value = "update user set _userpw = ?1 where _userid =?2",nativeQuery = true)
    public void updateNewPassword(String password, String email);

    @Transactional
    @Modifying
    public void deleteByUserId(String userid);

    @Query(value = "select * from user where _userid Like CONCAT('%', ?1, '%')",nativeQuery = true)
    public List<User> findUserIdType(String id);

    @Query(value = "select * from user where _username Like CONCAT('%', ?1, '%')",nativeQuery = true)
    public List<User> findUserNameType(String id);

    @Query(value = "select * from user where _gender Like CONCAT('%', ?1, '%')",nativeQuery = true)
    public List<User> findGenderType(String id);

    @Modifying
    @Transactional
    @Query(value = "update user set _grant = 0 where _userid = ?1",nativeQuery = true)
    public void revokeUser(String id);

    @Modifying
    @Transactional
    @Query(value = "update user set _grant = 1 where _userid = ?1",nativeQuery = true)
    public void grantUser(String id);
}
