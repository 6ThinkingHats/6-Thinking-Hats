package com.ssafy.sixhats.controller;


import com.ssafy.sixhats.dto.video.VideoGetResponseDTO;
import com.ssafy.sixhats.dto.video.VideoPostRequestDTO;
import com.ssafy.sixhats.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
@RequiredArgsConstructor
public class VideoController {

    // 영상 저장소를 설정하면서 필요하면 수정하기

    private final VideoService videoService;

    @PostMapping("")
    public ResponseEntity postVideo(@RequestBody VideoPostRequestDTO videoPostRequestDTO) {

        videoService.postVideo(videoPostRequestDTO);
        return new ResponseEntity("video save success", HttpStatus.ACCEPTED);
    }

    @PatchMapping("{videoId}")
    public  ResponseEntity patchVideo(@PathVariable Long videoId) {
        videoService.patchVideo(videoId);

        return new ResponseEntity("video delete success", HttpStatus.NO_CONTENT);

    }

}
