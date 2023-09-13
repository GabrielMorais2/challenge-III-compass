package com.moraes.gabriel.mscars.exception;

import com.moraes.gabriel.mscars.exception.validationerror.ValidationError;
import com.moraes.gabriel.mscars.exception.validationerror.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    private final LocalDateTime timestamp = LocalDateTime.now();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex) {
        ErrorResponse message = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                timestamp,
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> carAlreadyExistsException(CarAlreadyExistsException ex) {
        ErrorResponse message = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                timestamp,
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PilotAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> pilotAlreadyExistsException(PilotAlreadyExistsException ex) {
        ErrorResponse message = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                timestamp,
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResponse> carNotFoundException(CarNotFoundException ex) {
        ErrorResponse message = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                timestamp,
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getDefaultMessage(), fieldError.getField()))
                .collect(Collectors.toList());

        ValidationErrorResponse message = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                timestamp,
                "Validation failed: Invalid argument(s) in the request",
                validationErrors);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}