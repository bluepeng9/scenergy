package com.wbm.scenergyspring.domain.post.controller;

import com.wbm.scenergyspring.domain.post.entity.Video;
import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.controller.request.UploadPostRequest;
import com.wbm.scenergyspring.domain.post.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.service.command.VideoCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostCommand;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoPostService videoPostService;

    @PostMapping(value = "/uploadPost")
    public ResponseEntity<ApiResponse<String>> uploadPost(@ModelAttribute UploadPostRequest request) {

        String urlPath = videoPostService.uploadVideo(request.getVideo());

        VideoCommand videoCommand = request.createVideo();
        videoCommand.videoUrl = urlPath;
        Video video = videoPostService.createVideo(videoCommand);

        VideoPostCommand videoPostCommand = request.createVideoPost();
        videoPostCommand.video = video;

        VideoPost videoPost = videoPostService.createVideoPost(videoPostCommand);

        videoPostService.createVideoPostGenreTags(request.getGenreTags(),videoPost);

        return new ResponseEntity<>(ApiResponse.createSuccess("success"), HttpStatus.OK);
    }

}
