package com.wbm.scenergyspring.global.response;

import lombok.Getter;

@Getter
public class ErrorResponse<T> extends ApiResponse<T> {
    String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
