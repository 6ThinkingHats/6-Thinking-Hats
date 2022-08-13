package com.ssafy.sixhats.dto.user;

import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class UserLoginRequestDTO {
    private String email;
    private String password;

    @Builder
    public UserLoginRequestDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

}
