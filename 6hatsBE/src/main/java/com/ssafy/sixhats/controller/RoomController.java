package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.room.RoomGetResponseDTO;
import com.ssafy.sixhats.dto.video.VideoGetResponseDTO;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    private final JwtService jwtService;

    @PostMapping("")
    public ResponseEntity postRoom(HttpServletRequest request) {
        roomService.postRoom(jwtService.getUserId(request));

        return new ResponseEntity("room create success", HttpStatus.CREATED);
    }

    @PatchMapping("{roomId}")
    public ResponseEntity patchRoom(@PathVariable Long roomId) {
        roomService.patchRoom(roomId, "not");

        return new ResponseEntity("exit room success", HttpStatus.OK);
    }

    @GetMapping("{roomId}/videos")
    public ResponseEntity getRoomVideos(@PathVariable Long roomId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        Long userId = jwtService.getUserId(request);

        List<VideoGetResponseDTO> roomVideos = roomService.getRoomVideos(roomId, userId);

        resultMap.put("message", "get room videos success");
        resultMap.put("roomVideos", roomVideos);

        return new ResponseEntity(resultMap, status);
    }
}
