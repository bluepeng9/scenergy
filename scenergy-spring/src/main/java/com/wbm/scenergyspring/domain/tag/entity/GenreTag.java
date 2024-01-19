package com.wbm.scenergyspring.domain.tag.entity;

import jakarta.persistence.*;


/**
 * 0:전체, 1:팝, 2:발라드, 3:인디, 4:,힙합, 5:락, 6:R&B, 7:재즈, 8:클래식, 9: 기타
 */
@Entity
public class GenreTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String genreName;

    public void updateGenreTag(Long id, String changeGenreName){
        this.genreName=changeGenreName;
    }

    public static GenreTag createGenreTag(String genreName){
        GenreTag genreTag = new GenreTag();
        genreTag.genreName = genreName;
        return genreTag;
    }

}
