package com.seoulsystem.backboard.service;
import com.seoulsystem.backboard.entity.Board;
import com.seoulsystem.backboard.model.BoardListVo;
import com.seoulsystem.backboard.model.BoardRequestDto;
import com.seoulsystem.backboard.model.BoardResponseDto;
import com.seoulsystem.backboard.repository.BoardAttachmentRepository;
import com.seoulsystem.backboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardAttachmentRepository boardAttachmentRepository;

    //검색 게시글 결과 호출
    public Page<Board> getSearchBoardList(Pageable pageable, BoardListVo boardListVo) {
        List<BoardResponseDto> dtoList = null;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        String delete_flag = "N";
        Page<Board> list = null;
        if (boardListVo.getStartDate() != null && boardListVo.getEndDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDate = LocalDate.parse(boardListVo.getStartDate(), formatter);
            endDate = LocalDate.parse(boardListVo.getEndDate(), formatter);
        }
        //검색어가 없을 때 (전체 검색 결과 리턴 / 기본 값)
        if (boardListVo.getStartDate() == null && boardListVo.getEndDate() == null) {
            list = boardRepository.findByDeleteFlag(delete_flag, pageable);
        } else if (boardListVo.getKeyword() == null || boardListVo.getKeyword().isEmpty() && boardListVo.getStartDate() != null && boardListVo.getEndDate() != null) {
            list = boardRepository.findByDeleteFlagAndRegDateBetween(delete_flag, startDate, endDate, pageable);
            //전체 검색
        } else if (boardListVo.getKeyword() != null && boardListVo.getOption().equals("total") && boardListVo.getStartDate() != null && boardListVo.getEndDate() != null) {
            list = boardRepository.findByTitleContainingOrContentContainingAndDeleteFlagLikeAndRegDateBetween(boardListVo.getKeyword(), boardListVo.getKeyword(), delete_flag, startDate, endDate, pageable);
            //제목 검색
        } else if (boardListVo.getKeyword() != null && boardListVo.getOption().equals("title") && boardListVo.getStartDate() != null && boardListVo.getEndDate() != null) {
            list = boardRepository.findByTitleContainingAndDeleteFlagLikeAndRegDateBetween(boardListVo.getKeyword(), delete_flag, startDate, endDate, pageable);
            //내용 검색
        } else if (boardListVo.getKeyword() != null && boardListVo.getOption().equals("content") && boardListVo.getStartDate() != null && boardListVo.getEndDate() != null) {
            list = boardRepository.findByContentContainingAndDeleteFlagLikeAndRegDateBetween(boardListVo.getKeyword(), delete_flag, startDate, endDate, pageable);
        }
        return list;
    }


    //해당 게시글의 정보 호출(삭제된 게시글은 제외처리 기능 추가 예정)
    public BoardResponseDto boardByNo(int no) {
        Board entity = boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다."));
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        return boardResponseDto.fromEntity(entity);
    }

    //게시글 등록처리(최근 등록한 board 테이블의 pk값 확인 위해 리턴 받음)
    public Board boardWrite(BoardRequestDto boardRequestDto) {
        Board entity = null;
        try {
            entity = boardRepository.save(boardRequestDto.toEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    //작성자 검증
    public BoardResponseDto validationWriter(int no, String writerValid, String passwordValid) {
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        Board entity = boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다."));
        return boardResponseDto.fromEntity(entity);
    }

    //해당 게시물 delete_flag 'N' -> 'Y'
    public void updateBoardDeleteFlag(int no) {
        Board entity = boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다."));
        entity.setDeleteFlag("Y");
        boardRepository.save(entity); // 수정된 entity의 db 실제 수정처리
    }

    //신규 첨부파일 추가시 attachment_flag 'N' -> 'Y'
    public void updateBoardAttachmentFlag(BoardRequestDto boardRequestDto) {
        Board entity = boardRepository.findById(boardRequestDto.getNo()).orElseThrow(() -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다."));
        entity.setAttachment_flag("Y");
        boardRepository.save(entity); // 수정된 entity의 db 실제 수정처리

    }

    //게시글 업데이트 처리
    public void updateBoard(BoardRequestDto boardRequestDto) {
        Board entity = boardRepository.findById(boardRequestDto.getNo()).orElseThrow(() -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다."));
        entity.setSort(boardRequestDto.getSort());
        entity.setTitle(boardRequestDto.getTitle());
        entity.setContent(boardRequestDto.getContent());
        entity.setEmail(boardRequestDto.getEmail());
        entity.setPostcode(boardRequestDto.getPostcode());
        entity.setRoadAddr(boardRequestDto.getRoadAddr());
        entity.setJibunAddr(boardRequestDto.getJibunAddr());
        entity.setDetailAddr(boardRequestDto.getDetailAddr());
        entity.setRefAddr(boardRequestDto.getRefAddr());
        if (boardRequestDto.getAttachment_flag() != null) {
            entity.setAttachment_flag(boardRequestDto.getAttachment_flag());
        }
        entity.setPassword(boardRequestDto.getPassword());
        boardRepository.save(entity); // 수정된 entity의 db 실제 수정처리
    }

    //최초 게시글 정보(등록일=검색 날짜용) 리턴
    public List<BoardResponseDto> firstLastDates() {
        List<BoardResponseDto> dtoList = null;
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        List<Board> entityList = null;
        try {
            entityList = boardRepository.findAllByDeleteFlag("N",Sort.by(Sort.Order.asc("no")));
            dtoList = boardResponseDto.listEntityToDto(entityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtoList;
    }
}