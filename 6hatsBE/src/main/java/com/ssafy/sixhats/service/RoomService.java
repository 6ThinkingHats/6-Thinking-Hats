package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dao.UserRoomDAO;
import com.ssafy.sixhats.dto.RoomGetResponseDTO;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserRoomVO;
import com.ssafy.sixhats.vo.UserVO;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomDAO roomDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserRoomDAO userRoomDAO;
    public void postRoom(Long userId) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            Date date = new Date();
            RoomVO roomVO = RoomVO.builder().userVO(userVO).roomStartTime(new Date(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds())).build();
            roomDAO.save(roomVO);
        }
    }

    @Transactional
    public void patchRoom(Long roomId, String opinionFileUrl) {
        RoomVO roomVO =  roomDAO.findById(roomId).orElse(null);
        System.out.println("=============");
        System.out.println(roomVO);
        if(roomVO != null ) {
            Date date = new Date();
            roomVO.setOpinionFileUrl(opinionFileUrl);
            roomVO.setRoomEndTime(new Date(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds()));
            roomVO.setActive(false);
            System.out.println("=============");
            System.out.println(roomVO);

        }
    }

//    public RoomGetResponseDTO getRoom(Long roomId) {
//        RoomVO roomVO = roomDAO.findById(roomId).orElse(null);
//        if(roomVO !=null) {
//            RoomGetResponseDTO roomGetResponseDTO = new RoomGetResponseDTO().builder()
//                    .roomStartTime(roomVO.getRoomStartTime())
//                    .roomEndTime(roomVO.getRoomEndTime())
//                    .opinionFileUrl(roomVO.getOpinionFileUrl())
//                    .build();
//            return roomGetResponseDTO;
//        } else{
//            throw new NullPointerException("Room info Not Found");
//        }
//    }

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

    public void joinPostRoom(Long roomId, Long userId) {
        RoomVO roomVO = roomDAO.findById(roomId).orElse(null);
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(roomVO != null && roomVO.isActive() && userVO != null && userVO.isActive()) {
            UserRoomVO userRoomVO = new UserRoomVO().builder()
                    .roomVO(roomVO)
                    .userVO(userVO)
                    .build();
        }
    }



}
