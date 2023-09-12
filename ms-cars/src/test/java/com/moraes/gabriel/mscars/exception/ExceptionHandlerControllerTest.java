package com.moraes.gabriel.mscars.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

    @InjectMocks
    private ExceptionHandlerController exceptionHandlerController;

    @Test
    void globalExceptionHandler() {
        Exception ex = new Exception("Test exception");

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandlerController.globalExceptionHandler(ex);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Test exception", responseEntity.getBody().getMessage());
    }

    @Test
    void carAlreadyExistsException() {
        CarAlreadyExistsException ex = new CarAlreadyExistsException("Test exception");

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandlerController.carAlreadyExistsException(ex);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Test exception", responseEntity.getBody().getMessage());
    }

    @Test
    void pilotAlreadyExistsException() {
        PilotAlreadyExistsException ex = new PilotAlreadyExistsException("Test exception");

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandlerController.pilotAlreadyExistsException(ex);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Test exception", responseEntity.getBody().getMessage());
    }

    @Test
    void carNotFoundException() {
        CarNotFoundException ex = new CarNotFoundException("Test exception");

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandlerController.carNotFoundException(ex);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Test exception", responseEntity.getBody().getMessage());
    }
}