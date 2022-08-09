package com.ssafy.sixhats.service;

import java.util.ArrayList;
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



    //공지사항 전체
    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findNoticeAll() {

        //System.out.println(boardDAO.findAll().stream().map(BoardResponseDTO::new).collect(Collectors.toList()));
        //System.out.println(board);

        return boardDAO.findByBoardType(BoardType.ntc)
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    //qna 전체
    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findQnaAll() {

        //System.out.println(boardDAO.findAll().stream().map(BoardResponseDTO::new).collect(Collectors.toList()));
        //System.out.println(board);

        return boardDAO.findByBoardType(BoardType.qna)
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    //게시글 하나
    @Transactional(readOnly = true)
    public BoardResponseDTO findById(int boardId) {

        BoardVO board = boardDAO.findById(boardId).orElse(null);
       // System.out.println(board);
        if(board == null) {
            throw new NullPointerException("board Not Found");
        }
        return new BoardResponseDTO(board);
    }

    //게시글 작성
    /*
    유저아이디가 null -> nullpointerexception()
    유저아이디가 !null -> usertype "ADMIN" -> boardtype "ntc"
                     -> usertype "USER"  -> boardtype "qna"
     */
    @Transactional
    public BoardVO post(BoardPostRequestDTO boardPostRequestDTO) {

        UserVO userId = userDAO.findById(boardPostRequestDTO.getUserId()).orElse(null);

        if( userId != null){
            UserType userType = userId.getUserType();
            if(userType == UserType.ADMIN){
                boardPostRequestDTO.update(BoardType.ntc);
            } else {
                boardPostRequestDTO.update(BoardType.qna);
            }
        } else {
            throw new NullPointerException("User Not Found");
        }

        return boardDAO.save(boardPostRequestDTO.toEntity(userId));
    }

    //게시글 수정
    /*
    유저아이디가 null -> nullpointerexception
    유저아이디가 !null -> 작성자 아이디 == 유저 아이디 -> 수정 가능
                     -> 작성자 아이디 != 유저 아이디 -> UnAuthorizedException()
     */
    @Transactional
    public void patch(Integer boardId, Long userId, BoardPatchRequestDTO boardPatchRequestDTO) {

        BoardVO board = boardDAO.findById(boardId).orElse(null);

        if( userId != board.getUserId().getUserId()) {
            throw new UnAuthorizedException();
        } else {
            board.patch(boardPatchRequestDTO);
        }

    }

    //게시글 삭제
    /*
    유저아이디가 null -> nullpointerexception
    유저아이디가 !null -> 작성자 아이디 == 유저 아이디  -> 삭제 가능
                     -> 작성자 아이디 != 유저 아이디 -> UnAuthorizedException()
     */
    @Transactional
    public void delete(int boardId, Long userId) {

        BoardVO board = boardDAO.findById(boardId).orElse(null);

        if( userId != board.getUserId().getUserId()) {
            throw new UnAuthorizedException();
        } else {
            boardDAO.delete(board);
        }
    }
}
