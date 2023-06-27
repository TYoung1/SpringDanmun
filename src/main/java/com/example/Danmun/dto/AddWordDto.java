package com.example.Danmun.dto;

import com.example.Danmun.entity.MyWord;
import com.example.Danmun.entity.User;
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
   private int type;

   public MyWord toEntity() {
      MyWord entity = new MyWord();
      entity.setUserId(this.id);
      entity.setSeq(this.addWord);
      return entity;
   }

}
