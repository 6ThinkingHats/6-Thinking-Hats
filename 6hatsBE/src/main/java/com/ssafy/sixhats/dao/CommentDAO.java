package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.CommentVO;

import com.ssafy.sixhats.vo.type.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentDAO extends JpaRepository<CommentVO, Long> {
    List<CommentVO> findAll();
    List<CommentVO> findAllByBoardVO(BoardVO boardVO);
}

