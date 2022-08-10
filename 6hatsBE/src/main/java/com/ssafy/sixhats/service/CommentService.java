package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.BoardDAO;
import com.ssafy.sixhats.dao.CommentDAO;
import com.ssafy.sixhats.dao.UserDAO;

import com.ssafy.sixhats.dto.*;

import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.vo.CommentVO;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import com.ssafy.sixhats.vo.type.UserType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.sixhats.exception.UnAuthorizedException;

import java.util.ArrayList;
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
    public CommentVO postComment(CommentPostRequestDTO commentPostRequestDTO) {

        UserVO userId = userDAO.findById(commentPostRequestDTO.getUserVO().getUserId()).orElse(null);
        UserType userType = userId.getUserType();

        BoardVO boardId = boardDAO.findById(commentPostRequestDTO.getBoardVO().getBoardId()).orElse(null);

        if( userType == UserType.ADMIN) {
            CommentVO commentVO = commentPostRequestDTO.toEntity(userId, boardId);
            return commentDAO.save(commentVO);
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
            comment.patchComment(commentPatchRequestDTO);
        }
    }

    /*//댓글 전체 보기
    @Transactional(readOnly = true)
    public List<CommentGetResponseDTO> getAllComment() {

        return commentDAO.findAll()
                .stream()
                .map(CommentGetResponseDTO::new)
                .collect(Collectors.toList());
    }

     */

    //게시글당 댓글 보기
    @Transactional(readOnly = true)
    public List<CommentGetResponseDTO> getAllBoardComment(Long boardId) {

        BoardVO boardVO = boardDAO.findById(boardId).orElse(null);


        return commentDAO.findAllByBoardVO(boardVO)
                .stream()
                .map(CommentGetResponseDTO::new)
                .collect(Collectors.toList());
    }


    //댓글 삭제
    @Transactional
    public void delete(Long commentId, Long userId) {

        CommentVO commentVO = commentDAO.findById(commentId).orElse(null);

        if( userId != commentVO.getUserVO().getUserId()) {
            throw new UnAuthorizedException();
        } else {
            commentDAO.delete(commentVO);
        }
    }

}
