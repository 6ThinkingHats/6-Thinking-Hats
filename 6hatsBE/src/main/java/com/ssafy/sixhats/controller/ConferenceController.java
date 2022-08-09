package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.ConferencePostRequestDTO;
import com.ssafy.sixhats.service.ConferenceService;
import com.ssafy.sixhats.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("conference")
public class ConferenceController {

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    JwtService jwtService;

    @PostMapping("")
    public ResponseEntity postConference(@RequestBody ConferencePostRequestDTO conferencePostRequestDTO){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;

        conferenceService.postConference(conferencePostRequestDTO);

        resultMap.put("message", "conference create success");
        return new ResponseEntity(resultMap, status);
    }
}
