package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserPatchRequestDTO {
    private String password;

    @Builder
    public UserPatchRequestDTO(String password){
        this.password = password;
    }

}
