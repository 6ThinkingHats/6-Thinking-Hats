package com.ssafy.sixhats.dto.conference;

import com.ssafy.sixhats.vo.type.ConferenceCategory;
import com.ssafy.sixhats.vo.type.ConferencePurpose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@NoArgsConstructor
@ToString
public class ConferencePostRequestDTO {

    private Long roomId;

    private ConferenceCategory conferenceCategory;

    private ConferencePurpose conferencePurpose;

    @Builder
    public ConferencePostRequestDTO(Long roomId, ConferenceCategory conferenceCategory, ConferencePurpose conferencePurpose) {
        this.roomId = roomId;
        this.conferenceCategory = conferenceCategory;
        this.conferencePurpose = conferencePurpose;
    }

}
