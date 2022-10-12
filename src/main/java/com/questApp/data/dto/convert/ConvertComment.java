package com.questApp.data.dto.convert;

import com.questApp.data.dto.request.CommentCreate;
import com.questApp.data.dto.request.CommentUpdate;
import com.questApp.data.entity.Comment;
import com.questApp.data.entity.Post;
import com.questApp.data.entity.User;

import java.util.Date;

public class ConvertComment {

    public static Comment dtDtoEntity(CommentCreate commentCreate, User user, Post post){

        Comment comment=new Comment();
        comment.setId(commentCreate.getId());
        comment.setText(commentCreate.getText());
        comment.setCreateDate(new Date());
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }

    public static Comment dtDtoEntity(CommentUpdate commentUpdate,Comment comment){
        comment.setText(commentUpdate.getText());
        return comment;
    }
}
