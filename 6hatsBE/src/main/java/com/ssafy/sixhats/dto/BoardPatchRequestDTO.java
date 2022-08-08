package com.ssafy.sixhats.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPatchRequestDTO {

    private String title;
    private String board_contents;

    @Builder
    public BoardPatchRequestDTO (String title, String board_contents) {
        this.title = title;
        this.board_contents = board_contents;
    }
}
