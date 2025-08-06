package ru.nsu.assjohns.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.assjohns.dto.auth.AuthRequest;
import ru.nsu.assjohns.dto.auth.AuthResponse;
import ru.nsu.assjohns.dto.auth.RefreshRequest;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.entity.User;
import ru.nsu.assjohns.security.AuthTokenProvider;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final AuthTokenProvider authTokenProvider;

    public boolean validateToken(String token) {
        return authTokenProvider.isValid(token);
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = userService.getUserByName(authRequest.getUsername());
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new ServiceException(401, "Password is wrong.");
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setUsername(user.getUsername());
        authResponse.setAccessToken(authTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        authResponse.setRefreshToken(authTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return authResponse;
    }

    public AuthResponse refresh(RefreshRequest refreshRequest) {
        return authTokenProvider.refreshToken(refreshRequest.getRefreshToken());
    }

}
