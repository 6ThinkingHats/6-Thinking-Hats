package com.ssafy.sixhats.dto.conference;

import com.ssafy.sixhats.vo.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserConferencePostRequestDTO {

    private Long userId;
    private Long conferenceId;
    private Role role;

    @Builder
    public UserConferencePostRequestDTO(Long conferenceId, Role role){
        this.conferenceId = conferenceId;
        this.role = role;
    }

    public void updateUserId(Long userId) {
        this.userId = userId;
    }
}
