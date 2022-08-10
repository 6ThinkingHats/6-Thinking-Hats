package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardListResponseDTO {

    private final String name;
    private final Long boardId;
    private final String title;
    private final BoardType boardType;
    private final int views;

    @Builder
    public BoardListResponseDTO(BoardVO board) {
        this.name = board.getUserId().getName();
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.boardType = board.getBoardType();
        this.views = board.getViews();
    }
}