package com.wbm.scenergyspring.domain.post;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post {

    @Id
    @GeneratedValue
    private Long id;

    protected String title;
    protected String content;
    protected String writer;

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

