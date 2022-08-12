package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dto.user.UserGetResponseDTO;
import com.ssafy.sixhats.dto.user.UserLoginRequestDTO;
import com.ssafy.sixhats.dto.user.UserPostRequestDTO;
import com.ssafy.sixhats.dto.user.UserPutRequestDTO;
import com.ssafy.sixhats.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public UserVO postUser(UserPostRequestDTO userPostRequestDTO) {
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

    public UserVO loginGeneral(UserLoginRequestDTO userLoginRequestDTO){
        String email = userLoginRequestDTO.getEmail();
        String password = userLoginRequestDTO.getPassword();
        UserVO userVO = userDAO.findByEmailAndPassword(email, password).orElse(null);
        if(userVO != null && userVO.isActive()){
            return userVO;
        } else {
            throw new NullPointerException("User Not Found");
        }
    }

}
