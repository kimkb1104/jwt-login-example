package com.kimkb1104.auth.account.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
