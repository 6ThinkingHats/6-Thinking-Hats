package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.VideoVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoDAO extends JpaRepository<VideoVO, Long> {
    List<VideoVO> findAllByRoomVO(RoomVO roomVO);
}
