package com.ssafy.sixhats.dto.board;

import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPatchRequestDTO {

    private String title;
    private String boardContents;

    @Builder
    public BoardPatchRequestDTO (String title, String boardContents) {
        this.title = title;
        this.boardContents = boardContents;
    }

}
