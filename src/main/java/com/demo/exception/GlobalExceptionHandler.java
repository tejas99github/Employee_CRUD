package com.demo.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// without using custom error response class
	/*
	 * @ExceptionHandler(EmployeeNotFoundException.class) public
	 * ResponseEntity<Object>
	 * handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest
	 * request) { return new ResponseEntity<>(ex.getMessage(),
	 * HttpStatus.NOT_FOUND); }
	 */

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex,
			WebRequest request) {

		String reqDesc = request.getDescription(false);

		// Log the stack trace using default logging mechanism
		LOGGER.error("EmployeeNotFoundException occurred: {}", ex.getMessage(), ex);

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				LocalDateTime.now(), reqDesc);

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				LocalDateTime.now());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/*
	 * This logging statement is using SLF4J logging framework. Here's how it works:
	 * 
	 * logger.error: This logs a message at the error level.
	 * "EmployeeNotFoundException occurred: {}": This is the log message template.
	 * {} is a placeholder for parameters. ex.getMessage(): This retrieves
	 * themessage from the exception. ex: This is the exception object itself.
	 * 
	 * When the logging statement is executed, SLF4J will replace {} in the log
	 * message with the message obtained from ex.getMessage(). It will then log the
	 * message along with the stack trace of the exception ex.
	 */

	/*
	 * request.getDescription(true) is used to retrieve a description of the current
	 * web request. When true is passed as an argument, it includes the client IP
	 * address in the description with request else if set to false then not include
	 * CilentIP address.
	 */
}
