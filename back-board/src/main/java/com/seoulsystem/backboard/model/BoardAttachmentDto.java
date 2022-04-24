package com.seoulsystem.backboard.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class BoardAttachmentDto {

    private int ano;
    private int no;
    private String orgName;
    private String saveName;
    private int size;
    private LocalDateTime regDate;
    private String savePath;
    private String delete_flag;
}
