package com.wbm.scenergyspring.domain.like.controller;

import com.wbm.scenergyspring.domain.like.controller.request.GetLikeCountRequest;
import com.wbm.scenergyspring.domain.like.controller.request.GetLikeListRequest;
import com.wbm.scenergyspring.domain.like.controller.request.LikePostRequest;
import com.wbm.scenergyspring.domain.like.controller.request.UnlikePostRequest;
import com.wbm.scenergyspring.domain.like.controller.response.GetLikeCountResponse;
import com.wbm.scenergyspring.domain.like.controller.response.GetLikeListResponse;
import com.wbm.scenergyspring.domain.like.entity.Like;
import com.wbm.scenergyspring.domain.like.service.LikeService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post-like")
    public ResponseEntity postLike(@RequestBody LikePostRequest request) {
        log.info("LikePostRequest: " + request);
        likeService.likePost(request.toCreateLikePostCommand());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/post-unlike")
    public ResponseEntity postUnlike(@RequestBody UnlikePostRequest request) {
        log.info("UnlikePostRequest: " + request);
        likeService.unlikePost(request.toCreateUnlikePostCommand());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-likelist")
    public ResponseEntity<ApiResponse<GetLikeListResponse>> getLikeList(GetLikeListRequest request) {
        log.info("GetLikeListRequest: " + request);
        List<Like> likeList = likeService.findLikeListByPostId(request.toCreateGetLikeListCommand());
        GetLikeListResponse response = GetLikeListResponse.builder()
                .likeList(GetLikeListResponse.from(likeList))
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @GetMapping("/get-like-count")
    public ResponseEntity<ApiResponse<GetLikeCountResponse>> getLikeCount(GetLikeCountRequest request) {
        log.info("GetLikeCountRequest: " + request);
        int count = likeService.countLikeByPostId(request.toCreateGetLikeCountCommand());
        GetLikeCountResponse response = GetLikeCountResponse.builder()
                .likeCount(count)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }
}