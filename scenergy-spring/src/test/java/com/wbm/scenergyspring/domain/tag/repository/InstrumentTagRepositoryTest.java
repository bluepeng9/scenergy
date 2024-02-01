package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.service.TagService;
import com.wbm.scenergyspring.global.exception.BusinessException;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class InstrumentTagRepositoryTest {

    @Autowired
    private InstrumentTagRepository instrumentTagRepository;
    @Autowired
    private TagService tagService;

    @Test
    @DisplayName("악기 태그 추가 테스트")
    public void createInstrumentTest() {
        //given
        String instrument1 = "드럼";
        String instrument2 = "베이스";
        //when
        String testInstrumentName1 = tagService.createInstrumentTag(instrument1);
        String testInstrumentName2 = tagService.createInstrumentTag(instrument2);
        //then
        assertThat(instrument1).isEqualTo(testInstrumentName1);
        assertThat(instrument2).isEqualTo(testInstrumentName2);
    }

    @Test
    @DisplayName("악기 태그 추가 중복 테스트")
    public void duplicateCreateInstrumentTag() {
        //given
        String instrument1 = "기타";
        String instrument2 = "기타";
        //when
        String testInstrumentName1 = tagService.createInstrumentTag(instrument1);
        //then
        assertThat(testInstrumentName1).isEqualTo(instrument1);
        Assertions.assertThrows(BusinessException.class, () -> tagService.createInstrumentTag(instrument2));
    }

    @Test
    @DisplayName("null & empty 태그 입력 테스트")
    public void nullAndEmptyCreateInstrumentTag() {
        //given
        String instrumentName1 = null;
        String instrumentName2 = "  ";
        //when
        //then
        Assertions.assertThrows(BusinessException.class, () -> tagService.createInstrumentTag(instrumentName1));
        Assertions.assertThrows(BusinessException.class, () -> tagService.createInstrumentTag(instrumentName2));
    }

    @Test
    @DisplayName("InstrumentTagRepository save 테스트")
    public void saveInstrumentTag() {
        //given
        InstrumentTag instrumentTag = InstrumentTag.createInstrumentTag("바이올린");
        //when
        InstrumentTag saveInstrumentTag = instrumentTagRepository.save(instrumentTag);
        //then
        assertThat(instrumentTag).isEqualTo(saveInstrumentTag);
        assertThat(instrumentTag.getInstrumentName()).isEqualTo(saveInstrumentTag.getInstrumentName());
    }

    @Test
    @DisplayName("InstrumentTagRepository find 테스트")
    public void findInstrumentTag() {
        //given
        InstrumentTag saveInstrumentTag = instrumentTagRepository.save(InstrumentTag.createInstrumentTag("바이올린"));
        //when
        InstrumentTag findInstrumentTag = instrumentTagRepository.findById(saveInstrumentTag.getId()).orElseThrow(() -> new EntityNotFoundException(1 + "은 존재하지 않는 악기 Id입니다."));
        //then
        assertThat(saveInstrumentTag).isEqualTo(findInstrumentTag);
        assertThat(saveInstrumentTag.getInstrumentName()).isEqualTo(findInstrumentTag.getInstrumentName());
    }

    @Test
    @DisplayName("InstrumentTagRepository delete 테스트")
    public void deleteInstrumentTag() {
        //given
        InstrumentTag saveInstrumentTag = instrumentTagRepository.save(InstrumentTag.createInstrumentTag("바이올린"));
        //when
        instrumentTagRepository.deleteById(saveInstrumentTag.getId());
        //then
        assertThat(instrumentTagRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("InstrumentTagRepository exists 테스트")
    public void existsInstrumentTag() {
        //given
        InstrumentTag saveInstrumentTag = instrumentTagRepository.save(InstrumentTag.createInstrumentTag("바이올린"));
        //when

        //then
        assertThat(instrumentTagRepository.existsByInstrumentName("바이올린")).isTrue();
        assertThat(instrumentTagRepository.existsByInstrumentName("베이스")).isFalse();

    }

    @Test
    @DisplayName("InstrumentTagRepository InstrumentName 테스트")
    public void findByInstrumentName() {
        //given
        InstrumentTag saveInstrumentTag = instrumentTagRepository.save(InstrumentTag.createInstrumentTag("바이올린"));
        //when
        InstrumentTag findInstrumentTag = instrumentTagRepository.findByInstrumentName("바이올린").orElseThrow(() -> new EntityNotFoundException("바이올린은 존재하지 않는 악기입니다."));
        //then
        assertThat(saveInstrumentTag).isEqualTo(findInstrumentTag);
        assertThat(saveInstrumentTag.getInstrumentName()).isEqualTo(findInstrumentTag.getInstrumentName());
    }

}