package com.ssafy.sixhats.service;

import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final String secretKey = "ssafySecret";
    private final int EXPIRE_MINUTES = 60 * 24;

    public String getJwtToken(HttpServletRequest request) {
        return request.getHeader("accessToken");
    }
    /*
    Token을 생성하는 Method
    claim에 userid를 집어넣는다 -> long type을 집어넣을 수 없음
     */
    public String createToken(UserVO userVO) {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
                .claim("userId", userVO.getUserId())
                .signWith(SignatureAlgorithm.HS256, generateKey()).compact();
        return jwt;
    }


    /*
     토큰 유효성 검증
     1. claim을 획득할 수 있는지
     2. 유효 기간을 지났는지 확인
     */
    public boolean checkJwtToken(String jwt) {
        try {
            return getClaims(jwt).getExpiration().after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long getUserId(HttpServletRequest request){
        try {
            return Long.parseLong(String.valueOf(getClaims(getJwtToken(request)).get("userId")));
        } catch (Exception e){
            e.printStackTrace();
            throw new UnAuthorizedException();
        }
    }

    private Claims getClaims(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(jwt);
            return claims.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private byte[] generateKey() {
        try {
            return secretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
