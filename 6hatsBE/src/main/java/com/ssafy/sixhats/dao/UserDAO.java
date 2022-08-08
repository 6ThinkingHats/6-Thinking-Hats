package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.UserVO;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<UserVO, Long> {
    Optional<UserVO> findByEmailAndPassword(String email, String password);
}
