package ru.nsu.assjohns.error.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nsu.assjohns.error.response.ServiceErrorResponse;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.error.exception.ValidationException;
import ru.nsu.assjohns.error.response.ValidationErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ServiceErrorResponse> handleAllExceptions(Exception e) {
        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse(500, e.getMessage());
        return new ResponseEntity<>(serviceErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<ServiceErrorResponse> handleServiceException(ServiceException e) {
        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(serviceErrorResponse, HttpStatusCode.valueOf(e.getCode()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleMyExceptions(ValidationException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(ex.getMessage(), ex.getFieldsWithError());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
