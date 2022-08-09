package com.ssafy.sixhats.controller;

import java.util.List;

import com.ssafy.sixhats.dto.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.BoardResponseDTO;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.service.BoardService;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    JwtService jwtService;


    //공지사항 전체
    @GetMapping(value = "/notice")
    public ResponseEntity<List<BoardResponseDTO>> findNoticeAll() {

        List<BoardResponseDTO> boardResponseDTOList = boardService.findNoticeAll();

        return new ResponseEntity(boardResponseDTOList, HttpStatus.OK);
    }

    //qna 전체
    @GetMapping(value = "/qna")
    public ResponseEntity<List<BoardResponseDTO>> findQnaAll() {

        List<BoardResponseDTO> boardResponseDTOList = boardService.findQnaAll();

        return new ResponseEntity(boardResponseDTOList, HttpStatus.OK);
    }

    //게시글 하나
    @GetMapping(value = "/{board_id}")
    public ResponseEntity<BoardResponseDTO> findById(@PathVariable("board_id") int boardId) {

        BoardResponseDTO boardResponseDTO = boardService.findById(boardId);

        return new ResponseEntity(boardResponseDTO, HttpStatus.OK);
    }

    //게시글 작성
    @PostMapping(value = "")
    public ResponseEntity post(@RequestBody BoardPostRequestDTO boardPostRequestDTO) {

        boardService.post(boardPostRequestDTO);

        return new ResponseEntity("board create success", HttpStatus.CREATED);
    }

    //게시글 수정
    @PatchMapping(value = "/{board_id}")
    public ResponseEntity patch(@PathVariable("board_id") int boardId, @RequestBody BoardPatchRequestDTO boardPatchRequestDTO, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);
        boardService.patch(boardId, userId, boardPatchRequestDTO);

        return new ResponseEntity("board update success", HttpStatus.CREATED);
    }

    //게시글 삭제
    @DeleteMapping(value = "/{board_id}")
    public ResponseEntity delete(@PathVariable("board_id") int boardId, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);

        boardService.delete(boardId, userId);

        return new ResponseEntity("board delete success", HttpStatus.NO_CONTENT);
    }
}
