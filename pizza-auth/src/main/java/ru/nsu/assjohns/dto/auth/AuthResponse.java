package ru.nsu.assjohns.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for success sign in")
public class AuthResponse {

    private long id;

    private String username;

    @Schema(description = "Access token lives for one hour")
    private String accessToken;

    @Schema(description = "Refresh token lives for 30 hours")
    private String refreshToken;

}
