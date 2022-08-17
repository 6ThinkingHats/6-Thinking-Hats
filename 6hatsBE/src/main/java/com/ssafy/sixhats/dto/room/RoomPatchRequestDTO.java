package com.ssafy.sixhats.dto.room;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RoomPatchRequestDTO {

    private String opinionFileUrl;

    @Builder
    public RoomPatchRequestDTO(String opinionFileUrl) {
        this.opinionFileUrl = opinionFileUrl;
    }


}
