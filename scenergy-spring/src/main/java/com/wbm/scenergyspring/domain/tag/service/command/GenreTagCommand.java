package com.wbm.scenergyspring.domain.tag.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreTagCommand {

    private String genreTagName;
    private String changeGenreTagName;

}
