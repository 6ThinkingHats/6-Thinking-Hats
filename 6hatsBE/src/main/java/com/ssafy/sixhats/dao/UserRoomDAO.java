package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserRoomVO;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoomDAO extends JpaRepository<UserRoomVO, Long> {
    UserRoomVO findUserRoomVOByRoomVOAndUserVO(RoomVO roomVO, UserVO userVO);
    List<UserRoomVO> findAllByUserVOAndBanned(UserVO userVO, boolean banned, Pageable pageable);
}
