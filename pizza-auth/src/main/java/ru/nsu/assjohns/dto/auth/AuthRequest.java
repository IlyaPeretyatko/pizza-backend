package ru.nsu.assjohns.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for sign in")
public class AuthRequest {
    @NotNull(message = "Username cannot be null.")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters long.")
    @Schema(description = "Username must be between 2 and 50 characters long")
    private String username;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "The input must include at least one lowercase letter, one uppercase letter, one digit, " +
                    "and at least one special character.")
    @Schema(description = "The input must include at least one lowercase letter, one uppercase letter, one digit, " +
            "and at least one special character",
            example = "Qwerty123!")
    private String password;
}
