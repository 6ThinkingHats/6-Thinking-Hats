package com.ssafy.sixhats.dto.video;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoGetResponseDTO {

    private Long videoId;
    private String videoFileUrl;
    private boolean videoValid;

    @Builder
    public VideoGetResponseDTO(Long videoId, String videoFileUrl, boolean videoValid) {
        this.videoId = videoId;
        this.videoFileUrl = videoFileUrl;
        this.videoValid = videoValid;
    }

}
