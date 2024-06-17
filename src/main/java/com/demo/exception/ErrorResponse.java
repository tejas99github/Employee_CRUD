package com.demo.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

	private int statusCode;
	private String message;
	private LocalDateTime timestamp;
	private String description;

	public ErrorResponse() {

	}

	public ErrorResponse(int statusCode, String message, LocalDateTime timestamp) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.timestamp = timestamp;
	}

	public ErrorResponse(int statusCode, String message, LocalDateTime timestamp, String description) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.timestamp = timestamp;
		this.description = description;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
