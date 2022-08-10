package com.ssafy.sixhats.dao;

import com.ssafy.sixhats.vo.ConferenceVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceDAO extends JpaRepository<ConferenceVO, Long> {
}
