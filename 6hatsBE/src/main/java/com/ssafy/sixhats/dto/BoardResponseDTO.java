package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.domain.Board;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Getter;

@Getter
public class BoardResponseDTO {

    private final UserVO userId;
    private final String title;
    private final String board_contents;
    private final BoardType boardType;
    private final int views;

    public BoardResponseDTO(Board board) {
        this.userId = board.getUserId();
        this.title = board.getTitle();
        this.board_contents = board.getBoard_contents();
        this.boardType = board.getBoardType();
        this.views = board.getViews();
    }
}