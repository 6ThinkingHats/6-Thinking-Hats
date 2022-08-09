package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDTO {

    private final String name;
    private final int boardId;
    private final String title;
    private final String board_contents;
    private final BoardType boardType;
    private final int views;

    @Builder
    public BoardResponseDTO(BoardVO board) {
        this.name = board.getUserId().getName();
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.board_contents = board.getBoard_contents();
        this.boardType = board.getBoardType();
        this.views = board.getViews();
    }
}