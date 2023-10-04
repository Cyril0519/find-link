package com.w2.result.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String code;
    private String message;
    private T content;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

