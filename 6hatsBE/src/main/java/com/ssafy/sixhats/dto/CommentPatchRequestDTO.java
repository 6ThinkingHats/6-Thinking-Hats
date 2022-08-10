package com.ssafy.sixhats.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPatchRequestDTO {

    private String commentContents;

    @Builder
    public CommentPatchRequestDTO (String commentContents) {
        
        this.commentContents = commentContents;
    }

}
