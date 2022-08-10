package com.ssafy.sixhats.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPatchRequestDTO {

    private String comment_contents;

    @Builder
    public CommentPatchRequestDTO (String comment_contents) {
        
        this.comment_contents = comment_contents;
    }

}
