package com.ssafy.sixhats.dao;
import com.ssafy.sixhats.vo.BoardVO;

import com.ssafy.sixhats.vo.type.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardDAO extends JpaRepository<BoardVO, Integer>{
    List<BoardVO> findByBoardType(BoardType boardType);
    //BoardVO findNoticeById(BoardVO boardId);
}
