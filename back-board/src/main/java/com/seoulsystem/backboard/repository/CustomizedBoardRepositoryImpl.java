package com.seoulsystem.backboard.repository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seoulsystem.backboard.entity.Board;
import com.seoulsystem.backboard.entity.QBoard;
import com.seoulsystem.backboard.model.BoardListVo;
import com.seoulsystem.backboard.model.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@RequiredArgsConstructor
public class CustomizedBoardRepositoryImpl implements CustomizedBoardRepository{
    private final JPAQueryFactory jpaQueryFactory;

    EntityManager em;
    JPAQuery query = new JPAQuery(em);


    //검색 결과 처리
    @Override
    public List<?> getSearchList(BoardListVo boardListVo) {
        QBoard board = QBoard.board;

        List<?> entityList = jpaQueryFactory
                .from(board)
                .where(board.title.contains(boardListVo.getKeyword()).or(board.content.contains(boardListVo.getKeyword())))
                .orderBy(board.no.desc())
                .fetch();
        return entityList;
    }
}
