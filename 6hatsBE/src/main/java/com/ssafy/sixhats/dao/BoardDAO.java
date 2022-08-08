package com.ssafy.sixhats.dao;
import com.ssafy.sixhats.vo.BoardVO;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDAO extends JpaRepository<BoardVO, Integer>{


}
