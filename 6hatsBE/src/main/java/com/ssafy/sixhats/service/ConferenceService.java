package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.ConferenceDAO;
import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dto.conference.ConferencePostRequestDTO;
import com.ssafy.sixhats.vo.ConferenceVO;
import com.ssafy.sixhats.vo.RoomVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final ConferenceDAO conferenceDAO;

    private final RoomDAO roomDAO;

    @Transactional
    public void postConference(ConferencePostRequestDTO conferencePostRequestDTO) {

        RoomVO roomVO = roomDAO.findById(conferencePostRequestDTO.getRoomId()).orElse(null);

        if(roomVO != null && roomVO.isActive()){
            ConferenceVO conferenceVO = new ConferenceVO().builder()
                    .roomVO(roomVO)
                    .conferenceCategory(conferencePostRequestDTO.getConferenceCategory())
                    .conferencePurpose(conferencePostRequestDTO.getConferencePurpose())
                    .conferenceStartTime(new Date())
                    .build();
            conferenceDAO.save(conferenceVO);
        } else {
            throw new NullPointerException("Room Not Found");
        }
    }

    @Transactional
    public void patchConference(Long conferenceId) {

        ConferenceVO conferenceVO = conferenceDAO.findById(conferenceId).orElse(null);

        if(conferenceVO != null && conferenceVO.getConferenceEndTime() == null){
            conferenceVO.updateConferenceEndTime(new Date());
        } else {
            throw new NullPointerException("Conference Not Found");
        }

    }

}
