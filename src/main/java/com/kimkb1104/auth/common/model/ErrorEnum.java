package com.kimkb1104.auth.common.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {

    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST , "10000"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "10001")
    ;

    private final HttpStatus status;
    private final String code;

    ErrorEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

}
