package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.ConferenceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {

    @Autowired
    ConferenceDAO conferenceDAO;

}
