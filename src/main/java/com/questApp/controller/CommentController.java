package com.questApp.controller;

import com.questApp.data.dto.request.CommentCreate;
import com.questApp.data.dto.request.CommentUpdate;
import com.questApp.data.dto.response.CommentResponse;
import com.questApp.data.entity.Comment;
import com.questApp.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    final CommentService commentService;


    @GetMapping
    public List<CommentResponse>getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId,postId);
    }

    @PostMapping("/new")
    public Comment createOneComment(@RequestBody CommentCreate commentCreate){
        return commentService.createOneComment(commentCreate);
    }

    @GetMapping("/get/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return  commentService.getOneCommentById(commentId);
    }

    @PutMapping("/update/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdate commentUpdate){
        return commentService.updateOneCommentById(commentId, commentUpdate);
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneCommentById(commentId);
    }
}
