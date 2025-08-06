package ru.nsu.assjohns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsu.assjohns.dto.auth.AuthRequest;
import ru.nsu.assjohns.dto.auth.AuthResponse;
import ru.nsu.assjohns.dto.auth.RefreshRequest;
import ru.nsu.assjohns.service.AuthService;
import ru.nsu.assjohns.validator.auth.*;

import static ru.nsu.assjohns.config.constant.AuthData.AUTH_HEADER_NAME;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthValidator authValidator;

    @GetMapping("/validate")
    public boolean validateToken(@RequestHeader(AUTH_HEADER_NAME) String token) {
        return authService.validateToken(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest,
                              BindingResult bindingResult) {
        authValidator.validate(authRequest, bindingResult);
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }


}
