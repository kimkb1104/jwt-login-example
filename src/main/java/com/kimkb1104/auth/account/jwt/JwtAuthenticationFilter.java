package com.kimkb1104.auth.account.jwt;

import com.kimkb1104.auth.account.dto.TokenDto;
import com.kimkb1104.auth.account.entity.Account;
import com.kimkb1104.auth.common.util.CookieUtil;
import com.kimkb1104.auth.common.model.CookieEnum;
import com.kimkb1104.auth.account.repository.AccountRepository;
import com.kimkb1104.auth.common.exception.ApiException;
import com.kimkb1104.auth.common.model.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String accessToken = CookieUtil.get(request, CookieEnum.KB_AT.name());

        if (accessToken != null) {

            // 토큰이 만료됐을 때
            if (!jwtTokenProvider.validateToken(accessToken)) {

                String refreshToken = CookieUtil.get(request, CookieEnum.KB_RT.name());
                if (refreshToken == null)
                    throw new ApiException(ErrorEnum.UNAUTHORIZED_ERROR, "Refresh Token 없음");

                Account account = accountRepository.findByRefreshToken(refreshToken)
                        .orElseThrow(() -> new ApiException(ErrorEnum.UNAUTHORIZED_ERROR, "Refresh Token 변조"));

                TokenDto tokenDto = jwtTokenProvider.createToken(account.getEmail(), refreshToken);
                accessToken = tokenDto.getAccessToken();
            }

            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
