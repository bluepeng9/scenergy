package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InstrumentTagRepositoryTest {

    @Autowired
    private InstrumentTagRepository instrumentTagRepository;

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
        InstrumentTag findInstrumentTag = instrumentTagRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1 + "은 존재하지 않는 악기 Id입니다."));
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