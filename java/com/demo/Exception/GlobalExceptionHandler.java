package com.demo.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				request.getDescription(false));
		LOGGER.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
				request.getDescription(false));
		LOGGER.error("BadRequestException: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}

/*
 * The logger.error("ResourceNotFoundException: {}", ex.getMessage(), ex); line
 * uses the SLF4J logging framework to log an error message. Let's break down
 * the parameters and the expected output:
 * 
 * Parameters Message Pattern ("ResourceNotFoundException: {}"):
 * 
 * This is a template string where {} is a placeholder for the first argument
 * (ex.getMessage()). SLF4J uses this pattern to format the log message.
 * 
 * FirstArgument (ex.getMessage()):
 * 
 * This method retrieves the message from the ResourceNotFoundException
 * instance, which typically provides a description of why the resource was not
 * found.
 * 
 * Second Argument (ex):
 * 
 * This is the exception object itself. By passing the exception object, SLF4J
 * will include the stack trace in the log output, which is extremely useful for
 * debugging.
 */
