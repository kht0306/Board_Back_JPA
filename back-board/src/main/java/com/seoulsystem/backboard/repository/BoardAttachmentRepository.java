package com.seoulsystem.backboard.repository;

import com.seoulsystem.backboard.entity.Board_Attachment;
import com.seoulsystem.backboard.model.BoardAttachmentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardAttachmentRepository  extends JpaRepository<Board_Attachment,Integer> {
    
    //해당 게시글에 등록된 첨부파일 중 delete_flag가 "N"인 파일 리스트
    List<Board_Attachment> findByNoAndDeleteFlag(int no, String delete_flag);
}
