package com.seoulsystem.backboard.repository;

import com.seoulsystem.backboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer>{

    //default
    Page<Board> findByDeleteFlag(String delete_flag, Pageable pageable);

    //keyword없이 검색 했을 때(삭제된 게시글 제외한 전체 결과 리턴+선택날짜)
    Page<Board> findByDeleteFlagAndRegDateBetween(String attachmentFlag, LocalDate startDate, LocalDate endDate, Pageable pageable);


    //검색범위 제목+내용 둘다(삭제된 게시글 제외한 전체 결과 리턴)
    Page<Board> findByTitleContainingOrContentContainingAndDeleteFlagLikeAndRegDateBetween(String title, String content,String deleteFlag, LocalDate startDate, LocalDate endDate , Pageable pageable);

    //검색범위 제목(삭제된 게시글 제외한 전체 결과 리턴)
    Page<Board> findByTitleContainingAndDeleteFlagLikeAndRegDateBetween(String title, String deleteFlag, LocalDate startDate, LocalDate endDate , Pageable pageable);

    //검색범위 내용(삭제된 게시글 제외한 전체 결과 리턴)
    Page<Board> findByContentContainingAndDeleteFlagLikeAndRegDateBetween(String content,String deleteFlag, LocalDate startDate, LocalDate endDate , Pageable pageable);
    
    //삭제된 파일 제외한 전체 리스트 리턴
    List<Board> findAllByDeleteFlag(String deleteFlag,Sort no);

}
