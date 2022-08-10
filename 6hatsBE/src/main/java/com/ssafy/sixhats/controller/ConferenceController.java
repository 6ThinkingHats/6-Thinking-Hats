package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.ConferencePostRequestDTO;
import com.ssafy.sixhats.service.ConferenceService;
import com.ssafy.sixhats.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("conference")
@RequiredArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping("")
    public ResponseEntity postConference(@RequestBody ConferencePostRequestDTO conferencePostRequestDTO){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;

        conferenceService.postConference(conferencePostRequestDTO);

        resultMap.put("message", "conference create success");
        return new ResponseEntity(resultMap, status);
    }

    @PatchMapping("{conferenceId}")
    public ResponseEntity patchConference(@PathVariable Long conferenceId){ // end time만 바꿔주면 됌
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        conferenceService.patchConference(conferenceId);

        resultMap.put("message", "conference patch success");
        return new ResponseEntity(resultMap, status);
    }

}
