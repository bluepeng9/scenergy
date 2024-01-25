package com.wbm.scenergyspring.domain.tag.controller;

import com.wbm.scenergyspring.domain.tag.controller.request.UpdateGenreTagRequest;
import com.wbm.scenergyspring.domain.tag.controller.request.UpdateInstrumentTagRequest;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.service.TagService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/tag/genre/create")
    public ResponseEntity<ApiResponse<String>> createGenreTag(String genreName) {
        System.out.println("입력 장르 : "+genreName);
        String msg = tagService.createGenreTag(genreName);
        if (msg.equals(genreName))
            return new ResponseEntity<>(ApiResponse.createSuccess(genreName), HttpStatus.OK);
        return new ResponseEntity<>(ApiResponse.createSuccess(msg), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tag/genres")
    public ResponseEntity<ApiResponse<List<GenreTag>>> getAllGenreTags() {
        return new ResponseEntity<>(ApiResponse.createSuccess(tagService.getAllGenreTags()), HttpStatus.OK);
    }

    @PutMapping("/tag/genre/update")
    public ResponseEntity<ApiResponse<String>> updateGenreTag(UpdateGenreTagRequest request) {
        return new ResponseEntity<>(ApiResponse.createSuccess(tagService.updateGenreTag(request.toGenreTag())), HttpStatus.OK);
    }

    @DeleteMapping("/tag/genre/delete")
    public ResponseEntity<ApiResponse<String>> deleteGenreTag(String genreName) {
        String msg = tagService.deleteGenreTag(genreName);
        if (msg.equals(genreName))
            return ResponseEntity.ok(ApiResponse.createSuccess(genreName + " 장르가 삭제되었습니다."));
        return new ResponseEntity<>(ApiResponse.createError("장르를 재확인 부탁드립니다."), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tag/instrument/create")
    public ResponseEntity<ApiResponse<String>> createInstrumentTag(String instrumentName) {
        System.out.println("입력 악기 : " + instrumentName);
        String msg = tagService.createInstrumentTag(instrumentName);
        if (msg.equals(instrumentName))
            return new ResponseEntity<>(ApiResponse.createSuccess(instrumentName), HttpStatus.OK);
        return new ResponseEntity<>(ApiResponse.createSuccess(msg), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tag/instruments")
    public ResponseEntity<ApiResponse<List<InstrumentTag>>> getAllInstrumentTags() {
        return new ResponseEntity<>(ApiResponse.createSuccess(tagService.getAllInstrumentTags()), HttpStatus.OK);
    }

    @PutMapping("/tag/instrument/update")
    public ResponseEntity<ApiResponse<String>> updateInstrumentTag(UpdateInstrumentTagRequest request) {
        return ResponseEntity.ok(ApiResponse.createSuccess(tagService.updateInstrumentTag(request.toInstrumentTag())));
    }

    @DeleteMapping("/tag/instrument/delete")
    public ResponseEntity<ApiResponse<String>> deleteInstrumentTag(String instrumentName) {
        String msg = tagService.deleteInstrumentTag(instrumentName);
        if (msg.equals(instrumentName))
            return ResponseEntity.ok(ApiResponse.createSuccess(instrumentName + " 악기가 삭제되었습니다."));
        return new ResponseEntity<>(ApiResponse.createError("악기를 재확인 부탁드립니다."), HttpStatus.BAD_REQUEST);
    }



}
