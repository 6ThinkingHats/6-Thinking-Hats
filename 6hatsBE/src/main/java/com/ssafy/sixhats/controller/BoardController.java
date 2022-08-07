package com.ssafy.sixhats.controller;

import java.util.List;

import com.ssafy.sixhats.dto.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.BoardResponseDTO;
import com.ssafy.sixhats.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    //게시글 전체
    @GetMapping(value = "")
    public ResponseEntity<List<BoardResponseDTO>> findAll() {

        List<BoardResponseDTO> boardResponseDTOList = boardService.findAll();

        return new ResponseEntity<List<BoardResponseDTO>>(boardResponseDTOList, HttpStatus.OK);
    }

    //게시글 하나
    @GetMapping(value = "/{board_id}")
    public ResponseEntity<BoardResponseDTO> findById(@PathVariable("board_id") int boardId) {

        BoardResponseDTO boardResponseDTO = boardService.findById(boardId);

        return new ResponseEntity<BoardResponseDTO>(boardResponseDTO, HttpStatus.OK);
    }

    //게시글 작성
    @PostMapping(value = "")
    public ResponseEntity<Integer> post(@RequestBody BoardPostRequestDTO boardPostRequestDTO) {

        int postedboardId = boardService.post(boardPostRequestDTO);

        return new ResponseEntity<Integer>(postedboardId, HttpStatus.CREATED);
    }

    //게시글 수정
    @PatchMapping (value = "/{board_id}")
    public ResponseEntity<Integer> patch(@PathVariable("board_id") int boardId, @RequestBody BoardPatchRequestDTO boardPatchRequestDTO) {

        int patchedboardId = boardService.patch(boardId, boardPatchRequestDTO);

        return new ResponseEntity<Integer>(patchedboardId, HttpStatus.CREATED);
    }

    //게시글 삭제
    @DeleteMapping(value = "/{board_id}")
    public ResponseEntity<Integer> delete(@PathVariable("board_id") int boardId) {

        boardService.delete(boardId);

        return new ResponseEntity<Integer>(boardId, HttpStatus.NO_CONTENT);
    }
}
