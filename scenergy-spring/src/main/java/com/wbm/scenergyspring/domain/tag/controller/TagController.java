package com.wbm.scenergyspring.domain.tag.controller;

import com.wbm.scenergyspring.domain.tag.service.TagService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/genre/create")
    public ResponseEntity<ApiResponse<Boolean>> createGenreTag(String genreName){
        System.out.println("입력 장르 : "+genreName);
        if(tagService.createGenreTag(genreName))
            return new ResponseEntity<>(ApiResponse.createSuccess(true), HttpStatus.OK);
        return new ResponseEntity<>(ApiResponse.createSuccess(false), HttpStatus.OK);
    }


}
