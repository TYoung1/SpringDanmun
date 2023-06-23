package com.example.Danmun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_seq")
    private int seq;
    @Column(name="_word")
    private String word;
    @Column(name="_mean")
    private String mean;
    @Column(name="_count")
    private int count;
    @Column(name="_type")
    private int type;

}
