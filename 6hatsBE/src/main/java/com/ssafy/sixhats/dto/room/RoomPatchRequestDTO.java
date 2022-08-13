package com.ssafy.sixhats.dto.room;


import lombok.Builder;

import java.util.Date;


public class RoomPatchRequestDTO {

    private Date roomEndTime;

    private String opinionFileUrl;


    @Builder
    public RoomPatchRequestDTO(Date roomEndTime, String opinionFileUrl) {
        this.roomEndTime = roomEndTime;
        this.opinionFileUrl = opinionFileUrl;
    }


}
