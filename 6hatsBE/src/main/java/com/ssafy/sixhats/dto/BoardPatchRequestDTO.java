package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPatchRequestDTO {

    private Long userId;
    private String title;
    private String board_contents;
    private BoardType boardType;

    @Builder
    public BoardPatchRequestDTO (String title, String board_contents, BoardType boardType) {
        this.title = title;
        this.board_contents = board_contents;
        this.boardType = boardType;
    }

    public void update(BoardType boardType) {
        this.boardType = boardType;
    }



}
