package ru.nsu.assjohns.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {
    private String refreshToken;
}
