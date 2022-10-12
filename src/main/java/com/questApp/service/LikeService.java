package com.questApp.service;


import com.questApp.data.dto.request.LikeCreate;
import com.questApp.data.dto.response.LikeResponse;
import com.questApp.data.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId);

    Like getOneLikeById(Long likeId);

    Like createOneLike(LikeCreate request);

    void deleteOneLikeById(Long likeId);
}
