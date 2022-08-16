package com.ssafy.sixhats.controller;


import com.ssafy.sixhats.dto.comment.CommentGetResponseDTO;
import com.ssafy.sixhats.dto.comment.CommentPatchRequestDTO;
import com.ssafy.sixhats.dto.comment.CommentPostRequestDTO;

import com.ssafy.sixhats.service.CommentService;
import com.ssafy.sixhats.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final JwtService jwtService;


    @PostMapping("")
    public ResponseEntity postComment(@RequestBody CommentPostRequestDTO commentPostRequestDTO, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);
        commentService.postComment(userId, commentPostRequestDTO);

        return new ResponseEntity("comment create success", HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{comment_id}")
    public ResponseEntity patchComment(@PathVariable("comment_id") Long commentId, @RequestBody CommentPatchRequestDTO commentPatchRequestDTO, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        commentService.patchComment(commentId, userId, commentPatchRequestDTO);

        return new ResponseEntity("comment update success", HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "")
    public ResponseEntity<List<CommentGetResponseDTO>> getCommentList(Long boardId, Pageable pageable, HttpServletRequest request) {

        System.out.println(pageable);
        Long userId = jwtService.getUserId(request);
        List<CommentGetResponseDTO> commentList = commentService.getCommentList(userId, boardId, pageable);

        return new ResponseEntity(commentList, HttpStatus.ACCEPTED);

    }

    @DeleteMapping(value = "/{comment_id}")
    public ResponseEntity deleteComment(@PathVariable("comment_id") Long commentId, HttpServletRequest request) {

        Long userId = jwtService.getUserId(request);
        commentService.deleteComment(commentId, userId);

        return new ResponseEntity("comment delete success", HttpStatus.NO_CONTENT);
    }
}
