package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserLoginRequestDTO {
    private String email;
    private String password;

    @Builder
    public UserLoginRequestDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCreateRequestDTO{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
