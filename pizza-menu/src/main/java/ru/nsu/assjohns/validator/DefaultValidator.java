package ru.nsu.assjohns.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import ru.nsu.assjohns.error.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DefaultValidator {
    protected void createAndThrowException(Errors errors) {
        if (errors.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (FieldError error : errors.getFieldErrors()) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            List<String> fieldsWithError = errors.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField())
                    .collect(Collectors.toList());

            throw new ValidationException(errorMsg.toString(), fieldsWithError);
        }
    }
}