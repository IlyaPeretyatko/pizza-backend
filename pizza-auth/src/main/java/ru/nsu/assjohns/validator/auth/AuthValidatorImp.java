package ru.nsu.assjohns.validator.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.nsu.assjohns.dto.auth.AuthRequest;
import ru.nsu.assjohns.validator.DefaultValidator;


@Component
@RequiredArgsConstructor
public class AuthValidatorImp extends DefaultValidator implements AuthValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AuthRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}