package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user_room")
public class UserRoomController {

    @Autowired
    UserRoomService userRoomService;
    @Autowired
    JwtService jwtService;


    @PostMapping("")
    public ResponseEntity postUserRoom(Long roomId, HttpServletRequest request) {
        userRoomService.postUserRoom(roomId, jwtService.getUserId(request));

        return new ResponseEntity("join room success", HttpStatus.ACCEPTED);
    }

    @PatchMapping("")
    public ResponseEntity patchUserRoom(Long roomId, HttpServletRequest request) {
        userRoomService.patchUserRoom(roomId, jwtService.getUserId(request));

        return new ResponseEntity("user banned success", HttpStatus.ACCEPTED);
    }
}
