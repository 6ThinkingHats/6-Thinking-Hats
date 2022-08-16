package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.CommentVO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDAO extends JpaRepository<CommentVO, Long> {
    List<CommentVO> findAll();
    List<CommentVO> findAllByBoardVO(BoardVO boardVO, Pageable pageable);
}

