package com.questApp.service.impl;

import com.questApp.data.dto.convert.ConvertComment;
import com.questApp.data.dto.request.CommentCreate;
import com.questApp.data.dto.request.CommentUpdate;
import com.questApp.data.dto.response.CommentResponse;
import com.questApp.data.entity.Comment;
import com.questApp.data.entity.Post;
import com.questApp.data.entity.User;
import com.questApp.data.repository.CommentRepository;
import com.questApp.service.CommentService;
import com.questApp.service.PostService;
import com.questApp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;
    final UserService userService;
    final PostService postService;


    @Override
    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {

        List<Comment>comments;
      if(userId.isPresent()&&postId.isPresent()){
          comments=commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
      } else if (userId.isPresent()) {
          comments=commentRepository.findByUserId(userId.get());

      } else if (postId.isPresent()) {
          comments=commentRepository.findByPostId(postId.get());
      }else {
          comments=commentRepository.findAll();
      }
        return comments.stream().map(c->new CommentResponse(c)).collect(Collectors.toList());
    }

    @Override
    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public Comment createOneComment(CommentCreate commentCreate) {

        User user=userService.getOneUserById(commentCreate.getUserId());

        Post post=postService.getOnePostById(commentCreate.getPostId());

        if(post!=null&&user!=null){
            return commentRepository.save(ConvertComment.dtDtoEntity(commentCreate, user, post));
        }
        return null;
    }

    @Override
    public Comment updateOneCommentById(Long commentId, CommentUpdate commentUpdate) {
        Optional<Comment>comment=commentRepository.findById(commentId);
        if(comment.isPresent()){
            commentRepository.save(ConvertComment.dtDtoEntity(commentUpdate,comment.get()));
        }
        return null;
    }

    @Override
    public void deleteOneCommentById(Long commentId) {

        commentRepository.deleteById(commentId);

    }
}
