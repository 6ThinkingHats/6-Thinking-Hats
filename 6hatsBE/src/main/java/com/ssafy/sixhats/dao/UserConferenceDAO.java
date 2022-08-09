package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.UserConferenceVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConferenceDAO extends JpaRepository<UserConferenceVO, Long> {
}
