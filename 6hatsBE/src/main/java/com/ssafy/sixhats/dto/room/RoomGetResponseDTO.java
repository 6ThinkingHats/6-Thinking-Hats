package com.ssafy.sixhats.dto.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RoomGetResponseDTO {

    private Date roomStartTime;

    private Date roomEndTime;

    private String opinionFileUrl;

    @Builder
    public RoomGetResponseDTO(Date roomStartTime, Date roomEndTime, String opinionFileUrl) {
        this.roomStartTime = roomStartTime;
        this.roomEndTime = roomEndTime;
        this.opinionFileUrl = opinionFileUrl;
    }


}
