package com.ssafy.sixhats.aop;

import com.ssafy.sixhats.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity handleException(UnAuthorizedException e) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", e.getMessage());
        HttpStatus status = HttpStatus.FORBIDDEN;

        return new ResponseEntity(resultMap, status);
    }

    @ExceptionHandler(NullPointerException.class)
    public  ResponseEntity handleException(NullPointerException e) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", e.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity(resultMap, status);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public  ResponseEntity handleException(SQLIntegrityConstraintViolationException e) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "Duplicated Key");
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity(resultMap, status);
    }
}
