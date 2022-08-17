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
public class UserPutRequestDTO {
    private String email;
    private String name;
    private Job job;
    private LocalDate birth;
    private Gender gender;

    private String profileImageUrl;

    @Builder
    public UserPutRequestDTO(String email, String name, Job job, LocalDate birth, Gender gender, String profileImageUrl){
        this.email = email;
        this.name = name;
        this.job = job;
        this.birth = birth;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
    }
}
