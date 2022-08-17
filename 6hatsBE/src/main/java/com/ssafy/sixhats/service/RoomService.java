package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dao.UserRoomDAO;
import com.ssafy.sixhats.dto.room.RoomPostRequestDTO;
import com.ssafy.sixhats.dto.video.VideoGetResponseDTO;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.VideoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomDAO roomDAO;

    private final UserDAO userDAO;

    @Transactional
    public void postRoom(RoomPostRequestDTO roomPostRequestDTO, Long userId) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            RoomVO roomVO = RoomVO.builder()
                    .userVO(userVO)
                    .roomStartTime(new Date())
                    .sessionId(roomPostRequestDTO.getSessionId())
                    .build();
            roomDAO.save(roomVO);
        }
    }

    @Transactional
    public void patchRoom(Long roomId, String opinionFileUrl) {
        RoomVO roomVO =  roomDAO.findById(roomId).orElse(null);

        if(roomVO != null ) {
            Date date = new Date();
            roomVO.RoomUpdate(opinionFileUrl, date);

        }
    }

    @Transactional
    public void patchRoom(String sessionId, String opinionFileUrl) {
        RoomVO roomVO =  roomDAO.findBySessionId(sessionId).orElse(null);

        if(roomVO != null ) {
            Date date = new Date();
            roomVO.RoomUpdate(opinionFileUrl, date);

        }
    }

    @Transactional
    public List<VideoGetResponseDTO> getRoomVideos(Long roomId, Long userId) {

        UserVO userVO = userDAO.findById(userId).orElse(null);
        RoomVO roomVO = roomDAO.findById(roomId).orElse(null);

        if(userVO == null){
            throw new NullPointerException("User Not Found");
        } else if (roomVO == null) {
            throw new NullPointerException("Room Not Found");
        } else if (userId != roomVO.getUserVO().getUserId()) {
            throw new UnAuthorizedException();
        }

        List<VideoVO> videos = roomVO.getVideos();

        List<VideoGetResponseDTO> roomVideos = new ArrayList<>();

        //System.out.println(videos.size());

        for(VideoVO videoVO: videos) {
            VideoGetResponseDTO videoGetResponseDTO = new VideoGetResponseDTO()
                    .builder()
                    .videoFileUrl(videoVO.getVideoFileUrl())
                    .videoValid(videoVO.isVideoValid())
                    .build();
            roomVideos.add(videoGetResponseDTO);
        }

        return roomVideos;
    }

    @Transactional
    public List<VideoGetResponseDTO> getRoomVideos(String sessionId, Long userId) {

        UserVO userVO = userDAO.findById(userId).orElse(null);
        RoomVO roomVO = roomDAO.findBySessionId(sessionId).orElse(null);

        if(userVO == null){
            throw new NullPointerException("User Not Found");
        } else if (roomVO == null) {
            throw new NullPointerException("Room Not Found");
        } else if (userId != roomVO.getUserVO().getUserId()) {
            throw new UnAuthorizedException();
        }

        List<VideoVO> videos = roomVO.getVideos();

        List<VideoGetResponseDTO> roomVideos = new ArrayList<>();

        //System.out.println(videos.size());

        for(VideoVO videoVO: videos) {
            VideoGetResponseDTO videoGetResponseDTO = new VideoGetResponseDTO()
                    .builder()
                    .videoFileUrl(videoVO.getVideoFileUrl())
                    .videoValid(videoVO.isVideoValid())
                    .build();
            roomVideos.add(videoGetResponseDTO);
        }

        return roomVideos;
    }
}
