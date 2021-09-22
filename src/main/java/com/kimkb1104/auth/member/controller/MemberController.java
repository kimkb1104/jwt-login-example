package com.kimkb1104.auth.member.controller;

import com.kimkb1104.auth.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    @GetMapping("/member")
    public String member(@AuthenticationPrincipal Account account) {
        System.out.println("user.getEmail() : " + account.getEmail());
        return null;
    }
}
