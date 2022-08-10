package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.RoomGetResponseDTO;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping ("")
    public ResponseEntity getRoomList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        List<RoomGetResponseDTO> roomList = roomService.getRoomList(jwtService.getUserId(request));
        resultMap.put("message", "get Room info list success");
        resultMap.put("roomlist", roomList);

        return new ResponseEntity(resultMap, HttpStatus.OK);

    }





}
