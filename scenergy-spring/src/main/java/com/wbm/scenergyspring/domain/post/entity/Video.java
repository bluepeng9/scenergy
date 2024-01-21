package com.wbm.scenergyspring.domain.post.entity;

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

    String videoUrl;

    String thumbnail;

    String musicTitle;

    String artist;

    public static Video createVideo(
        String videoUrl,
        String thumbnail,
        String musicTitle,
        String artist
    ){
        Video video = new Video();
        video.videoUrl = videoUrl;
        video.thumbnail = thumbnail;
        video.musicTitle = musicTitle;
        video.artist = artist;

        return video;
    }

}
