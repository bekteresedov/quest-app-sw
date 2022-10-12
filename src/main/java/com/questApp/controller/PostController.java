package com.questApp.controller;

import com.questApp.data.dto.request.PostCreate;
import com.questApp.data.dto.request.PostUpdate;
import com.questApp.data.dto.response.PostResponse;
import com.questApp.data.entity.Post;
import com.questApp.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    final PostService postService;


    @GetMapping("/getAll")
    public List<PostResponse>getAllPosts(@RequestParam Optional<Long>userId){
        return postService.getAllPosts(userId);
    }

    @PostMapping("/new")
    public Post createOnePost(@RequestBody PostCreate postCreate){
        return postService.createOnePost(postCreate);
    }

    @GetMapping("/get/{postId}")
    public  PostResponse getOnePost(@PathVariable Long postId){
        return postService.getOnePostByIdWithLikes(postId);
    }

    @PutMapping("/update/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdate postUpdate){
        return postService.updateOnePostById(postId,postUpdate);
    }

    @DeleteMapping("/delete/{postId}")

    public void deleteOnePost(@PathVariable Long postId){
        postService.deleteOnePostById(postId);
    }


}
