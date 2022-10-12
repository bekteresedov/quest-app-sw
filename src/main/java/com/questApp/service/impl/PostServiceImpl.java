package com.questApp.service.impl;

import com.questApp.data.dto.convert.ConvertPost;
import com.questApp.data.dto.request.PostCreate;
import com.questApp.data.dto.request.PostUpdate;
import com.questApp.data.dto.response.LikeResponse;
import com.questApp.data.dto.response.PostResponse;
import com.questApp.data.entity.Post;
import com.questApp.data.entity.User;
import com.questApp.data.repository.PostRepository;
import com.questApp.service.LikeService;
import com.questApp.service.PostService;
import com.questApp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;
    LikeService likeService;
    final UserService userService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public List<PostResponse> getAllPosts(Optional<Long> userId) {
     List<Post> posts;

      if(userId.isPresent()){
          posts=postRepository.findByUserId(userId.get());
      }else{
          posts=postRepository.findAll();
      }
        return posts.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p, likes);}).collect(Collectors.toList());
    }

    @Override
    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public PostResponse getOnePostByIdWithLikes(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(postId));
        return new PostResponse(post, likes);
    }

    @Override
    public Post createOnePost(PostCreate postCreate) {
        User user=userService.getOneUserById(postCreate.getUserId());
        if(user==null)
        return null;
       return postRepository.save(ConvertPost.dtoToEntity(postCreate,user));
    }

    @Override
    public Post updateOnePostById(Long postId, PostUpdate updatePost) {

        Optional<Post>post=postRepository.findById(postId);
        if(post.isPresent()){
          return postRepository.save(ConvertPost.dtoToEntity(updatePost,post.get()));
        }
        return null;
    }

    @Override
    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);

    }
}
