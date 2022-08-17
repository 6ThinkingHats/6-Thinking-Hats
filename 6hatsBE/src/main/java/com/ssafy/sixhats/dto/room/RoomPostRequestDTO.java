package com.ssafy.sixhats.dto.room;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RoomPostRequestDTO {

    private String sessionId;


    @Builder
    public RoomPostRequestDTO(String sessionId) {
        this.sessionId = sessionId;
    }


}
