package ru.nsu.assjohns.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for sign up")
public class UserPostRequest {
    @NotNull(message = "Username cannot be null.")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters long.")
    @Schema(description = "Username must be between 2 and 50 characters long, required field")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Bad input email.")
    @Schema(description = "Email, required field")
    private String email;

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
