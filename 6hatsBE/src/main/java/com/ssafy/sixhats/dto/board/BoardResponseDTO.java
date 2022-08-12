package com.ssafy.sixhats.dto.board;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class BoardResponseDTO {

    private String name;
    private Long boardId;
    private String title;
    private String boardContents;
    private BoardType boardType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate boardCreatedAt;
    private int views;

    @Builder
    public BoardResponseDTO(BoardVO board) {
        this.name = board.getUserId().getName();
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.boardContents = board.getBoardContents();
        this.boardType = board.getBoardType();
        this.views = board.getViews();
        this.boardCreatedAt = board.getBoardCreatedAt();
    }
}