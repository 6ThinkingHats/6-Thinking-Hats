package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.conference.UserConferencePostRequestDTO;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.UserConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user_conference")
@RequiredArgsConstructor
public class UserConferenceController {

    private final UserConferenceService userConferenceService;

    private final JwtService jwtService;

    @PostMapping("")
    public ResponseEntity postUserConference(HttpServletRequest request, @RequestBody UserConferencePostRequestDTO userConferencePostRequestDTO){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        userConferencePostRequestDTO.updateUserId(jwtService.getUserId(request));
        userConferenceService.postUserConference(userConferencePostRequestDTO);

        return new ResponseEntity(resultMap, status);
    }
}
