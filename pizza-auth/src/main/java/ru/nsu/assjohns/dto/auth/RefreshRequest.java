package ru.nsu.assjohns.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for requesting on access token refresh")
public class RefreshRequest {
    @Schema(description = "Refresh token lives for 30 hours")
    private String refreshToken;
}
