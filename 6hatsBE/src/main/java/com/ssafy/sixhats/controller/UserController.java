package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.UserGetResponseDTO;
import com.ssafy.sixhats.dto.UserPostRequestDTO;
import com.ssafy.sixhats.dto.UserLoginRequestDTO;
import com.ssafy.sixhats.dto.UserPutRequestDTO;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.UserService;
import com.ssafy.sixhats.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    // User Create (POST)
    @PostMapping("")
    public ResponseEntity<String> postUser(@RequestBody UserPostRequestDTO userPostRequestDTO){
        userService.postUser(userPostRequestDTO);

        // email 확인 로직과 관련해서 고민중
        return new ResponseEntity("signin success", HttpStatus.CREATED);
    }

    // User Read (READ)
    @GetMapping("{userId}")
    public ResponseEntity getUser(@PathVariable Long userId, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;

        // 유저가 다른 유저의 정보를 요청했을 때
        if(jwtService.getUserId(request) != userId){
            throw new UnAuthorizedException();
        }

        UserGetResponseDTO userGetResponseDTO = userService.getUser(userId);

        resultMap.put("messge", "get user info success");
        resultMap.put("user", userGetResponseDTO);

        return new ResponseEntity(resultMap, status);
    }

    @PutMapping("{userId}")
    public ResponseEntity putUser(@PathVariable Long userId, @RequestBody UserPutRequestDTO userPutRequestDTO, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        // 유저가 다른 유저의 정보를 요청했을 때
        if(jwtService.getUserId(request) != userId){
            throw new UnAuthorizedException();
        }

        UserGetResponseDTO userGetResponseDTO = userService.putUser(userId, userPutRequestDTO);
        resultMap.put("messge", "update user info success");
        resultMap.put("user", userGetResponseDTO);

        return new ResponseEntity(resultMap, status);
    }

    @DeleteMapping ("{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.NO_CONTENT;

        // 유저가 다른 유저의 정보를 요청했을 때
        if(jwtService.getUserId(request) != userId){
            throw new UnAuthorizedException();
        }

        userService.deleteUser(userId);
        return new ResponseEntity(resultMap, status);
    }

    // User Login General
    @PostMapping("login")
    public ResponseEntity loginGeneral(@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        UserVO userVO = userService.loginGeneral(userLoginRequestDTO);
        String token = jwtService.createToken(userVO);
        resultMap.put("access-token", token);
        resultMap.put("message", "Login Success");

        return new ResponseEntity(resultMap, status);
    }

}
