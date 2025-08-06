package ru.nsu.assjohns.dto.user;

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
public class UserPostRequest {
    @NotNull(message = "Name cannot be null.")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long.")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Bad input email.")
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "The input must include at least one lowercase letter, one uppercase letter, one digit, " +
                    "and at least one special character.")
    private String password;
}
