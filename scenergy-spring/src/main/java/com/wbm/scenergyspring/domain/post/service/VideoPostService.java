package com.wbm.scenergyspring.domain.post.service;

import com.wbm.scenergyspring.domain.post.entity.Video;
import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.post.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.service.command.VideoCommand;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.VideoPostGenreTag;
import com.wbm.scenergyspring.domain.tag.repository.VideoPostGenreTagRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.config.PathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoPostService {

    private final PathProperties pathProperties;
    private final VideoRepository videoRepository;
    private final VideoPostRepository videoPostRepository;
    private final GenreTagRepository genreTagRepository;
    private final UserRepository userRepository;
    private final VideoPostGenreTagRepository videoPostGenreTagRepository;

    @Transactional(readOnly = false)
    public String uploadVideo(MultipartFile video){

        String videoName = StringUtils.cleanPath(video.getOriginalFilename());
        String videoPath = pathProperties.getVideoPath();
        String filePath =  videoPath + File.separator + videoName;

        try{
            File uploadVideo = new File(filePath);

            video.transferTo(uploadVideo);

            return filePath;
        }
        catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }

    }

    @Transactional(readOnly = false)
    public Video createVideo(VideoCommand command){
        Video newVideo = Video.createVideo(command.videoUrl, command.thumbnail, command.musicTitle, command.artist);
        videoRepository.save(newVideo);
        return newVideo;
    }

    @Transactional(readOnly = false)
    public VideoPost createVideoPost(VideoPostCommand command){
        User user = userRepository.findById(command.getUserId()).get();
        VideoPost videoPost = VideoPost.createVideoPost(
                user,
                command.getVideo(),
                command.getTitle(),
                command.getContent(),
                user.getNickname()
        );
        videoPostRepository.save(videoPost);
        return videoPost;
    }

    @Transactional(readOnly = false)
    public void createVideoPostGenreTags(List<Long> genreTagIds, VideoPost videoPost) {
        List<VideoPostGenreTag> videoPostGenreTags = videoPost.getVideoPostGenreTags();

        for (Long genreTagId : genreTagIds) {
            GenreTag genreTag = genreTagRepository.findById(genreTagId).get();

            VideoPostGenreTag videoPostGenreTag = new VideoPostGenreTag();
            videoPostGenreTag.updateVideoPost(videoPost);
            videoPostGenreTag.updateGenreTag(genreTag);

            videoPostGenreTagRepository.save(videoPostGenreTag);

            videoPostGenreTags.add(videoPostGenreTag);
        }
    }

}
