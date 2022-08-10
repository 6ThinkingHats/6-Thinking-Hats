package com.ssafy.sixhats.vo;

import com.ssafy.sixhats.vo.ConferenceVO;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "user_conference")
@Getter
public class UserConferenceVO {

    @Id
    @Column(name = "user_conference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userConferecneId;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private ConferenceVO conferenceVO;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserVO userVO;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Builder
    public UserConferenceVO(ConferenceVO conferenceVO, UserVO userVO, Role userRole){
        this.conferenceVO = conferenceVO;
        this.userVO = userVO;
        this.userRole = userRole;
    }
}
