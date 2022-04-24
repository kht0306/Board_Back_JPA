package com.seoulsystem.backboard.service;

import com.seoulsystem.backboard.entity.Board_Attachment;
import com.seoulsystem.backboard.model.BoardAttachmentRequestDto;
import com.seoulsystem.backboard.model.BoardAttachmentResponseDto;
import com.seoulsystem.backboard.repository.BoardAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardAttachmentService {

    @Autowired
    BoardAttachmentRepository boardAttachmentRepository;

    //첨부파일 등록처리
    public void boardAttachWrite(BoardAttachmentRequestDto boardAttachmentRequestDto) {
        try{
            boardAttachmentRepository.save(boardAttachmentRequestDto.toEntity());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    //등록된 첨부파일 정보 리턴
    public List<BoardAttachmentResponseDto> boardAttachmentByNo(int no) {

        List<Board_Attachment> attachmentEntityList = null;
        List<BoardAttachmentResponseDto> attachmentList = null;
        Board_Attachment entity = new Board_Attachment();
        BoardAttachmentResponseDto attachmentDto = new BoardAttachmentResponseDto();
        try {
            String delete_flag = "N";
            attachmentEntityList = boardAttachmentRepository.findByNoAndDeleteFlag(no, delete_flag);
            attachmentList = attachmentDto.listEntityToDto(attachmentEntityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attachmentList;
    }



    //첨부파일 테이블의 delete_flag 'N' -> 'Y'
    public void updateAttachDeleteFlag(int ano) {
        Board_Attachment entity =  boardAttachmentRepository.findById(ano).orElseThrow(()->new IllegalArgumentException("해당 번호의 첨부파일이 존재하지 않습니다."));
        entity.setDeleteFlag("Y");
        boardAttachmentRepository.save(entity); // 수정된 entity의 db 실제 수정처리
    }


    // 해당 게시글 번호로 등록된 첨부파일중 delete_flag 'N'인 파일의 갯수 확인
    public int confirmAttachDeleteFlag(int no, String delete_flag) {
        List<Board_Attachment> entityList = boardAttachmentRepository.findByNoAndDeleteFlag(no, delete_flag);
        return entityList.size();
    }

    // 해당 게시글의 첨부파일 delete_Flga N => Y처리
    public void updateBoardAttachDeleteFlag(int no) {
        try {
            List<Board_Attachment> boardList = boardAttachmentRepository.findByNoAndDeleteFlag(no, "N");
            for(Board_Attachment entity : boardList){
                entity.setDeleteFlag("Y");
                boardAttachmentRepository.save(entity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
