package com.ssafy.sixhats.controller;

import java.util.List;

import com.ssafy.sixhats.dto.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.BoardResponseDTO;
import com.ssafy.sixhats.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    /** 게시글 - 목록 조회  */
    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<BoardResponseDTO>> findAll() {

        List<BoardResponseDTO> boardResponseDtoList = boardService.findAll();

        return new ResponseEntity<List<BoardResponseDTO>>(boardResponseDtoList, HttpStatus.OK);
    }

    /** 게시글 - 상세 조회 */
    @GetMapping(value = "/{board_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<BoardResponseDTO> findById(@PathVariable("boardId") int boardId) {

        BoardResponseDTO boardResponseDto = boardService.findById(boardId);

        return new ResponseEntity<BoardResponseDTO>(boardResponseDto, HttpStatus.OK);
    }

    /** 게시글 - 등록 */
    @PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Integer> save(@RequestBody BoardPostRequestDTO boardPostRequestDto) {

        int savedBoardSeq = boardService.post(boardPostRequestDto);

        return new ResponseEntity<Integer>(savedBoardSeq, HttpStatus.CREATED);
    }

    /** 게시글 - 수정 */
    @PatchMapping (value = "/{board_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Integer> patch(@PathVariable("boardId") int boardId, @RequestBody BoardPatchRequestDTO boardPatchRequestDTO) {

        int patchedboardId = boardService.patch(boardId, boardPatchRequestDTO);

        return new ResponseEntity<Integer>(patchedboardId, HttpStatus.CREATED);
    }

    /** 게시글 - 삭제 */
    @DeleteMapping(value = "/{board_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Integer> delete(@PathVariable("boardId") int boardId) {

        boardService.delete(boardId);

        return new ResponseEntity<Integer>(boardId, HttpStatus.NO_CONTENT);
    }
}
