package com.ssafy.sixhats.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "comment")
public class CommentVO {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserVO userVO;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardVO boardVO;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //개별 엔티티별로 키값 증가
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_contents", nullable = false, columnDefinition = "LONGTEXT")
    private String commentContents;

    @Column(nullable = false, name = "comment_created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate commentCreatedAt = LocalDate.now();

    @Builder
    public CommentVO(UserVO userVO, BoardVO boardVO, Long commentId, String commentContents) {
        this.userVO = userVO;
        this.boardVO = boardVO;
        this.commentId = commentId;
        this.commentContents = commentContents;
    }

    public void patchComment(String commentContents) {
        this.commentContents = commentContents;

    }

}
