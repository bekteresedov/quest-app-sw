package com.questApp.service.impl;

import com.questApp.data.dto.convert.ConvertLike;
import com.questApp.data.dto.request.LikeCreate;
import com.questApp.data.dto.response.LikeResponse;
import com.questApp.data.entity.Like;
import com.questApp.data.entity.Post;
import com.questApp.data.entity.User;
import com.questApp.data.repository.LikeRepository;
import com.questApp.service.LikeService;
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
public class LikeServiceImpl implements LikeService {

    final LikeRepository likeRepository;
    final UserService userService;
    final PostService postService;
    @Override
    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like>likes;

        if(userId.isPresent()&& postId.isPresent()){
            likes=likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            likes=likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            likes=likeRepository.findByPostId(postId.get());
        }else {
            likes=likeRepository.findAll();
        }
        return likes.stream().map(l -> new LikeResponse(l)).collect(Collectors.toList());
    }

    @Override
    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    @Override
    public Like createOneLike(LikeCreate request) {

        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());
        if(user!=null&&post!=null){
            return likeRepository.save(ConvertLike.dtoToEntity(request,user,post));
        }
        return null;
    }

    @Override
    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);

    }
}
