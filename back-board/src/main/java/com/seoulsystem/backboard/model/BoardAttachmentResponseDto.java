package com.seoulsystem.backboard.model;

import com.seoulsystem.backboard.entity.Board_Attachment;
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
public class BoardAttachmentResponseDto {

    private int ano;
    private int no;
    private String orgName;
    private String saveName;
    private int size;
    private LocalDate regDate;
    private String savePath;
    private String delete_flag;


    //entity를 responseDto로 전환처리
    public BoardAttachmentResponseDto fromEntity(Board_Attachment entity){
        return BoardAttachmentResponseDto.builder()
                .ano(entity.getAno())
                .no(entity.getNo())
                .orgName(entity.getOrgName())
                .saveName(entity.getSaveName())
                .size(entity.getSize())
                .regDate(entity.getRegDate())
                .savePath(entity.getSavePath())
                .delete_flag(entity.getDeleteFlag())
                .build();
    }

    //entity타입 List를 responseDto타입 List로 전환처리
    public List<BoardAttachmentResponseDto> listEntityToDto(List<Board_Attachment> list){
        return list.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }
}
