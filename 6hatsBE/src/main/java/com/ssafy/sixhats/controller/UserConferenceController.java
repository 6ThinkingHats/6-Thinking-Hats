package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.service.UserConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user_conference")
public class UserConferenceController {

    @Autowired
    UserConferenceService userConferenceService;

}
