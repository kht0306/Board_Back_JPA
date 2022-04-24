package com.seoulsystem.backboard.entity;

import com.seoulsystem.backboard.model.BoardResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor   // 기본 생성자 추가
@DynamicInsert      //insert시 null값인 필드는 제외하고 insert
@DynamicUpdate      //update시 null값인 필드는 제외하고 update시
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*MySql의 autoincrement 처리*/
    private int no;

    @Column(columnDefinition = "varchar(60) not null")
    private String sort;

    @Column(columnDefinition = "varchar(300) not null")
    private String title;

    @Column(columnDefinition = "longtext not null")
    private String content;

    @Column(columnDefinition = "varchar(100) not null")
    private String email;

    @Column(columnDefinition = "varchar(5) not null")
    private String postcode;

    @Column(columnDefinition = "varchar(300) not null")
    private String roadAddr;

    @Column(columnDefinition = "varchar(300) not null")
    private String jibunAddr;

    @Column(columnDefinition = "varchar(300)")
    private String detailAddr;

    @Column(columnDefinition = "varchar(300)")
    private String refAddr;

    @Column(columnDefinition = "varchar(1)")
    @ColumnDefault("N")
    private String attachment_flag;

    @Column(columnDefinition = "varchar(100) not null")
    private String writer;

    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @Column(columnDefinition = "varchar(1)", name = "delete_flag")
    @ColumnDefault("N")
    private String deleteFlag;

    @Column(columnDefinition = "timestamp not null")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDate regDate;

    @Builder //Borad entity의 생성자(파라미터를 가지는 생성자)
    public Board(int no, String sort, String title, String content, String email,
                 String postcode, String roadAddr, String jibunAddr, String detailAddr, String refAddr,
                 String attachment_flag, String writer, String password, String delete_flag){
            this.no = no;
            this.sort = sort;
            this.title = title;
            this.content = content;
            this.email = email;
            this.postcode = postcode;
            this.roadAddr = roadAddr;
            this.jibunAddr =jibunAddr;
            this.detailAddr = detailAddr;
            this.refAddr = refAddr;
            this.attachment_flag = attachment_flag;
            this.writer = writer;
            this.password = password;
            this.deleteFlag = delete_flag;
    }


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
