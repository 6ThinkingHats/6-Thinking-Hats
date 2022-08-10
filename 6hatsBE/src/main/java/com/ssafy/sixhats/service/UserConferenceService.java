package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.ConferenceDAO;
import com.ssafy.sixhats.dao.UserConferenceDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dto.UserConferencePostRequestDTO;
import com.ssafy.sixhats.vo.ConferenceVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.UserConferenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserConferenceService {

    @Autowired
    UserConferenceDAO userConferenceDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ConferenceDAO conferenceDAO;

    @Transactional
    public void postUserConference(UserConferencePostRequestDTO userConferencePostRequestDTO) {

        UserVO userVO = userDAO.findById(userConferencePostRequestDTO.getUserId()).orElse(null);
        ConferenceVO conferenceVO = conferenceDAO.findById(userConferencePostRequestDTO.getConferenceId()).orElse(null);

        if(userVO == null || !userVO.isActive()) {
            throw new NullPointerException("user not found");
        } else if ( conferenceVO == null || conferenceVO.getConferenceEndTime() != null) {
            throw new NullPointerException("conference not found");
        }

        UserConferenceVO userConferenceVO = new UserConferenceVO().builder()
                .userVO(userVO)
                .conferenceVO(conferenceVO)
                .userRole(userConferencePostRequestDTO.getRole())
                .build();

        userConferenceDAO.save(userConferenceVO);
    }

}
