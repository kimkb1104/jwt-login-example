package com.kimkb1104.auth.account.jwt;

import com.kimkb1104.auth.common.model.CookieEnum;
import com.kimkb1104.auth.account.repository.AccountRepository;
import com.kimkb1104.auth.account.dto.TokenDto;
import com.kimkb1104.auth.common.exception.ApiException;
import com.kimkb1104.auth.common.model.ErrorEnum;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = Base64.getEncoder().encodeToString("kimkb1104secretkey".getBytes());

    private final AccountRepository accountRepository;

    // 토큰 생성
    public TokenDto createToken(String username, String refreshToken) {
        long now = System.currentTimeMillis();

        return TokenDto.builder()
                .accessToken(generateToken(username, now + CookieEnum.KB_AT.getExpireTime()))
                .refreshToken(
                        refreshToken == null ? generateToken(username, now + CookieEnum.KB_RT.getExpireTime())
                                : refreshToken)
                .build();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = accountRepository.findByEmail(parseClaims(token).getSubject())
                .orElseThrow(() -> new ApiException(ErrorEnum.UNAUTHORIZED_ERROR, "일치하는 ID 없음"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 유효성 체크
    public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
        try {
            return token != null && !parseClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    // 토큰 복호화
    private Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // 토큰 생성
    private String generateToken(String username, long expireTime) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(expireTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
