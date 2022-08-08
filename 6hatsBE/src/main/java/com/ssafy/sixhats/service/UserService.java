package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.dto.*;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public UserVO postUser(UserPostRequestDTO userPostRequestDTO) {
        return userDAO.save(userPostRequestDTO.toEntity());
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
