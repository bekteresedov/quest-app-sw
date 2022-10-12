package com.questApp.service;

import com.questApp.data.dto.request.CommentCreate;
import com.questApp.data.dto.request.CommentUpdate;
import com.questApp.data.dto.response.CommentResponse;
import com.questApp.data.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId);

    Comment getOneCommentById(Long commentId);

    Comment createOneComment(CommentCreate commentCreate);

    Comment updateOneCommentById(Long commentId, CommentUpdate commentUpdate);

    void deleteOneCommentById(Long commentId);
}
