package com.rafa.dsclient.resource.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rafa.dsclient.services.exceptions.DatabaseException;
import com.rafa.dsclient.services.exceptions.ResourceNotFoundException;

@ControllerAdvice //intercepta a exceção do resource
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		error.setTimestamp(Instant.now() );
		error.setStatus(status.value() );
		error.setError("Resource not found");
		error.setMessage(e.getMessage() );
		error.setPath(request.getRequestURI() );
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		error.setTimestamp(Instant.now() );
		error.setStatus(status.value() );
		error.setError("Database exception");
		error.setMessage(e.getMessage() );
		error.setPath(request.getRequestURI() );
		
		return ResponseEntity.status(status).body(error);
	}

}