package com.ssafy.sixhats.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "video")
public class VideoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomVO roomVO;

    @Column(name = "video_file_url")
    private String videoFileUrl;

    @Column(name = "video_valid", columnDefinition = "boolean default true")
    private boolean videoValid = true;

    @Builder
    public VideoVO(RoomVO roomVO, String videoFileUrl) {
        this.roomVO = roomVO;
        this.videoFileUrl = videoFileUrl;
    }

    public void updateVideoValid(boolean videoValid) {
        this.videoValid = videoValid;
    }

}
