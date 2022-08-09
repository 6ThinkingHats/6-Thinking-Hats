package com.ssafy.sixhats.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoGetResponseDTO {

    private String videoFileUrl;

    private boolean videoValid;

    @Builder
    public VideoGetResponseDTO(String videoFileUrl, boolean videoValid) {
        this.videoFileUrl = videoFileUrl;
        this.videoValid = videoValid;
    }

}
