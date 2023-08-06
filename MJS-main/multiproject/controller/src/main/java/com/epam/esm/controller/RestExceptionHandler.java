package com.epam.esm.controller;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.response.ErrorResponse;

import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                HttpStatus.NOT_FOUND.value() + exc.getMessage().substring(exc.getMessage().indexOf(":") + 2));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                exc.getMessage(),
                HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ServiceException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BusinessValidationException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(AuthorizationException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                exc.getMessage(),
                HttpStatus.FORBIDDEN.toString());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}






