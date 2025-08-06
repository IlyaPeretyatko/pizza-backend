package ru.nsu.assjohns.validator.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.nsu.assjohns.dto.user.UserPostRequest;
import ru.nsu.assjohns.error.exception.ValidationException;
import ru.nsu.assjohns.service.UserService;
import ru.nsu.assjohns.validator.DefaultValidator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidatorImp extends DefaultValidator implements UserValidator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserPostRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
        if (target instanceof UserPostRequest userPostRequest) {
            if (userService.existsByEmail(userPostRequest.getEmail())) {
                throw new ValidationException("User with this email already exists", List.of("email"));
            } else if (userService.existsByName(userPostRequest.getName())) {
                throw new ValidationException("User with this name already exists", List.of("name"));
            }
        }
    }
}
