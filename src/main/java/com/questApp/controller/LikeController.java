package com.questApp.controller;

import com.questApp.data.dto.request.LikeCreate;
import com.questApp.data.dto.response.LikeResponse;
import com.questApp.data.entity.Like;
import com.questApp.service.LikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    final LikeService likeService;


    @GetMapping("/getAll")
    public List<LikeResponse>getAllLikes(@RequestParam Optional<Long>userId,@RequestParam Optional<Long>postId){
        return  likeService.getAllLikesWithParam(userId,postId);
    }

    @PostMapping("/new")
    public Like createOneLike(@RequestBody LikeCreate likeCreate){
        return likeService.createOneLike(likeCreate);
    }

    @GetMapping("/get/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){
        return likeService.getOneLikeById(likeId);
    }

    @DeleteMapping("/delete/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){
        likeService.deleteOneLikeById(likeId);
    }
}
