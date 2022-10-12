package com.questApp.data.dto.convert;

import com.questApp.data.dto.request.LikeCreate;
import com.questApp.data.entity.Like;
import com.questApp.data.entity.Post;
import com.questApp.data.entity.User;

public class ConvertLike {

    public  static Like dtoToEntity(LikeCreate likeCreate, User user, Post post){

        Like like=new Like();
        like.setId(likeCreate.getId());
        like.setUser(user);
        like.setPost(post);
        return like;
    }
}
