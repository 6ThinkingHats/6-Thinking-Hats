package com.ssafy.sixhats.dto.video;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class VideoPostRequestDTO {
    private String videoFileUrl;
    private String sessionId;

    @Builder
    public VideoPostRequestDTO(String videoFileUrl, String sessionId){
        this.videoFileUrl = videoFileUrl;
        this.sessionId = sessionId;
    }
}
