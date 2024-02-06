package com.wbm.scenergyspring.domain.tag.controller.request;


import com.wbm.scenergyspring.domain.tag.service.command.GenreTagCommand;
import lombok.Data;

@Data
public class UpdateGenreTagRequest {

    private String genreTagName;
    private String changeGenreTagName;

    public GenreTagCommand toGenreTag() {
        return GenreTagCommand.builder()
                .genreTagName(genreTagName)
                .changeGenreTagName(changeGenreTagName)
                .build();
    }

}
