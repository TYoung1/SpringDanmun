package com.example.Danmun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "_userid")
    private String userId;
    @Column(name = "_userpw")
    private String userPw;
    @Column(name = "_username")
    private String userName;
    @Column(name = "_userage")
    private int userAge;
    @Column(name = "_gender")
    private String gender;
    @Column(name = "_grant")
    private int grant;
}
