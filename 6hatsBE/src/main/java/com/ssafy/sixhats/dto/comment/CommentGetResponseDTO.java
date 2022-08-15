package com.ssafy.sixhats.dto.comment;
import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.CommentVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import java.time.LocalDate;
@Getter
@NoArgsConstructor
public class CommentGetResponseDTO {
    private Long userId;
    private String userName;
    private Long boardId;
    private Long commentId;
    private String comment_contents;
    private LocalDate commentCreatedAt;

    @Builder
    public CommentGetResponseDTO(CommentVO commentVO) {
        this.userId = commentVO.getUserVO().getUserId();
        this.boardId = commentVO.getBoardVO().getBoardId();
        this.commentId = commentVO.getCommentId();
        this.comment_contents = commentVO.getCommentContents();
        this.commentCreatedAt = commentVO.getCommentCreatedAt();
        this.userName = commentVO.getUserVO().getName();
    }
}