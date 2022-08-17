package com.ssafy.sixhats.vo;

import com.ssafy.sixhats.dto.user.UserPutRequestDTO;
import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import com.ssafy.sixhats.vo.type.LoginType;
import com.ssafy.sixhats.vo.type.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user")
public class UserVO {
    // 원활한 테스트를 위해서 우선 userid,email,password,name으로만 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //개별 엔티티별로 키값 증가
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Job job;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // Nullable 기준선 위에 필드들은 Builder로 안전하게 받을 수 있으어햠
    // 아래에는 전부 default가 잇으므로 설정하지 않아도 됌 후에 setter를 만들어서 처리
    @Column(nullable = false, name="profile_image_url", length = 100)
    @ColumnDefault("'basic'")
    private String profileImageUrl = "basic";

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private boolean isActive = true;

    @Column(name = "login_type", nullable = false, length = 10)
    @ColumnDefault("'GENERAL'")
    @Enumerated(EnumType.STRING)
    private LoginType loginType = LoginType.GENERAL;

    @Column(name = "user_type", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private UserType userType = UserType.USER;

    @Builder
    public UserVO(String email, String password, String name, Job job, Gender gender, String profileImageUrl){
        this.email = email;
        this.password = password;
        this.name = name;
        this.job = job;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
    }

    public void update(UserPutRequestDTO userPutRequestDTO) {
        this.email = userPutRequestDTO.getEmail();
        this.name = userPutRequestDTO.getName();
        this.job = userPutRequestDTO.getJob();
        this.gender = userPutRequestDTO.getGender();
        this.profileImageUrl = userPutRequestDTO.getProfileImageUrl();
    }

    public void updateUserType(UserType type) {
        this.userType = type;
    }
    public void updateBirth(LocalDate date) {this.birth = date;}

    public void updateLoginType(LoginType type) {this.loginType = type;}
    public void updatePassword(String password){
        this.password = password;
    }
    public void updateIsActive() {
        this.isActive = false;
    }

    public void updateProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }
}
