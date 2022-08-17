package com.ssafy.sixhats.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class TxtPostRequestDTO {
    private String sessionId;
    private String contents;

    @Builder
    public TxtPostRequestDTO(String sessionId, String contents){
        this.sessionId= sessionId;
        this.contents=contents;
    }
}
