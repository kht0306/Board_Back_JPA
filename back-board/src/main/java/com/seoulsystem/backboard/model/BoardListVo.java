package com.seoulsystem.backboard.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListVo {
    private String startDate;
    private String endDate;
    private String option;
    private String keyword;
}
