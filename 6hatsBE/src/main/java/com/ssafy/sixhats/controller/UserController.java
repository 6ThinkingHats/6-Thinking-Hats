package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.room.RoomGetResponseDTO;
import com.ssafy.sixhats.dto.user.*;
import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.service.JwtService;
import com.ssafy.sixhats.service.OAuthService;
import com.ssafy.sixhats.service.UserService;
import com.ssafy.sixhats.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final OAuthService oAuthService;

    // User Create (POST)
    @PostMapping("")
    public ResponseEntity postUser(@RequestBody UserPostRequestDTO userPostRequestDTO) throws NoSuchAlgorithmException {

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

    @GetMapping("{userId}/rooms")
    public ResponseEntity getUserRooms(@PathVariable Long userId, Pageable pageable, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        // 유저가 다른 유저의 정보를 요청했을 때
        if(jwtService.getUserId(request) != userId){
            throw new UnAuthorizedException();
        }

        List<RoomGetResponseDTO> rooms = userService.getUserRooms(userId, pageable);

        resultMap.put("messge", "get user rooms info success");
        resultMap.put("rooms", rooms);

        return new ResponseEntity(resultMap, status);
    }

    // User Update (PUT)
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

    // User Delete (DELETE)
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

    // User password Update (PATCH)
    @PatchMapping("{userId}")
    public ResponseEntity patchUser(@PathVariable Long userId, @RequestBody UserPatchRequestDTO userPatchRequestDTO, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        // 유저가 다른 유저의 정보를 요청했을 때
        if(jwtService.getUserId(request) != userId){
            throw new UnAuthorizedException();
        }

        userService.patchUser(userId, userPatchRequestDTO.getPassword());
        resultMap.put("messge", "update password success");

        return new ResponseEntity(resultMap, status);
    }

    // User Login General
    @PostMapping("login")
    public ResponseEntity loginGeneral(@RequestBody UserLoginRequestDTO userLoginRequestDTO) throws NoSuchAlgorithmException {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        UserVO userVO = userService.loginGeneral(userLoginRequestDTO);
        String token = jwtService.createToken(userVO);
        resultMap.put("access-token", token);
        resultMap.put("message", "Login Success");

        return new ResponseEntity(resultMap, status);
    }

    @PostMapping("login/kakao")
    public ResponseEntity loginKakao(@RequestBody String code) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        Map<String, String> tokenMap = oAuthService.getKaKaoAccessToken(code);
        UserVO userVO = oAuthService.createKakaoUser(tokenMap.get("access-token"));
        String token = jwtService.createToken(userVO);
        resultMap.put("access-token", token);
        resultMap.put("message", "Kakao Login Success");
        return new ResponseEntity(resultMap, status);
    }
}
