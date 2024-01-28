package com.wbm.scenergyspring.domain.post.videoPost.entity;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.UpdateVideoCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String videoUrlPath;

    String thumbnailUrlPath;

    String musicTitle;

    String artist;

    public static Video createVideo(CreateVideoCommand command) {
        Video video = new Video();
        video.videoUrlPath = command.getVideoUrlPath();
        video.thumbnailUrlPath = command.getThumbnailUrlPath();
        video.musicTitle = command.getVideoTitle();
        video.artist = command.getArtist();

        return video;
    }

    public void updateVideo(UpdateVideoCommand command) {
        if (command.getVideoUrlPath() != null)
            videoUrlPath = command.getVideoUrlPath();
        if (command.getThumbnailUrlPath() != null)
            thumbnailUrlPath = command.getThumbnailUrlPath();
        if (command.getVideoTitle() != null)
            musicTitle = command.getVideoTitle();
        if (command.getVideoArtist() != null)
            artist = command.getVideoArtist();
    }

}
