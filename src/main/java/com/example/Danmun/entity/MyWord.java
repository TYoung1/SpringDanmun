package com.example.Danmun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="myword")
public class MyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_order")
    private int order;
    @Column(name="_userid")
    private String userId;
    @Column(name="_seq")
    private int seq;
}
