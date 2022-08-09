package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.service.ConferenceService;
import com.ssafy.sixhats.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("confernce")
public class ConferenceController {

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    JwtService jwtService;

}
