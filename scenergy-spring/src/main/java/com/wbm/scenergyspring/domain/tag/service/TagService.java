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
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final GenreTagRepository genreTagRepository;
    private final InstrumentTagRepository instrumentTagRepository;

    @Transactional(readOnly = false)
    public String createGenreTag(String genreName) {

        if (!StringUtils.hasText(genreName))
            return "장르가 입력되지 않았습니다.";
        String str = StringUtils.trimWhitespace(genreName);
        if (genreTagRepository.existsByGenreName(str))
            return "이미 입력된 장르입니다.";
        genreTagRepository.save(GenreTag.createGenreTag(str));
        return genreName;
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
    public String deleteGenreTag(String genreName) {
        if (!StringUtils.hasText(genreName))
            return "장르가 입력되지 않았습니다.";
        String str = StringUtils.trimWhitespace(genreName);
        GenreTag genreTag = genreTagRepository.findByGenreName(str).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르입니다."));
        genreTagRepository.delete(genreTag);
        return genreName;
    }

    @Transactional(readOnly = false)
    public String createInstrumentTag(String instrumentName) {
        if (!StringUtils.hasText(instrumentName))
            return "악기가 입력되지 않았습니다.";
        String str = StringUtils.trimWhitespace(instrumentName);
        if (instrumentTagRepository.existsByInstrumentName(str))
            return "이미 입력된 악기입니다.";
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag(str));
        return instrumentName;
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
    public String deleteInstrumentTag(String instrumentName) {
        if (!StringUtils.hasText(instrumentName))
            return "악기가 입력되지 않았습니다.";
        InstrumentTag instrumentTag = instrumentTagRepository.findByInstrumentName(instrumentName).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 악기입니다."));
        instrumentTagRepository.delete(instrumentTag);
        return instrumentName;
    }


}
