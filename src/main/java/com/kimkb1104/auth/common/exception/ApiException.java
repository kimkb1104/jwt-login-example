package com.kimkb1104.auth.common.exception;

import com.kimkb1104.auth.common.model.ErrorEnum;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private ErrorEnum error;
    private String message;

    public ApiException(ErrorEnum error, String message) {
        super(error.getCode());
        this.error = error;
        this.message = message;
    }
}
