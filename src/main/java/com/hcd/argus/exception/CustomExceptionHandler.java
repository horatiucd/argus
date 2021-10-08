package com.hcd.argus.exception;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
		
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException() {
    	return ResponseEntity.notFound().build();
    }
		
    @ExceptionHandler(value = OperationNotAllowedException.class)
    public ResponseEntity<?> handleEntityValidationException(RuntimeException ex) {
    	OperationNotAllowedException customException = (OperationNotAllowedException) ex;
    	    	    	
    	return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(Problem.create()
					.withTitle("Not Allowed")
					.withDetail(customException.getMessage()));
    }
}

