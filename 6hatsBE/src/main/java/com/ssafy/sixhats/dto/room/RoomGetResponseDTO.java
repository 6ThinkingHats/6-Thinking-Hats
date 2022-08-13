package com.ssafy.sixhats.dto.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RoomGetResponseDTO implements Comparable<RoomGetResponseDTO>{

    // room 생성자인지 프론트에서 확인하기 위한 용도
    private Long userId;

    // video request보낼 때 써야할 친구
    private Long roomId;
    
    private Date roomStartTime;

    private Date roomEndTime;
    
    private String opinionFileUrl;

    private boolean opinionFileValid;

    // 방을 생성한 사람인지 확인 -> true면 비디오 다운 가능
    private boolean isCreator;

    @Builder
    public RoomGetResponseDTO(Long userId, Long roomId, Date roomStartTime, Date roomEndTime, String opinionFileUrl, boolean opinionFileValid, boolean isCreator) {
        this.userId = userId;
        this.roomId = roomId;
        this.roomStartTime = roomStartTime;
        this.roomEndTime = roomEndTime;
        this.opinionFileUrl = opinionFileUrl;
        this.opinionFileValid = opinionFileValid;
        this.isCreator = isCreator;
    }

    @Override
    public int compareTo(RoomGetResponseDTO o) {
        return this.roomStartTime.compareTo(o.getRoomStartTime());
    }
}
