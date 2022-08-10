package com.ssafy.sixhats.controller;


import com.ssafy.sixhats.dto.CommentGetResponseDTO;
import com.ssafy.sixhats.dto.CommentPatchRequestDTO;
import com.ssafy.sixhats.dto.CommentPostRequestDTO;

import com.ssafy.sixhats.service.CommentService;
import com.ssafy.sixhats.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    JwtService jwtService;


    @PostMapping("")
    public ResponseEntity postComment(@RequestBody CommentPostRequestDTO commentPostRequestDTO, HttpServletRequest request) {

        commentService.postComment(commentPostRequestDTO);

        return new ResponseEntity("comment create success", HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{comment_id}")
    public ResponseEntity patchComment(@PathVariable("comment_id") Long commentId, @RequestBody CommentPatchRequestDTO commentPatchRequestDTO, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        commentService.patchComment(commentId, userId, commentPatchRequestDTO);

        return new ResponseEntity("comment update success", HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "")
    public ResponseEntity<List<CommentGetResponseDTO>> getAllBoardComment(Long boardId) {

        List<CommentGetResponseDTO> commentList = commentService.getAllBoardComment(boardId);

        return new ResponseEntity(commentList, HttpStatus.ACCEPTED);

    }

    @DeleteMapping(value = "/{comment_id}")
    public ResponseEntity deleteComment(@PathVariable("comment_id") Long commentId, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);

        commentService.delete(commentId, userId);

        return new ResponseEntity("comment delete success", HttpStatus.NO_CONTENT);
    }
}
