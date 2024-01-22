package com.wbm.scenergyspring.domain.tag.entity;

import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GenreTagTest {

    @Autowired
    private TagService tagService;
    @Autowired
    private GenreTagRepository genreTagRepository;

    @Test
    @Transactional
    @DisplayName("GenreTag 엔티티 생성 테스트")
    void createGenreTag(){
        //given
        String genreName = "Seoul";
        //when
        boolean result = tagService.createGenreTag(genreName);
        GenreTag genreTag = genreTagRepository.findByGenreName(genreName);
        //then
        assertThat(result).isTrue();
        assertThat(genreTag.getGenreName()).isEqualTo(genreTagRepository.findById(1L).get().getGenreName());
    }

}