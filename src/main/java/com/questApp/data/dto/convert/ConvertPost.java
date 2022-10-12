package com.questApp.data.dto.convert;

import com.questApp.data.dto.request.PostCreate;
import com.questApp.data.dto.request.PostUpdate;
import com.questApp.data.entity.Post;
import com.questApp.data.entity.User;

import java.util.Date;
import java.util.Optional;

public class ConvertPost {

    public static Post dtoToEntity(PostCreate postCreate, User user){
        Post post=new Post();
        post.setId(postCreate.getId());
        post.setText(postCreate.getText());
        post.setTitle(postCreate.getTitle());
        post.setUser(user);
        post.setCreateDate(new Date());
        return  post;
    }

    public static Post dtoToEntity(PostUpdate postUpdate,Post post){
        post.setText(postUpdate.getText());
        post.setTitle(postUpdate.getTitle());
        return  post;
    }
}
