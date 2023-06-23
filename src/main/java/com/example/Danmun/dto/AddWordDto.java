package com.example.Danmun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWordDto {
   private int addWord;
   private String id;
   private String key;

}
