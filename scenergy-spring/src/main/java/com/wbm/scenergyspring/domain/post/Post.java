package com.wbm.scenergyspring.domain.post;

import lombok.Getter;

@Getter
public class Post {

    private String title;
    private String content;
    private String writer;

    public void updatePost(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void updatePostTitle(String title) {
        this.title = title;
    }

    public void updatePostContent(String content) {
        this.content = content;
    }

    public void updatePostWriter(String Writer) {
        this.writer = writer;
    }
}
