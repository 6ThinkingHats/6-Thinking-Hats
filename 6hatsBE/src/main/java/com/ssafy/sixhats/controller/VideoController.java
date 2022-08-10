package com.ssafy.sixhats.controller;


import com.ssafy.sixhats.dto.VideoGetResponseDTO;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoController {


    @Autowired
    VideoService videoService;

    @PostMapping("")
    public ResponseEntity postVideo(String videoFileUrl, Long roomId) {
        videoService.postVideo(videoFileUrl, roomId);
        return new ResponseEntity("video save success", HttpStatus.ACCEPTED);
    }

    @PatchMapping("{videoId}")
    public  ResponseEntity patchVideo(@PathVariable Long videoId) {
        videoService.patchVideo(videoId);

        return new ResponseEntity("video delete success", HttpStatus.NO_CONTENT);

    }

    @GetMapping("{videoId}")
    public ResponseEntity getVideo(@PathVariable Long videoId) {
        Map<String, Object> resultMap = new HashMap<>();
        VideoGetResponseDTO videoGetResponseDTO = videoService.getVideo(videoId);

        resultMap.put("message", "get video info success");
        resultMap.put("video", videoGetResponseDTO);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity getVideoList(Long roomId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<VideoGetResponseDTO> videoList = videoService.getVideoList(roomId);

        resultMap.put("message", "get video info list success");
        resultMap.put("videolist", videoList);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

}
