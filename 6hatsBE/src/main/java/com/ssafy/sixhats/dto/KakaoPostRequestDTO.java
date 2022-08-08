package com.ssafy.sixhats.dto;

import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import com.ssafy.sixhats.vo.type.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class KakaoPostRequestDTO {
    private String email;
    private String password;
    private String name;
    private Gender gender;

    private Job job;

    // kakao에서 birth를 받아올 수 없음
    @Builder
    public KakaoPostRequestDTO(String email, String password, String name, Gender gender){
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.job = Job.OTHER;
    }

    public UserVO toEntity() {
        return UserVO.builder()
                .email(email)
                .password(password)
                .name(name)
                .gender(gender)
                .build();
    }
}
