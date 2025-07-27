package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;


    public static BaseResponse of (Exception e) {
        return BaseResponse.builder()
                .message(e.getMessage())
                .data(null)
                .build();
    }
    public static BaseResponse of (Exception e, Object data) {
        return BaseResponse.builder()
                .message(e.getMessage())
                .data(data)
                .build();
    }
    public static BaseResponse of (int statusCode, String message) {
        return BaseResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .data(null)
                .build();

    }
}
