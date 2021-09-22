package com.kimkb1104.auth.account.controller;

import com.kimkb1104.auth.account.dto.JoinRequestDto;
import com.kimkb1104.auth.account.dto.LoginRequestDto;
import com.kimkb1104.auth.account.dto.TokenDto;
import com.kimkb1104.auth.common.model.CookieEnum;
import com.kimkb1104.auth.account.service.AccountService;
import com.kimkb1104.auth.common.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/join")
    public void join(@RequestBody JoinRequestDto joinRequestDto) {
        accountService.join(joinRequestDto);
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = accountService.login(loginRequestDto);
        CookieUtil.add(response, CookieEnum.KB_AT, tokenDto.getAccessToken());
        CookieUtil.add(response, CookieEnum.KB_RT, tokenDto.getAccessToken());
    }
}
