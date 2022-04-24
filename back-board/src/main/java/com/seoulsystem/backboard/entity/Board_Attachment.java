package com.seoulsystem.backboard.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@DynamicInsert      //insert시 null값인 필드는 제외하고 insert
@DynamicUpdate      //update시 null값인 필드는 제외하고 update시
@Table(name="board_attachment")
public class Board_Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*MySql의 autoincrement 처리*/
    private int ano;

    @Column(columnDefinition = "int not null")
    private int no;

    @Column(columnDefinition = "varchar(300) not null")
    private String orgName;

    @Column(columnDefinition = "varchar(350) not null")
    private String saveName;

    @Column(columnDefinition = "int not null")
    private int size;

    @Column(columnDefinition = "timestamp not null")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDate regDate;

    @Column(columnDefinition = "varchar(200) not null")
    private String savePath;

    @Column(columnDefinition = "varchar(1)", name = "delete_flag")
    @ColumnDefault("N")
    private String deleteFlag;

    @Builder
    public Board_Attachment(int no, String orgName, String saveName, int size, String savePath, String delete_flag){
        this.no = no;
        this.orgName = orgName;
        this.saveName = saveName;
        this.size = size;
        this.savePath = savePath;
        this.deleteFlag = delete_flag;
    }
}
