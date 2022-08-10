package com.ssafy.sixhats.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.sixhats.vo.type.ConferenceCategory;
import com.ssafy.sixhats.vo.type.ConferencePurpose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "conference")
@Getter
@NoArgsConstructor
public class ConferenceVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private Long conferenceId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomVO roomVO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "conference_start_time")
    private Date conferenceStartTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    @Column(name = "conference_end_time")
    private Date conferenceEndTime;

    @Column(name = "conference_category", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ConferenceCategory conferenceCategory;

    @Column(name = "conference_purpose", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ConferencePurpose conferencePurpose;

    @Builder
    public ConferenceVO(RoomVO roomVO, ConferenceCategory conferenceCategory, ConferencePurpose conferencePurpose, Date conferenceStartTime) {
        this.roomVO = roomVO;
        this.conferenceCategory = conferenceCategory;
        this.conferencePurpose = conferencePurpose;
        this.conferenceStartTime = conferenceStartTime;
    }

    public void updateConferenceEndTime(Date conferenceEndTime){
        this.conferenceEndTime = conferenceEndTime;
    }
}
