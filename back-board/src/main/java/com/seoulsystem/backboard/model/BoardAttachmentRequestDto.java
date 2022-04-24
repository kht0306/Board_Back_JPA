package com.seoulsystem.backboard.model;

import com.seoulsystem.backboard.entity.Board;
import com.seoulsystem.backboard.entity.Board_Attachment;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class BoardAttachmentRequestDto {

    private int ano;
    private int no;
    private String orgName;
    private String saveName;
    private int size;
    private LocalDateTime regDate;
    private String savePath;
    private String delete_flag;

    public Board_Attachment toEntity(){
        return Board_Attachment.builder()
                .no(no)
                .orgName(orgName)
                .saveName(saveName)
                .size(size)
                .savePath(savePath)
                .delete_flag(delete_flag)
                .build();
    }
}
