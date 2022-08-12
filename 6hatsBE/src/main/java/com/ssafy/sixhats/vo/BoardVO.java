package com.ssafy.sixhats.vo;

import com.ssafy.sixhats.dto.board.BoardPatchRequestDTO;
import com.ssafy.sixhats.vo.type.BoardType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
/*
롬복 사용시 setter 사용에 주의해야하는데
어느 column을 setter로 사용해야 할 지 모르겠어서 일단 전체 설정함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
이 설정으로 setter 접근 막을 수 있음
 */
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "board")
public class BoardVO {

    /*User 와 Board 관계 ManyToOne
    fk user_id 로 join
    jpa 책 pg170
    */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserVO userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //개별 엔티티별로 키값 증가
    @Column(name = "board_id")
    private Long boardId;


    @Column(nullable = false, length = 100)
    private String title;

    @Column(name="board_contents", columnDefinition = "LONGTEXT")
    private String boardContents;

    @Column(nullable = false, name = "board_created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate boardCreatedAt = LocalDate.now();

    /*
    board_type : qna, ntc(notice)
     */
    @Column(name = "board_type", length = 3)
    @ColumnDefault("'qna'")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ColumnDefault("0") // default 0
    @Column(nullable = false)
    private int views;


    @Builder
    public BoardVO(UserVO userId, String title, String boardContents, BoardType boardType) {
        this.userId = userId;
        this.title = title;
        this.boardContents = boardContents;
        this.boardType = boardType;
    }

    public void patch(BoardPatchRequestDTO boardPatchRequestDTO) {
        this.title = boardPatchRequestDTO.getTitle();
        this.boardContents = boardPatchRequestDTO.getBoardContents();
    }


}
