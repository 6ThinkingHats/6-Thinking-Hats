package com.ssafy.sixhats.controller;

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


    @PostMapping("")
    public ResponseEntity postUserRoom(Long roomId, HttpServletRequest request) {
        userRoomService.postUserRoom(roomId, jwtService.getUserId(request));

        return new ResponseEntity("join room success", HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity patchUserRoom(Long roomId, HttpServletRequest request) {
        userRoomService.patchUserRoom(roomId, jwtService.getUserId(request));

        return new ResponseEntity("user banned success", HttpStatus.OK);
    }
}
