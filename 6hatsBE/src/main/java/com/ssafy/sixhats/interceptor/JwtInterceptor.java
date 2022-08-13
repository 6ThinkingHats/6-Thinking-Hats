package com.ssafy.sixhats.interceptor;

import com.ssafy.sixhats.exception.UnAuthorizedException;
import com.ssafy.sixhats.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String token = jwtService.getJwtToken(request);;
        if(jwtService.checkJwtToken(token)){
            return true;
        } else {
            throw new UnAuthorizedException();
        }
    }
}
