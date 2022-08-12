package com.ssafy.sixhats.dto.comment;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.CommentVO;
import com.ssafy.sixhats.vo.UserVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.User;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class CommentPostRequestDTO {

    private Long boardId;
    private String commentContents;

    @Builder
    public CommentPostRequestDTO(Long boardId, String commentContents) {
        this.boardId = boardId;
        this.commentContents = commentContents;

    }

    public CommentVO toEntity(UserVO userVO, BoardVO boardVO) {
        return CommentVO.builder()
                .userVO(userVO)
                .boardVO(boardVO)
                .commentContents(commentContents)
                .build();
    }


}
