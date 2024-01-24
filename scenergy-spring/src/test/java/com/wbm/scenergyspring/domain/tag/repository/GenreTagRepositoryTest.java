package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreTagRepositoryTest {

    @Autowired
    private GenreTagRepository genreTagRepository;

    @Test
    @DisplayName("GenreTagRepository save 테스트")
    public void saveGenreTag() {
        //given
        GenreTag genreTag = GenreTag.createGenreTag("Jazz");
        //when
        GenreTag saveGenreTag = genreTagRepository.save(genreTag);
        //then
        assertThat(genreTag).isEqualTo(saveGenreTag);
        assertThat(genreTag.getGenreName()).isEqualTo(saveGenreTag.getGenreName());
    }

    @Test
    @DisplayName("GenreTagRepository find 테스트")
    public void findGenreTag() {
        //given
        GenreTag saveGenreTag1 = genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        GenreTag saveGenreTag2 = genreTagRepository.save(GenreTag.createGenreTag("Pop"));
        //when
        GenreTag findGenreTag1 = genreTagRepository.findById(saveGenreTag1.getId()).orElseThrow(() -> new EntityNotFoundException(saveGenreTag1.getId() + "는 없는 장르 Id입니다."));
        GenreTag findGenreTag2 = genreTagRepository.findById(saveGenreTag2.getId()).orElseThrow(() -> new EntityNotFoundException(saveGenreTag2.getId() + "는 없는 장르 Id입니다."));
        //then
        assertThat(saveGenreTag1).isEqualTo(findGenreTag1);
        assertThat(saveGenreTag1.getGenreName()).isEqualTo(findGenreTag1.getGenreName());
        assertThat(saveGenreTag2).isEqualTo(findGenreTag2);
        assertThat(saveGenreTag2.getGenreName()).isEqualTo(findGenreTag2.getGenreName());
    }

    @Test
    @DisplayName("GenreTagRepository delete 테스트")
    public void deleteGenreTag() {
        //given
        GenreTag saveGenreTag = genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        //when
        genreTagRepository.deleteById(saveGenreTag.getId());
        //then
        assertThat(genreTagRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("GenreTagRepository exists 테스트")
    public void existsGenreTag() {
        //given
        GenreTag saveGenreTag = genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        //when

        //then
        assertThat(genreTagRepository.existsByGenreName("Jazz")).isTrue();
        assertThat(genreTagRepository.existsByGenreName("Pop")).isFalse();
    }

    @Test
    @DisplayName("GenreTagRepository GenreName 테스트")
    public void findByGenreName() {
        //given
        GenreTag genreTag = genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        //when
        GenreTag findGenreTag = genreTagRepository.findByGenreName("Jazz").orElseThrow(() -> new EntityNotFoundException(genreTag.getGenreName() + "은 존재하지 않는 장르입니다."));
        //then
        assertThat(genreTag).isEqualTo(findGenreTag);
        assertThat(genreTag.getGenreName()).isEqualTo(findGenreTag.getGenreName());
    }

}