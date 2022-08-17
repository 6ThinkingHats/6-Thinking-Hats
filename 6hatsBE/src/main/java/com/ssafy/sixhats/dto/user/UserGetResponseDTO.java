package com.ssafy.sixhats.dto.user;

import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import com.ssafy.sixhats.vo.type.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserGetResponseDTO {
    private String email;
    private String name;
    private Job job;
    private LocalDate birth;
    private Gender gender;
    private String profileImageUrl;
    private UserType userType;

    @Builder
    public UserGetResponseDTO(String email,  String name, Job job, LocalDate birth, Gender gender, UserType userType, String profileImageUrl){
        this.email = email;
        this.name = name;
        this.job = job;
        this.birth = birth;
        this.gender = gender;
        this.userType = userType;
        this.profileImageUrl = profileImageUrl;
    }


}
