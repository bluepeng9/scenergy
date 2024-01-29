package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.service.TagService;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GenreTagRepositoryTest {

    @Autowired
    private GenreTagRepository genreTagRepository;
    @Autowired
    private TagService tagService;

    @Test
    @Transactional
    @DisplayName("장르 태그 추가 테스트")
    public void createGenreTag() {
        //given
        String genreName1 = "Jazz";
        String genreName2 = "재즈";
        //when
        String testGenreName1 = tagService.createGenreTag(genreName1);
        String testGenreName2 = tagService.createGenreTag(genreName2);
        //then
        assertThat(genreName1).isEqualTo(testGenreName1);
        assertThat(genreName2).isEqualTo(testGenreName2);
    }

    @Test
    @Transactional
    @DisplayName("장르 태그 추가 중복 테스트")
    public void duplicateCreateGenreTag() {
        //given
        String genreName1 = "Jazz";
        String genreName2 = "Jazz";
        //when
        String testGenreName1 = tagService.createGenreTag(genreName1);
        String testGenreName2 = tagService.createGenreTag(genreName2);
        //then
        assertThat(genreName1).isEqualTo(testGenreName1);
        assertThat(testGenreName2).isEqualTo("이미 입력된 장르입니다.");
    }

    @Test
    @Transactional
    @DisplayName("null & empty 태그 입력 테스트")
    public void nullAndEmptyCreateGenreTag() {
        //given
        String genreName1 = null;
        String genreName2 = "  ";
        //when
        String testGenreName1 = tagService.createGenreTag(genreName1);
        String testGenreName2 = tagService.createGenreTag(genreName2);
        //then
        assertThat(testGenreName1).isEqualTo("장르가 입력되지 않았습니다.");
        assertThat(testGenreName2).isEqualTo("장르가 입력되지 않았습니다.");
    }

    @Test
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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