package com.wbm.scenergyspring.domain.tag.service;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final GenreTagRepository genreTagRepository;

    @Transactional(readOnly = false)
    public boolean createGenreTag(String genreName){
        if(genreTagRepository.existsByGenreName(genreName))
            return false;
        genreTagRepository.save(GenreTag.createGenreTag(genreName));
        return true;
    }

}
