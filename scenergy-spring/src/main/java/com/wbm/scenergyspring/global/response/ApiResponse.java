package com.wbm.scenergyspring.global.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private T data;

    public static <T> ApiResponse<T> createSuccess(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> createError(String message) {
        return new ErrorResponse<T>(message);
    }
}
