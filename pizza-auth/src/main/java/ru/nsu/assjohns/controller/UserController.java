package ru.nsu.assjohns.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsu.assjohns.dto.user.UserPatchRequest;
import ru.nsu.assjohns.dto.user.UserPostRequest;
import ru.nsu.assjohns.dto.user.UserPostResponse;
import ru.nsu.assjohns.service.UserService;
import ru.nsu.assjohns.validator.user.UserValidator;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    @PostMapping
    @Operation(
            summary = "Sign Up",
            description = "Returns status code",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Validation exception", content = @Content())
            }
    )
    public UserPostResponse createUser(@Valid @RequestBody UserPostRequest userPostRequest,
                                       BindingResult bindingResult) {
        userValidator.validate(userPostRequest, bindingResult);
        return userService.createUser(userPostRequest);
    }

    @GetMapping("/verify")
    @Hidden
    public String verifyEmail(@RequestParam String token) {
        boolean isVerified = userService.verifyEmail(token);
        if (isVerified) {
            return "Email verification successful!";
        }
        return "Invalid verification token.";
    }

    @GetMapping("/request-reset-password")
    @Hidden
    public void requestResetPassword(@RequestParam String email) {
        userService.requestResetPassword(email);
    }

    @PatchMapping("/reset-password")
    @Hidden
    public void resetPasswordForm(@RequestParam String token,
                                  @RequestBody UserPatchRequest userPatchRequest,
                                  BindingResult bindingResult) {
        userValidator.validate(userPatchRequest, bindingResult);
        userService.resetPassword(token, userPatchRequest);
    }
    

}
