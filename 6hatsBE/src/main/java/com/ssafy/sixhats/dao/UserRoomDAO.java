package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserRoomVO;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomDAO extends JpaRepository<UserRoomVO, Long> {
    UserRoomVO findUserRoomVOByRoomVOAndUserVO(UserVO userVO, RoomVO roomVO);
}
