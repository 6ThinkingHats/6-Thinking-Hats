package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dao.UserRoomDAO;
import com.ssafy.sixhats.dto.room.RoomGetResponseDTO;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserVO;
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

    private final UserRoomDAO userRoomDAO;

    @Transactional
    public void postRoom(Long userId) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            RoomVO roomVO = RoomVO.builder()
                    .userVO(userVO)
                    .roomStartTime(new Date())
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
    public List<RoomGetResponseDTO> getRoomList(Long userId) {
        List<RoomVO> roomList = new ArrayList<>();
        roomList = roomDAO.findAllByUserVO(userDAO.findById(userId).orElse(null));

        List<RoomGetResponseDTO> roomDtoList = new ArrayList<>();
        for (RoomVO roomVO:roomList) {
            roomDtoList.add(new RoomGetResponseDTO().builder()
                            .roomStartTime(roomVO.getRoomStartTime())
                            .roomEndTime(roomVO.getRoomEndTime())
                            .opinionFileUrl(roomVO.getOpinionFileUrl())
                            .build());
        }
        return roomDtoList;
    }

}
