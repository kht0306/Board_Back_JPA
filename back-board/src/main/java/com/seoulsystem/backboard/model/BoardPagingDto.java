package com.seoulsystem.backboard.model;

import lombok.Data;

@Data
public class BoardPagingDto {

    //board db정보 외 페이징 관련 변수들
    private int nowPage; // 현재 페이지
    private int startBlockPage; // 페이지 블럭 시작점
    private int endBlockPage; // 페이지 블럭 종료점
}
