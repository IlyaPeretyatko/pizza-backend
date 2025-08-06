package ru.nsu.assjohns.validator.cart;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.nsu.assjohns.dto.CartPatchRequest;
import ru.nsu.assjohns.dto.CartPostRequest;
import ru.nsu.assjohns.validator.DefaultValidator;

@Component
public class CartValidatorImp extends DefaultValidator implements CartValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CartPostRequest.class) || clazz.equals(CartPatchRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
