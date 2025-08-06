package ru.nsu.assjohns.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchRequest {

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "The input must include at least one lowercase letter, one uppercase letter, one digit, " +
                    "and at least one special character.")
    private String password;

}
