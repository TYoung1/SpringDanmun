package com.example.Danmun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindInfoDto {
    private String userId;
    private String userName;
    private int userAge;
}
