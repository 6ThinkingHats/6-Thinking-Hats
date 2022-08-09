package com.ssafy.sixhats.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.dao.BoardDAO;
import com.ssafy.sixhats.dto.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.BoardResponseDTO;

import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import com.ssafy.sixhats.vo.type.UserType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardDAO boardDAO;
    private final UserDAO userDAO;

    //게시글 전체
    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findAll() {

        return boardDAO.findAll()
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    //게시글 하나
    @Transactional(readOnly = true)
    public BoardResponseDTO findById(int boardId) {

        BoardVO board = boardDAO.findById(boardId)
                .orElseThrow(() -> new IllegalAccessError("[boardId=" + boardId + "] 해당 게시글이 존재하지 않습니다."));

        return new BoardResponseDTO(board);
    }

    //게시글 작성
    /*
    유저아이디가 null -> nullpointerexception()
    유저아이디가 !null -> usertype "ADMIN" -> boardtype "ntc"
                     -> usertype "USER"  -> boardtype "qna"
     */
    @Transactional
    public Integer post(BoardPostRequestDTO boardPostRequestDTO) {

        UserVO userId = userDAO.findById(boardPostRequestDTO.getUserId()).orElse(null);
        if(userId != null){
            UserType userType = userId.getUserType();
            if(userType == UserType.ADMIN){
                boardPostRequestDTO.update(BoardType.ntc);
            } else {
                boardPostRequestDTO.update(BoardType.qna);
            }
        } else {
            throw new NullPointerException("User Not Found");
        }

        return boardDAO.save(boardPostRequestDTO.toEntity(userId))
                .getBoardId();
    }

    //게시글 수정
    /*
    유저아이디가 null -> nullpointerexception
    유저아이디가 !null -> 작성자 아이디 == 유저 아이디 -> 수정 가능
                     -> 작성자 아이디 != 유저 아이디 -> UnAuthorizedException()
     */
    @Transactional
    public Integer patch(Integer boardId,BoardPatchRequestDTO boardPatchRequestDTO) {

        BoardVO board = boardDAO.findById(boardId)
                .orElseThrow(() -> new IllegalAccessError("[boardId=" + boardId + "] 해당 게시글이 존재하지 않습니다."));

        UserVO userId = userDAO.findById(boardPatchRequestDTO.getUserId()).orElse(null);
        if(userId != null){
            if (!board.checkwriterOfBoard(userId)) {
                throw new UnAuthorizedException();

            } else {
                board.patch(boardPatchRequestDTO.getTitle(), boardPatchRequestDTO.getBoard_contents());
            }

        } else {
            throw new NullPointerException("User Not Found");
        }

        return boardId;
    }

    //게시글 삭제
    /*
    유저아이디가 null -> nullpointerexception
    유저아이디가 !null -> 작성자 아이디 == 유저 아이디  -> 삭제 가능
                     -> 작성자 아이디 != 유저 아이디 -> UnAuthorizedException()
     */
    @Transactional
    public void delete(int boardId, UserVO userId) {

        BoardVO board = boardDAO.findById(boardId)
                .orElseThrow(() -> new IllegalAccessError("[boardId=" + boardId + "] 해당 게시글이 존재하지 않습니다."));

        if (!board.checkwriterOfBoard(userId)) {
            boardDAO.delete(board);

        } else {
            throw new UnAuthorizedException();
        }

    }
}
