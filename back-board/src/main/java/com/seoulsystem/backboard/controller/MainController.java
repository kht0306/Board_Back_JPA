package com.seoulsystem.backboard.controller;

import com.seoulsystem.backboard.entity.Board;
import com.seoulsystem.backboard.model.*;
import com.seoulsystem.backboard.service.BoardAttachmentService;
import com.seoulsystem.backboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("board")
public class MainController {

    @Autowired
    private BoardService bs;
    @Autowired
    private BoardAttachmentService bas;
    private UUID uuid = UUID.randomUUID();

    @CrossOrigin
    @GetMapping("list")
    public ResponseEntity<?> list(@PageableDefault(page = 0, size = 20 ,sort = "no", direction = Sort.Direction.DESC) Pageable pageable, BoardListVo boardListVo){
        int nowPage;
        int totalPage;
        int pageBlock = 10; // 페이징 블럭 단위
        int startBlockPage;
        int endBlockPage;

        Page<Board> list = bs.getSearchBoardList(pageable, boardListVo);
            //페이징 관련 처리
            nowPage = list.getPageable().getPageNumber(); // 현재 페이지
            totalPage = list.getTotalPages();
            startBlockPage = (nowPage / pageBlock) * pageBlock + 1; // 페이징 시작 블럭 (int/int) = int
            endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = totalPage < endBlockPage ? totalPage : endBlockPage;

            return new ResponseEntity<>(list, HttpStatus.OK);
        }

    //게시글 정보(최초 및 마지막 날짜 확보용)
    @CrossOrigin
    @GetMapping("firstLastDates")
    public ResponseEntity<?> firstLastDates() {
        List<BoardResponseDto> dtoList = bs.firstLastDates();
        List<BoardResponseDto> firstLastDates = new ArrayList<>();
        firstLastDates.add(dtoList.get(0));
        firstLastDates.add(dtoList.get(dtoList.size()-1));
        return new ResponseEntity<>(firstLastDates, HttpStatus.OK);
    }


    //해당 게시글 정보 리턴
    @CrossOrigin
    @GetMapping("view/{no}")
    public ResponseEntity<?> boardByNo(@PathVariable  int no) {
        BoardResponseDto boardResponseDto = bs.boardByNo(no);
        System.out.println(boardResponseDto.toString());
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    // 등록된 첨부파일 정보 리턴
    @CrossOrigin
    @GetMapping("attachment/{no}")
    public ResponseEntity<?> boardAttachmentByNo(@PathVariable  int no) {
         List<BoardAttachmentResponseDto> attachmentList = bas.boardAttachmentByNo(no);
        return new ResponseEntity<>(attachmentList, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/write")
    public ResponseEntity<?> boardWrite(BoardRequestDto boardRequestDto, List<MultipartFile> attachment) {
        //  첨부파일을 list로 받을 때 파일을 실제로 전송하지 않아도 list에 null관련 값이 추가 되기 때문에 list의 첫번째 인자의 size가 0인지 여부를 통해 첨부파일 업로드 여부를 확인함.
        if (attachment.get(0).getSize() > 0) { // 첨부파일이 있을 때
            boardRequestDto.setAttachment_flag("Y"); // Attachment_flag Y로 변경
            Board board = bs.boardWrite(boardRequestDto); // 1) 게시글 테이블 db에 등록

            int lastInserId =  board.getNo();
            System.out.println("board 테이블 등록된 pk값 확인  = "+board.getNo());

            //첨부파일 DB 등록을 위한 BoardAttachmentDto entity선언
            BoardAttachmentRequestDto boardAttachmentRequestDto = new BoardAttachmentRequestDto();
            for (MultipartFile multipartFile : attachment) {
                String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\files";
                String fileName = uuid +"-"+multipartFile.getOriginalFilename();
                File saveFile = new File(projectPath, fileName); // 실제 파일 저장 처리

                boardAttachmentRequestDto.setNo(lastInserId);// 첨부파일 테이블에 게시판 테이블 PK값 저장위한 SET처리
                boardAttachmentRequestDto.setOrgName(multipartFile.getOriginalFilename());
                boardAttachmentRequestDto.setSaveName(fileName);
                boardAttachmentRequestDto.setSize((int)multipartFile.getSize());
                boardAttachmentRequestDto.setSavePath(projectPath+"\\"+fileName);

                bas.boardAttachWrite(boardAttachmentRequestDto); // 첨부파일 테이블(board_attachment) insert
            }
            return new ResponseEntity<>("게시글을 성공적으로 등록하였습니다.", HttpStatus.CREATED);
        } else { // 첨부파일 없을때
            bs.boardWrite(boardRequestDto); // 게시글 테이블만 db에 등록
            return new ResponseEntity<>("게시글을 성공적으로 등록하였습니다.", HttpStatus.CREATED);
        }
    }

    //게시글 수정
    @CrossOrigin
    @PutMapping("/modify")
    public ResponseEntity<?> boardModify(BoardRequestDto boardRequestDto, List<MultipartFile> attachment, @RequestParam List<Integer> delAno) {
        System.out.println(boardRequestDto.toString());
        //1-1) 신규 첨부파일이 있을 있을 때
        if (attachment.get(0).getSize() > 0) {
            BoardAttachmentRequestDto boardAttachmentRequestDto = new BoardAttachmentRequestDto();
            for (MultipartFile multipartFile : attachment) {
                String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\files";
                String fileName = uuid +"-"+multipartFile.getOriginalFilename();
                File saveFile = new File(projectPath, fileName); // 실제 파일 저장 처리

                boardAttachmentRequestDto.setNo(boardRequestDto.getNo());// 첨부파일 테이블에 게시판 테이블 PK값 저장위한 SET처리
                boardAttachmentRequestDto.setOrgName(multipartFile.getOriginalFilename());
                boardAttachmentRequestDto.setSaveName(fileName);
                boardAttachmentRequestDto.setSize((int)multipartFile.getSize());
                boardAttachmentRequestDto.setSavePath(projectPath+"\\"+fileName);
                bas.boardAttachWrite(boardAttachmentRequestDto); // 첨부파일 테이블(board_attachment) insert
            }
            //1-2 기등록된 첨부파일의 삭제 요청이 있을 때
            boardRequestDto.setAttachment_flag("Y"); // 신규 첨부파일이 등록되었음으로 attachment_Flag N -> Y 변경처리
            if(!delAno.isEmpty() || delAno.size() > 0){
                for(int i=1; i<-delAno.size(); i++){
                    bas.updateAttachDeleteFlag(delAno.get(i-1)); // 첨부파일의 delete_flag N -> Y 변경처리(첨부파일 테이블의 pk)
                }
                bs.updateBoard(boardRequestDto); // 게시물 정보들 수정처리
                return new ResponseEntity<>("게시글을 성공적으로 수정하였습니다.", HttpStatus.OK);
            }else{ // 1-3 기등록된 첨부파일의 삭제 요청이 없을 때
               bs.updateBoard(boardRequestDto); // 게시물 정보들 수정처리
                return new ResponseEntity<>("게시글을 성공적으로 수정하였습니다.", HttpStatus.OK);
            }
        }else { // 2-1) 신규 첨부파일이 없을 때
            if(!delAno.isEmpty() || delAno.size() > 0) { // 기등록된 첨부파일의 삭제 요청이 있을 때
                for (int i = 0; i < delAno.size(); i++) {
                    bas.updateAttachDeleteFlag(delAno.get(i)); // 첨부파일의 delete_flag N -> Y 변경처리
                }
                // 해당 게시글 번호로 등록된 첨부파일 중 delete_flag 'N'인 파일의 갯수 확인
                String delete_flag = "N";
                int attachmentCnt = bas.confirmAttachDeleteFlag(boardRequestDto.getNo(), delete_flag);
                if(attachmentCnt > 0){ // 해당 게시글 번호로 등록된 첨부파일중 delete_flag 'N'인 파일이 존재 할 때
                    boardRequestDto.setAttachment_flag("Y");
                    bs.updateBoard(boardRequestDto); // 게시물 정보들 수정처리
                    return new ResponseEntity<>("게시글을 성공적으로 수정하였습니다.", HttpStatus.OK);
                }else{
                    boardRequestDto.setAttachment_flag("N");
                    bs.updateBoard(boardRequestDto); // 게시물 정보들 수정처리
                    return new ResponseEntity<>("게시글을 성공적으로 수정하였습니다.", HttpStatus.OK);
                }
            }else{ //기등록된 첨부파일의 삭제 요청이 없을 때
                bs.updateBoard(boardRequestDto); // 게시물 정보들 수정처리
                return new ResponseEntity<>("게시글을 성공적으로 수정하였습니다.", HttpStatus.OK);
                }
        }
    }
    
    //작성자 검증 처리
    @CrossOrigin
    @GetMapping("/validation")
    public ResponseEntity<?> validationWriter (int no, String writerValid, String passwordValid){
        BoardResponseDto boardResponseDto = bs.validationWriter(no,writerValid,passwordValid);
        if(boardResponseDto.getWriter().equals(writerValid) && boardResponseDto.getPassword().equals(passwordValid)){
            return new ResponseEntity<>(1,HttpStatus.OK); // 작성자 검증 성공
        }else{
            return new ResponseEntity<>(0,HttpStatus.OK); // 작성자 검증 실패
        }
    }
    
    //게시글 삭제
    @CrossOrigin
    @DeleteMapping ("/delete/{no}")
    public ResponseEntity<?> deleteBoard (@PathVariable int no) {
        //board의 attachment_flag여부 확인
        BoardResponseDto boardResponseDto = bs.boardByNo(no); // 해당번호 게시글 entity 호출
        System.out.printf("첨부파일 존재 여부 확인 = "+boardResponseDto.getAttachment_flag().equals("Y"));
        if(boardResponseDto.getAttachment_flag().equals("Y")){ // 해당 게시글의 첨부파일 존재할때
            bas.updateBoardAttachDeleteFlag(no); // 해당 게시글 첨부파일 테이블 delete_Flga N => Y 처리
            bs.updateBoardDeleteFlag(no); // 해당 게시글 테이블 delete_Flag N => Y 처리
            return new ResponseEntity<>("게시글을 성공적으로 삭제하였습니다.",HttpStatus.OK);
        }else{// 해당 게시글의 첨부파일 존재 안 할 때
            bs.updateBoardDeleteFlag(no); // 해당 게시글 테이블 delete_Flag N => Y 처리
            return new ResponseEntity<>("게시글을 성공적으로 삭제하였습니다.",HttpStatus.OK);
        }
    }
}