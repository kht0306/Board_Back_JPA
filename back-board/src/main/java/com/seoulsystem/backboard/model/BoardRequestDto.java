package com.seoulsystem.backboard.model;

import com.seoulsystem.backboard.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardRequestDto {
    private int no;
    private String sort;
    private String title;
    private String content;
    private String email;
    private String postcode;
    private String roadAddr;
    private String jibunAddr;
    private String detailAddr;
    private String refAddr;
    private String attachment_flag;
    private String writer;
    private String password;
    private String delete_flag;
    private LocalDateTime regDate;
    
    //BoardRequestDto를 entity로 변환
    public Board toEntity(){
        return Board.builder()
                .sort(sort)
                .title(title)
                .content(content)
                .email(email)
                .postcode(postcode)
                .roadAddr(roadAddr)
                .jibunAddr(jibunAddr)
                .detailAddr(detailAddr)
                .refAddr(refAddr)
                .attachment_flag(attachment_flag)
                .writer(writer)
                .password(password)
                .delete_flag(delete_flag)
                .build();
    }

}
