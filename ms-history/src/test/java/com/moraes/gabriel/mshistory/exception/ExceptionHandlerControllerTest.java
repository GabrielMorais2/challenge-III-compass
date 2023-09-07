package com.moraes.gabriel.mshistory.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

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
    void objectNotFoundException() {
        ObjectNotFoundException ex = new ObjectNotFoundException("Object not found");

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandlerController.objectNotFoundException(ex);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Object not found", responseEntity.getBody().getMessage());
    }

}