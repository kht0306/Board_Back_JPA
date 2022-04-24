package com.seoulsystem.backboard.model;

import com.seoulsystem.backboard.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
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
    private LocalDate regDate;

    public BoardResponseDto fromEntity(Board entity){
        return BoardResponseDto.builder()
                .no(entity.getNo())
                .sort(entity.getSort())
                .title(entity.getTitle())
                .content(entity.getContent())
                .email(entity.getEmail())
                .postcode(entity.getPostcode())
                .roadAddr(entity.getRoadAddr())
                .jibunAddr(entity.getJibunAddr())
                .detailAddr(entity.getDetailAddr())
                .refAddr(entity.getRefAddr())
                .attachment_flag(entity.getAttachment_flag())
                .writer(entity.getWriter())
                .password(entity.getPassword())
                .delete_flag(entity.getDeleteFlag())
                .regDate(entity.getRegDate())
                .build();
    }

    //entity타입 List를 responseDto타입 List로 전환처리
    public List<BoardResponseDto> listEntityToDto(List<Board> list){
        return list.stream().map(this::fromEntity)
                .collect(Collectors.toList());
    }

}
