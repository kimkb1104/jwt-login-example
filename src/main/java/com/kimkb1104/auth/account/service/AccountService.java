package com.kimkb1104.auth.account.service;

import com.kimkb1104.auth.account.dto.JoinRequestDto;
import com.kimkb1104.auth.account.dto.LoginRequestDto;
import com.kimkb1104.auth.account.dto.TokenDto;
import com.kimkb1104.auth.account.entity.Account;
import com.kimkb1104.auth.account.jwt.JwtTokenProvider;
import com.kimkb1104.auth.common.exception.ApiException;
import com.kimkb1104.auth.common.model.ErrorEnum;
import com.kimkb1104.auth.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new ApiException(ErrorEnum.UNAUTHORIZED_ERROR, "일치하는 ID 없음"));
    }


    public void join(JoinRequestDto joinRequestDto) {
        accountRepository.save(
                Account.builder()
                        .email(joinRequestDto.getEmail())
                        .password(passwordEncoder.encode(joinRequestDto.getPassword()))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build());
    }

    public TokenDto login(LoginRequestDto loginRequestDto) {
        Account account = accountRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ApiException(ErrorEnum.UNAUTHORIZED_ERROR, "E-mail 불일치"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), account.getPassword()))
            throw new ApiException(ErrorEnum.UNAUTHORIZED_ERROR, "비밀번호 불일치");

        TokenDto tokenDto = jwtTokenProvider.createToken(account.getUsername(), null);
        accountRepository.save(account.updateRefreshToken(tokenDto.getRefreshToken()));

        return tokenDto;
    }
}
