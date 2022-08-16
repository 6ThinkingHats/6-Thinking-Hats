package com.ssafy.sixhats.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dto.board.BoardListResponseDTO;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.vo.BoardVO;
import com.ssafy.sixhats.dao.BoardDAO;
import com.ssafy.sixhats.dto.board.BoardPatchRequestDTO;
import com.ssafy.sixhats.dto.board.BoardPostRequestDTO;
import com.ssafy.sixhats.dto.board.BoardResponseDTO;

import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.BoardType;
import com.ssafy.sixhats.vo.type.UserType;
import org.springframework.data.domain.Pageable;
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
    public List<BoardListResponseDTO> getBoardNoticeList(Pageable pageable) {

        return boardDAO.findAllByBoardType(BoardType.ntc, pageable)
                .stream()
                .map(BoardListResponseDTO::new)
                .collect(Collectors.toList());
    }

    //qna 전체
    @Transactional(readOnly = true)
    public List<BoardListResponseDTO> getBoardQnaList(Pageable pageable) {

        return boardDAO.findAllByBoardType(BoardType.qna, pageable)
                .stream()
                .map(BoardListResponseDTO::new)
                .collect(Collectors.toList());
    }

    //게시글 하나
    @Transactional(readOnly = true)
    public BoardResponseDTO getBoard(Long boardId, Long userId) {

        BoardVO board = boardDAO.findById(boardId).orElse(null);
        if(board == null) {
            throw new NullPointerException("board Not Found");
        } else if (board.getBoardType() == BoardType.qna){
            
            // user가 admin인지 확인하기 위해서 받아 와야함
            UserVO user = userDAO.findById(userId).orElse(null);
            
            if(user == null) {
                throw new NullPointerException("user Not Found");
            } else  if( user.getUserType() != UserType.ADMIN && userId != board.getUserId().getUserId()){
                throw new UnAuthorizedException();
            }
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
    public void postBoard(Long userId, BoardPostRequestDTO boardPostRequestDTO) {

        UserVO user = userDAO.findById( userId ).orElse(null);

        if( user != null){
            UserType userType = user.getUserType();
            if(userType == UserType.ADMIN){
                boardPostRequestDTO.update(BoardType.ntc);
            } else {
                boardPostRequestDTO.update(BoardType.qna);
            }
        } else {
            throw new NullPointerException("User Not Found");
        }

        boardDAO.save(boardPostRequestDTO.toEntity(user));
    }

    //게시글 수정
    /*
    유저아이디가 null -> nullpointerexception
    유저아이디가 !null -> 작성자 아이디 == 유저 아이디 -> 수정 가능
                     -> 작성자 아이디 != 유저 아이디 -> UnAuthorizedException()
     */
    @Transactional
    public void patchBoard(Long boardId, Long userId, BoardPatchRequestDTO boardPatchRequestDTO) {

        BoardVO board = boardDAO.findById(boardId).orElse(null);
        if(board == null ){
            throw new NullPointerException("Board Not Found");
        } else if( userId != board.getUserId().getUserId()) {
            throw new UnAuthorizedException();
        }

        board.patch(boardPatchRequestDTO);

    }

    //게시글 삭제
    /*
    유저아이디가 null -> nullpointerexception
    유저아이디가 !null -> 작성자 아이디 == 유저 아이디  -> 삭제 가능
                     -> 작성자 아이디 != 유저 아이디 -> UnAuthorizedException()
     */
    @Transactional
    public void delete(Long boardId, Long userId) {

        BoardVO board = boardDAO.findById(boardId).orElse(null);

        if(board == null){
            throw  new NullPointerException("Board Not Found");
        } else if( userId != board.getUserId().getUserId()) {
            throw new UnAuthorizedException();
        }

        boardDAO.delete(board);

    }

    public BoardResponseDTO getBoardNotice(Long boardId) {
        BoardVO board = boardDAO.findById(boardId).orElse(null);
        if(board == null) {
            throw new NullPointerException("board Not Found");
        }

        return new BoardResponseDTO(board);
    }
}
