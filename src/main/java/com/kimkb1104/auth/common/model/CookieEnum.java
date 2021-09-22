package com.kimkb1104.auth.common.model;

import lombok.Getter;

@Getter
public enum CookieEnum {

    KB_AT(1000L * 60),
    KB_RT(1000L * 60 * 2);

    private final long expireTime;

    CookieEnum(long expireTime) {
        this.expireTime = expireTime;
    }

}
