package com.wbm.scenergyspring.domain.post.videoPost.controller;

import com.wbm.scenergyspring.domain.post.videoPost.controller.request.SearchVideoPostRequest;
import com.wbm.scenergyspring.domain.post.videoPost.controller.request.UpdateVideoPostRequest;
import com.wbm.scenergyspring.domain.post.videoPost.controller.request.UploadVideoPostRequest;
import com.wbm.scenergyspring.domain.post.videoPost.controller.response.SearchVideoPostResponse;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.SearchVideoPostCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommandResponse;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/video-post")
public class VideoController {

    private final VideoPostService videoPostService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VideoPostCommandResponse>>> getAllVideoPosts() {

        return new ResponseEntity<>(ApiResponse.createSuccess(videoPostService.getAllVideoPost()), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<VideoPostCommandResponse>> getVideoPost(Long id) {
        VideoPostCommandResponse videoPost = videoPostService.getVideoPost(id);
        return new ResponseEntity<>(ApiResponse.createSuccess(videoPost), HttpStatus.OK);
    }

    @GetMapping("/list/following")
    public ResponseEntity<ApiResponse<List<VideoPostCommandResponse>>> getAllFollowingVideoPosts(Long id) {
        return new ResponseEntity<>(ApiResponse.createSuccess(videoPostService.getFollowingVideoPost(id)), HttpStatus.OK);
    }

    @PostMapping("/upload/video")
    public ResponseEntity<ApiResponse<String>> uploadJustVideo(MultipartFile justVideo) {
        String videoUrlPath = videoPostService.uploadJustVideoS3(justVideo);
        if (videoUrlPath == null)
            return new ResponseEntity<>(ApiResponse.createError("video error"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ApiResponse.createSuccess(videoUrlPath), HttpStatus.OK);
    }

    @PostMapping("/upload/thumbnail")
    public ResponseEntity<ApiResponse<String>> uploadThumbnail(MultipartFile thumbnail) {
        String thumbnailUrlPath = videoPostService.uploadThumbnailS3(thumbnail);
        if (thumbnailUrlPath == null)
            return new ResponseEntity<>(ApiResponse.createError("video error"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ApiResponse.createSuccess(thumbnailUrlPath), HttpStatus.OK);
    }

    @PostMapping("/upload/video-post")
    public ResponseEntity<ApiResponse<String>> uploadVideoPost(@RequestBody UploadVideoPostRequest request) {

        Video video = videoPostService.createVideo(request.toCreateVideo());

        VideoPostCommand command = request.toCreateVideoPost(video);

        videoPostService.createVideoPost(command);

        return new ResponseEntity<>(ApiResponse.createSuccess("success"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateVideoPost(@RequestBody UpdateVideoPostRequest request) {
        if (!videoPostService.updateVideoPost(request))
            return new ResponseEntity<>(ApiResponse.createError("변경 사항이 없습니다."), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(ApiResponse.createSuccess("success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteVideoPost(Long id) {
        videoPostService.deleteVideoPost(id);
        return new ResponseEntity<>(ApiResponse.createSuccess(true), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<SearchVideoPostResponse>>> searchVideoPost(@RequestBody SearchVideoPostRequest request) {
        SearchVideoPostCommand command = SearchVideoPostCommand.builder()
                .word(request.getWord())
                .gt(request.getGt())
                .it(request.getIt())
                .build();
        return new ResponseEntity<>(ApiResponse.createSuccess(videoPostService.searchVideoPostsByCondition(command)), HttpStatus.OK);
    }

}
