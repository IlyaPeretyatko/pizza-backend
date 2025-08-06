package ru.nsu.assjohns.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private long id;

    private String username;

    private String accessToken;

    private String refreshToken;

}
