package com.questApp.service;

import com.questApp.data.dto.request.PostCreate;
import com.questApp.data.dto.request.PostUpdate;
import com.questApp.data.dto.response.PostResponse;
import com.questApp.data.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostResponse> getAllPosts(Optional<Long> userId);

    Post getOnePostById(Long postId);

    PostResponse getOnePostByIdWithLikes(Long postId);

    Post createOnePost(PostCreate postCreate);

    Post updateOnePostById(Long postId, PostUpdate updatePost);

    void deleteOnePostById(Long postId);
}
