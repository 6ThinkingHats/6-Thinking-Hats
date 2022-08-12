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
public class UserPostRequestDTO {
    private String email;
    private String password;
    private String name;
    private Job job;
    private LocalDate birth;
    private Gender gender;

    @Builder
    public UserPostRequestDTO(String email, String password, String name, Job job, LocalDate birth, Gender gender){
        this.email = email;
        this.password = password;
        this.name = name;
        this.job = job;
        this.birth = birth;
        this.gender = gender;
    }

    public UserVO toEntity() {
        return UserVO.builder()
                .email(email)
                .password(password)
                .name(name)
                .job(job)
                .gender(gender)
                .build();
    }
}
