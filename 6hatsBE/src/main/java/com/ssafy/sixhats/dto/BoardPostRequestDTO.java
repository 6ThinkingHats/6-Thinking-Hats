package com.ssafy.sixhats.dto;
import com.ssafy.sixhats.domain.Board;

import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPostRequestDTO {

    private UserVO userId;
    private String title;
    private String board_contents;
    private BoardType boardType;

    @Builder
    public BoardPostRequestDTO(UserVO userId, String title, String board_contents, BoardType boardType) {
        this.userId = userId;
        this.title = title;
        this.board_contents = board_contents;
        this.boardType = boardType;
    }

    public Board toEntity() {
        return Board.builder()
                .userId(userId)
                .title(title)
                .board_contents(board_contents)
                .boardType(boardType)
                .build();
    }
}
