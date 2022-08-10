package com.ssafy.sixhats.vo;

import com.ssafy.sixhats.dto.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.CommentPatchRequestDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Data
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

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String comment_contents;

    @Column(nullable = false, name = "comment_created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate commentCreatedAt = LocalDate.now();

    @Builder
    public CommentVO(UserVO userVO, BoardVO boardVO, Long commentId, String comment_contents) {
        this.userVO = userVO;
        this.boardVO = boardVO;
        this.commentId = commentId;
        this.comment_contents = comment_contents;
    }

    public void patchComment(CommentPatchRequestDTO commentPatchRequestDTO) {
        this.comment_contents = commentPatchRequestDTO.getComment_contents();

    }

}
