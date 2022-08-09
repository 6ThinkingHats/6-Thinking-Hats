package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<UserVO, Long> {
    Optional<UserVO> findByEmailAndPassword(String email, String password);
    Optional<UserVO> findByEmail(String email);
}
