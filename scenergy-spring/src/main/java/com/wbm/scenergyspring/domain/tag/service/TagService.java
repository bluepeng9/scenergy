package com.wbm.scenergyspring.domain.tag.service;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.LocationTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final GenreTagRepository genreTagRepository;
    private final InstrumentTagRepository instrumentTagRepository;
    private final LocationTagRepository locationTagRepository;

    @Transactional(readOnly = false)
    public boolean createGenreTag(String genreName){
        if(genreTagRepository.existsByGenreName(genreName))
            return false;
        genreTagRepository.save(GenreTag.createGenreTag(genreName));
        return true;
    }

    @Transactional(readOnly = false)
    public boolean createInstrumentName(String instrumentName){
        if(instrumentTagRepository.existsByInstrumentName(instrumentName))
            return false;
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag(instrumentName));
        return true;
    }

    @Transactional(readOnly = false)
    public boolean createLocationTag(String locationName){
        if(locationTagRepository.existsByLocationName(locationName))
            return false;
        locationTagRepository.save(LocationTag.createLocationTag(locationName));
        return true;
    }

}
