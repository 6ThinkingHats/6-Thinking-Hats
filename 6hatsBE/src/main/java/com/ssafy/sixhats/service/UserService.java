package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dao.UserRoomDAO;
import com.ssafy.sixhats.dto.room.RoomGetResponseDTO;
import com.ssafy.sixhats.dto.user.UserGetResponseDTO;
import com.ssafy.sixhats.dto.user.UserLoginRequestDTO;
import com.ssafy.sixhats.dto.user.UserPostRequestDTO;
import com.ssafy.sixhats.dto.user.UserPutRequestDTO;
import com.ssafy.sixhats.util.SHA256;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.UserRoomVO;
import com.ssafy.sixhats.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final UserRoomDAO userRoomDAO;
    private final RoomDAO roomDAO;
    private final SHA256 sha256;

    public UserVO postUser(UserPostRequestDTO userPostRequestDTO) throws NoSuchAlgorithmException {
        String encryptPassword = sha256.encrypt(userPostRequestDTO.getPassword());
        userPostRequestDTO.updatePassword(encryptPassword);
        UserVO userVO = userPostRequestDTO.toEntity();
        userVO.updateBirth(userPostRequestDTO.getBirth());
        return userDAO.save(userVO);
    }
    public UserGetResponseDTO getUser(Long userId) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO().builder()
                    .email(userVO.getEmail())
                    .name(userVO.getName())
                    .job(userVO.getJob())
                    .birth(userVO.getBirth())
                    .gender(userVO.getGender())
                    .userType(userVO.getUserType())
                    .profileImageUrl(userVO.getProfileImageUrl())
                    .build();
            return userGetResponseDTO;
        } else {
            throw new NullPointerException("User Not Found");
        }
    }

    @Transactional
    public UserGetResponseDTO putUser(Long userId, UserPutRequestDTO userPutRequestDTO) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            // update를 바로 실행
            userVO.update(userPutRequestDTO);
            UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO().builder()
                    .email(userVO.getEmail())
                    .name(userVO.getName())
                    .job(userVO.getJob())
                    .birth(userVO.getBirth())
                    .gender(userVO.getGender())
                    .userType(userVO.getUserType())
                    .profileImageUrl(userVO.getProfileImageUrl())
                    .build();
            return userGetResponseDTO;
        } else {
            throw new NullPointerException("User Not Found");
        }
    }

    @Transactional
    public void patchUser(Long userId, String password) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            // update를 바로 실행
            userVO.updatePassword(password);
        } else {
            throw new NullPointerException("User Not Found");
        }
    }
    @Transactional
    public void deleteUser(Long userId) {
        UserVO userVO = userDAO.findById(userId).orElse(null);
        if(userVO != null && userVO.isActive()){
            // is active 부분 변경
            userVO.updateIsActive();
        } else {
            throw new NullPointerException("User Not Found");
        }
    }

    public UserVO loginGeneral(UserLoginRequestDTO userLoginRequestDTO) throws NoSuchAlgorithmException {
        String email = userLoginRequestDTO.getEmail();
        String password = sha256.encrypt(userLoginRequestDTO.getPassword());;
        UserVO userVO = userDAO.findByEmailAndPassword(email, password).orElse(null);
        if(userVO != null && userVO.isActive()){
            return userVO;
        } else {
            throw new NullPointerException("User Not Found");
        }
    }

    @Transactional
    public List<RoomGetResponseDTO> getUserRooms(Long userId, Pageable pageable) {

        UserVO userVO = userDAO.findById(userId).orElse(null);

        if(userVO == null || !userVO.isActive()) {
            throw new NullPointerException("User Not Found");
        }

        // 내가 참여한 방 목록
        List<UserRoomVO> userParticipateRooms = userRoomDAO.findAllByUserVOAndBanned(userVO, false, pageable);

        /*
        두개를 합쳐서 여기에다가 넣음
        가장 최근에 만들어진게 앞으로 나오도록 구현
         */
        List<RoomGetResponseDTO> userRoomList = new ArrayList<>();

        // 내가 참여한 방 목록 추가
        // 끝난 것만 넣어주기
        for(UserRoomVO userRoomVO: userParticipateRooms){
            RoomVO roomVO = userRoomVO.getRoomVO();

            RoomGetResponseDTO roomGetResponseDTO = new RoomGetResponseDTO()
                    .builder()
                    .userId(roomVO.getUserVO().getUserId())
                    .roomId(roomVO.getRoomId())
                    .roomStartTime(roomVO.getRoomStartTime())
                    .roomEndTime(roomVO.getRoomEndTime())
                    .opinionFileUrl(roomVO.getOpinionFileUrl())
                    .opinionFileValid(roomVO.isOpinionFileValid())
                    .isCreator(false)
                    .build();
            userRoomList.add(roomGetResponseDTO);
        }

        // 생성 날짜 순으로 sorting
        Collections.sort(userRoomList);

        return userRoomList;
    }
}
