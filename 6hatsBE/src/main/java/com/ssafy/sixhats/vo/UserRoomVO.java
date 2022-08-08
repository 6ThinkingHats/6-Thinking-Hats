package com.ssafy.sixhats.vo;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Table(name = "user_room")
public class UserRoomVO {

    @Id
    @Column(name = "user_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoomId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomVO roomVO;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserVO userVO;

    @Column(name = "banned")
    @ColumnDefault("false")
    private boolean banned;

    @Builder
    public UserRoomVO(UserVO userVO, RoomVO roomVO) {
        this.userVO = userVO;
        this.roomVO = roomVO;
    }


}
