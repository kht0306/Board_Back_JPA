package com.seoulsystem.backboard.repository;

import com.seoulsystem.backboard.entity.Board;
import com.seoulsystem.backboard.model.BoardListVo;
import com.seoulsystem.backboard.model.BoardResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomizedBoardRepository {
    List<?> getSearchList(BoardListVo boardListVo);
}
