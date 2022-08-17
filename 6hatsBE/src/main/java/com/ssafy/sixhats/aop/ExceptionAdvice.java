package com.ssafy.sixhats.aop;

import com.ssafy.sixhats.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    @ExceptionHandler(IOException.class)
    public ResponseEntity handleException(IOException e){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "ioexception fail");
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity(resultMap, status);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public  ResponseEntity handleException(FileNotFoundException e) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "File Not Found");
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity(resultMap, status);
    }
}
