package com.wbm.scenergyspring.domain.tag.service;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.tag.service.command.GenreTagCommand;
import com.wbm.scenergyspring.domain.tag.service.command.InstrumentTagCommand;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final GenreTagRepository genreTagRepository;
    private final InstrumentTagRepository instrumentTagRepository;

    @Transactional(readOnly = false)
    public boolean createGenreTag(String genreName){
        if(genreTagRepository.existsByGenreName(genreName))
            return false;
        genreTagRepository.save(GenreTag.createGenreTag(genreName));
        return true;
    }

    public List<GenreTag> getAllGenreTags() {
        return genreTagRepository.findAll();
    }

    @Transactional(readOnly = false)
    public String updateGenreTag(GenreTagCommand command) {
        GenreTag genreTag = genreTagRepository.findByGenreName(command.getGenreTagName()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르입니다."));
        genreTag.updateGenreTag(command.getChangeGenreTagName());
        return genreTag.getGenreName();
    }

    @Transactional(readOnly = false)
    public boolean deleteGenreTag(String genreName) {
        GenreTag genreTag = genreTagRepository.findByGenreName(genreName).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르입니다."));
        genreTagRepository.delete(genreTag);
        return true;
    }

    @Transactional(readOnly = false)
    public boolean createInstrumentTag(String instrumentName) {
        if(instrumentTagRepository.existsByInstrumentName(instrumentName))
            return false;
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag(instrumentName));
        return true;
    }

    public List<InstrumentTag> getAllInstrumentTags() {
        return instrumentTagRepository.findAll();
    }

    @Transactional(readOnly = false)
    public String updateInstrumentTag(InstrumentTagCommand command) {
        InstrumentTag instrumentTag = instrumentTagRepository.findByInstrumentName(command.getInstrumentTagName()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 악기입니다."));
        instrumentTag.updateInstrumentTag(command.getChangeInstrumentTagName());
        return instrumentTag.getInstrumentName();
    }

    @Transactional(readOnly = false)
    public boolean deleteInstrumentTag(String instrumentName) {
        InstrumentTag instrumentTag = instrumentTagRepository.findByInstrumentName(instrumentName).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 악기입니다."));
        instrumentTagRepository.delete(instrumentTag);
        return true;
    }


}
