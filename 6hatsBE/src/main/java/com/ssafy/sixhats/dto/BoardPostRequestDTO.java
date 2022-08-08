package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;

import com.ssafy.sixhats.vo.type.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPostRequestDTO {

    private Long userId;
    private String title;
    private String board_contents;
    private BoardType boardType;

    @Builder
    public BoardPostRequestDTO(Long userId, String title, String board_contents, BoardType boardType) {
        this.userId = userId;
        this.title = title;
        this.board_contents = board_contents;
        this.boardType = boardType;
    }

    public void update(BoardType boardType) {
        this.boardType = boardType;
    }

    public BoardVO toEntity(UserVO userId) {

        return BoardVO.builder()
                .userId(userId)
                .title(title)
                .board_contents(board_contents)
                .boardType(boardType)
                .build();
    }

}
