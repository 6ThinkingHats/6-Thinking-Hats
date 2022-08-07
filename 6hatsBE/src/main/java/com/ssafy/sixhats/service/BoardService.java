package com.ssafy.sixhats.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.sixhats.domain.Board;
import com.ssafy.sixhats.domain.BoardRepository;
import com.ssafy.sixhats.dto.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.BoardResponseDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 전체
    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findAll() {

        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    //게시글 하나
    @Transactional(readOnly = true)
    public BoardResponseDTO findById(int boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalAccessError("[boardId=" + boardId + "] 해당 게시글이 존재하지 않습니다."));

        return new BoardResponseDTO(board);
    }

    //게시글 작성
    @Transactional
    public Integer post(BoardPostRequestDTO boardPostRequestDTO) {

        return boardRepository.save(boardPostRequestDTO.toEntity())
                .getBoardId();
    }

    //게시글 수정
    @Transactional
    public Integer patch(Integer boardId, BoardPatchRequestDTO boardPatchRequestDTO) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalAccessError("[boardId=" + boardId + "] 해당 게시글이 존재하지 않습니다."));

        board.patch(boardPatchRequestDTO.getTitle(), boardPatchRequestDTO.getBoard_contents());

        return boardId;
    }

    //게시글 삭제
    @Transactional
    public void delete(int boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalAccessError("[boardId=" + boardId + "] 해당 게시글이 존재하지 않습니다."));

        boardRepository.delete(board);
    }
}
