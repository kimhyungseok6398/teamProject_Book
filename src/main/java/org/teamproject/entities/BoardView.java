package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
@IdClass(BoardView.class)
public class BoardView {
    @Id
   private Long id;// 게시글 번호
    @Id
   private String uid;// Ip , + UA + 회원번호
}
