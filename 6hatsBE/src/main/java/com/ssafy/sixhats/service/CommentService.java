package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.BoardDAO;
import com.ssafy.sixhats.dao.CommentDAO;
import com.ssafy.sixhats.dao.UserDAO;

import com.ssafy.sixhats.dto.comment.CommentGetResponseDTO;
import com.ssafy.sixhats.dto.comment.CommentPatchRequestDTO;
import com.ssafy.sixhats.dto.comment.CommentPostRequestDTO;
import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.CommentVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.UserType;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.sixhats.exception.UnAuthorizedException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final BoardDAO boardDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;

    //댓글 쓰기
    @Transactional
    public void postComment(Long userId, CommentPostRequestDTO commentPostRequestDTO) {

        UserVO user = userDAO.findById(userId).orElse(null);
        BoardVO board = boardDAO.findById(commentPostRequestDTO.getBoardId()).orElse(null);

        if(user == null || !user.isActive()) {
            throw new NullPointerException("User Not Found");
        } else if(board == null) {
            throw new NullPointerException("Board Not Found");
        } else if( user.getUserType() == UserType.ADMIN || user.getUserId() == board.getUserId().getUserId()) {
            CommentVO commentVO = commentPostRequestDTO.toEntity(user, board);
            commentDAO.save(commentVO);
        } else {
            throw new UnAuthorizedException();
        }

    }

    //댓글 수정
    @Transactional
    public void patchComment(Long commentId, Long userId, CommentPatchRequestDTO commentPatchRequestDTO ) {
        CommentVO comment = commentDAO.findById(commentId).orElse(null);

        if( userId != comment.getUserVO().getUserId()) {
            throw new UnAuthorizedException();
        } else {
            comment.patchComment(commentPatchRequestDTO.getCommentContents());
        }
    }

    //게시글당 댓글 보기
    @Transactional(readOnly = true)
    public List<CommentGetResponseDTO> getCommentList(Long userId, Long boardId, Pageable pageable) {

        UserVO user = userDAO.findById(userId).orElse(null);
        BoardVO board = boardDAO.findById(boardId).orElse(null);

        if(user == null || !user.isActive()) {
            throw new NullPointerException("User Not Found");
        } else if(board == null) {
            throw new NullPointerException("Board Not Found");
        } else if( user.getUserType() != UserType.ADMIN && user.getUserId() != board.getUserId().getUserId()) {
            throw new UnAuthorizedException();
        }

        return commentDAO.findAllByBoardVO(board, pageable)
                .stream()
                .map(CommentGetResponseDTO::new)
                .collect(Collectors.toList());
    }


    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {

        CommentVO commentVO = commentDAO.findById(commentId).orElse(null);

        if( userId != commentVO.getUserVO().getUserId()) {
            throw new UnAuthorizedException();
        } else {
            commentDAO.delete(commentVO);
        }
    }

}
