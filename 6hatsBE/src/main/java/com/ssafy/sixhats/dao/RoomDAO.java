package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDAO extends JpaRepository<RoomVO, Long> {
    List<RoomVO> findAllByUserVO(UserVO userVO);
}
