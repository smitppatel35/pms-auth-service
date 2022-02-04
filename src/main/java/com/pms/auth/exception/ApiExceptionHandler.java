package com.pms.auth.exception;

import com.pms.auth.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(
            ResourceNotFoundException e, WebRequest webResult) {

        return new ResponseEntity<>(new ErrorResponse("true", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException e, WebRequest webRequest) {

        return new ResponseEntity<>(new ErrorResponse("true", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public final ResponseEntity<ErrorResponse> handleResourceBadRequestException(
            ResourceBadRequestException e, WebRequest webRequest) {

        return new ResponseEntity<>(new ErrorResponse("true", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public final ResponseEntity<ErrorResponse> handleValidationFailureException(
            UserNotActivatedException e, WebRequest webRequest) {

        return new ResponseEntity<>(new ErrorResponse("true", e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllException(
            Exception e, WebRequest webRequest) {

        return new ResponseEntity<>(new ErrorResponse("true", e.getMessage()), HttpStatus.CONFLICT);
    }

}
