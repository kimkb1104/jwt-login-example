package com.kimkb1104.auth.common.util;

import com.kimkb1104.auth.common.model.CookieEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void add(HttpServletResponse response, CookieEnum cookieEnum, String value) {
        response.addHeader(HttpHeaders.SET_COOKIE,
                ResponseCookie
                        .from(cookieEnum.name(), value)
                        .httpOnly(true)
                        .maxAge(cookieEnum.getExpireTime())
                        .path("/")
                        .build().toString());
    }

    public static String get(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(cookieName))
                    return cookie.getValue();
            }
        }

        return null;
    }
}
