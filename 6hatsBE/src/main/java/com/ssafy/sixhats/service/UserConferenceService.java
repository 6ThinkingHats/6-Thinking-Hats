package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.ConferenceDAO;
import com.ssafy.sixhats.dao.UserConferenceDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dto.conference.UserConferencePostRequestDTO;
import com.ssafy.sixhats.vo.ConferenceVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.UserConferenceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserConferenceService {

    private final UserConferenceDAO userConferenceDAO;

    private final UserDAO userDAO;

    private final ConferenceDAO conferenceDAO;

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
