package com.ssafy.sixhats.dto;

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

    private UserVO userVO;
    private BoardVO boardVO;
    private String comment_contents;

    @Builder
    public CommentPostRequestDTO(UserVO userVO, BoardVO boardVO, String comment_contents) {
        this.userVO = userVO;
        this.boardVO = boardVO;
        this.comment_contents = comment_contents;

    }

    public CommentVO toEntity(UserVO userVO, BoardVO boardVO) {
        return CommentVO.builder()
                .userVO(userVO)
                .boardVO(boardVO)
                .comment_contents(comment_contents)
                .build();
    }


}
