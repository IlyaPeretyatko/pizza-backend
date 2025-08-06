package ru.nsu.assjohns.validator.menu;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.nsu.assjohns.dto.MenuPatchRequest;
import ru.nsu.assjohns.dto.MenuPostRequest;
import ru.nsu.assjohns.validator.DefaultValidator;

@Component
public class MenuValidatorImp extends DefaultValidator implements MenuValidator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MenuPostRequest.class) || clazz.equals(MenuPatchRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
