package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.room.RoomPostRequestDTO;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user_room")
@RequiredArgsConstructor
public class UserRoomController {

    private final UserRoomService userRoomService;
    private final JwtService jwtService;

    // 방 참여 확인 코드
    @PostMapping("")
    public ResponseEntity postUserRoom(@RequestBody RoomPostRequestDTO roomPostRequestDTO, HttpServletRequest request) {
        userRoomService.postUserRoom(roomPostRequestDTO, jwtService.getUserId(request));

        return new ResponseEntity("join room success", HttpStatus.OK);
    }

    // 강퇴처리 코드
    @PatchMapping("")
    public ResponseEntity patchUserRoom(@RequestBody RoomPostRequestDTO roomPostRequestDTO, HttpServletRequest request) {
        userRoomService.patchUserRoom(roomPostRequestDTO, jwtService.getUserId(request));

        return new ResponseEntity("user banned success", HttpStatus.OK);
    }

}
