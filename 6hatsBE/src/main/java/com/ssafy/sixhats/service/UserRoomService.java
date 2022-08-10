package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dao.UserRoomDAO;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserRoomVO;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoomService {

    @Autowired
    UserRoomDAO userRoomDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoomDAO roomDAO;

    @Transactional
    public void postUserRoom(Long roomId, Long userId) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        RoomVO roomVO = roomDAO.findById(roomId).orElse(null);

        if(userVO==null) {
            throw new NullPointerException("user not found");
        } else if (roomVO == null) {
            throw new NullPointerException("room not found");
        }
        if(!roomVO.isActive()) {
            throw new NullPointerException("room is done");
        }
        UserRoomVO userRoomVO = userRoomDAO.findUserRoomVOByRoomVOAndUserVO(roomVO, userVO);
        if(userRoomVO != null) {
            if(!userRoomVO.isBanned()) {
                throw new UnAuthorizedException("you don't join this room");
            }
        } else {
            userRoomVO = new UserRoomVO().builder()
                    .roomVO(roomVO)
                    .userVO(userVO)
                    .build();
            userRoomDAO.save(userRoomVO);
        }
    }

    @Transactional
    public void patchUserRoom(Long roomId, Long userId) {
        RoomVO roomVO = roomDAO.findById(roomId).orElse(null);
        UserVO userVO = userDAO.findById(userId).orElse(null);

        if(userVO==null) {
            throw new NullPointerException("user not found");
        } else if (roomVO == null) {
            throw new NullPointerException("room not found");
        }
        UserRoomVO userRoomVO = userRoomDAO.findUserRoomVOByRoomVOAndUserVO(roomVO, userVO);
        if(userRoomVO == null) {
            throw new NullPointerException("join user is not found");
        }
        userRoomVO.setBanned(!userRoomVO.isBanned());


    }
}
