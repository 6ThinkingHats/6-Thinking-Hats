package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.UserConferenceDAO;
import com.ssafy.sixhats.vo.type.UserConferenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConferenceService {

    @Autowired
    UserConferenceDAO userConferenceDAO;

}
