package ru.nsu.assjohns.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication API")
public class AuthController {

    private final AuthService authService;

    private final AuthValidator authValidator;

    @GetMapping("/validate")
    @Hidden
    public boolean validateToken(@RequestHeader(AUTH_HEADER_NAME) String token) {
        return authService.validateToken(token);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Sign Im",
            description = "Returns access and refresh token",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Validation exception", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Password is wrong", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "User wasn't found", content = @Content())
            }
    )
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest,
                              BindingResult bindingResult) {
        authValidator.validate(authRequest, bindingResult);
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Returns new access token",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "403", description = "Invalid refresh token", content = @Content())
            }
    )
    public AuthResponse refresh(@RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }


}
