package ru.nsu.assjohns.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsu.assjohns.dto.user.UserGetResponse;
import ru.nsu.assjohns.dto.user.UserPatchRequest;
import ru.nsu.assjohns.dto.user.UserPostRequest;
import ru.nsu.assjohns.dto.user.UserPostResponse;
import ru.nsu.assjohns.service.UserService;
import ru.nsu.assjohns.validator.auth.UserValidator;

import jakarta.validation.Valid;

import static ru.nsu.assjohns.config.constant.AuthData.AUTH_HEADER_NAME;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    @PostMapping
    public UserPostResponse createUser(@Valid @RequestBody UserPostRequest userPostRequest,
                                       BindingResult bindingResult) {
        userValidator.validate(userPostRequest, bindingResult);
        return userService.createUser(userPostRequest);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        boolean isVerified = userService.verifyEmail(token);
        if (isVerified) {
            return "Email verification successful!";
        }
        return "Invalid verification token.";
    }

    @GetMapping("/request-reset-password")
    public void requestResetPassword(@RequestParam String email) {
        userService.requestResetPassword(email);
    }

    @PatchMapping("/reset-password")
    public void resetPasswordForm(@RequestParam String token,
                                  @RequestBody UserPatchRequest userPatchRequest,
                                  BindingResult bindingResult) {
        userValidator.validate(userPatchRequest, bindingResult);
        userService.resetPassword(token, userPatchRequest);
    }
    

}
