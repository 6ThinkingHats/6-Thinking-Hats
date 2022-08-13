package com.ssafy.sixhats.dto.board;

import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;

import com.ssafy.sixhats.vo.type.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BoardPostRequestDTO {

    private String title;
    private String boardContents;
    private BoardType boardType;

    @Builder
    public BoardPostRequestDTO(String title, String boardContents, BoardType boardType) {
        this.title = title;
        this.boardContents = boardContents;
        this.boardType = boardType;
    }

    public void update(BoardType boardType) {
        this.boardType = boardType;
    }

    public BoardVO toEntity(UserVO userId) {
        return BoardVO.builder()
                .userId(userId)
                .title(title)
                .boardContents(boardContents)
                .boardType(boardType)
                .build();
    }

}
