package com.ssafy.sixhats.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor
public class RoomVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserVO userVO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "room_start_time")
    private Date roomStartTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    @Column(name = "room_end_time")
    private Date roomEndTime;

    @Column(name = "opinion_file_url")
    private String opinionFileUrl;

    @Column(name = "opinion_file_valid")
    private boolean opinionFileValid = true;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Builder
    public RoomVO(UserVO userVO, Date roomStartTime){
        this.userVO = userVO;
        this.roomStartTime = roomStartTime;
    }









}
