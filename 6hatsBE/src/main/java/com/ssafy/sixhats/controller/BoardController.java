package com.ssafy.sixhats.controller;

import java.util.List;

import com.ssafy.sixhats.dto.board.BoardListResponseDTO;
import com.ssafy.sixhats.dto.board.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.board.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.board.BoardResponseDTO;
import com.ssafy.sixhats.service.BoardService;
import com.ssafy.sixhats.service.JwtService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    private final JwtService jwtService;

    //공지사항 전체
    @GetMapping(value = "/notice")
    public ResponseEntity<List<BoardResponseDTO>> getBoardNoticeList(Pageable pageable) {
        System.out.println(pageable);
        // 페이징 값을 나중에 받아와야 할 것
        List<BoardListResponseDTO> boardResponseDTOList = boardService.getBoardNoticeList(pageable);

        return new ResponseEntity(boardResponseDTOList, HttpStatus.OK);
    }

    //qna 전체
    @GetMapping(value = "/qna")
    public ResponseEntity<List<BoardResponseDTO>> getBoardQnaList(Pageable pageable) {

        List<BoardListResponseDTO> boardResponseDTOList = boardService.getBoardQnaList(pageable);

        return new ResponseEntity(boardResponseDTOList, HttpStatus.OK);
    }

    //게시글 하나
    @GetMapping(value = "notice/{board_id}")
    public ResponseEntity<BoardResponseDTO> getBoardNotice(@PathVariable("board_id") Long boardId, HttpServletRequest request) {

        BoardResponseDTO boardResponseDTO = boardService.getBoardNotice(boardId);

        return new ResponseEntity(boardResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "qna/{board_id}")
    public ResponseEntity<BoardResponseDTO> getBoardQna(@PathVariable("board_id") Long boardId, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);
        BoardResponseDTO boardResponseDTO = boardService.getBoard(boardId, userId);

        return new ResponseEntity(boardResponseDTO, HttpStatus.OK);
    }

    //게시글 작성
    @PostMapping(value = "")
    public ResponseEntity postBoard(@RequestBody BoardPostRequestDTO boardPostRequestDTO, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);
        boardService.postBoard(userId, boardPostRequestDTO);

        return new ResponseEntity("board create success", HttpStatus.CREATED);
    }

    //게시글 수정
    @PatchMapping(value = "/{board_id}")
    public ResponseEntity patchBoard(@PathVariable("board_id") Long boardId, @RequestBody BoardPatchRequestDTO boardPatchRequestDTO, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);
        boardService.patchBoard(boardId, userId, boardPatchRequestDTO);

        return new ResponseEntity("board update success", HttpStatus.CREATED);
    }

    //게시글 삭제
    @DeleteMapping(value = "/{board_id}")
    public ResponseEntity deleteBoard(@PathVariable("board_id") Long boardId, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);

        boardService.delete(boardId, userId);

        return new ResponseEntity("board delete success", HttpStatus.NO_CONTENT);
    }
}
