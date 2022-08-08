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
public class UserPutRequestDTO {
    private String email;
    private String name;
    private Job job;
    private LocalDate birth;
    private Gender gender;

    @Builder
    public UserPutRequestDTO(String email, String name, Job job, LocalDate birth, Gender gender){
        this.email = email;
        this.name = name;
        this.job = job;
        this.birth = birth;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserCreateRequestDTO{" +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", job=" + job +
                ", birth=" + birth +
                ", gender=" + gender +
                '}';
    }
}
