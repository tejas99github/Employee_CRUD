package com.demo.Exception;

import java.time.LocalDateTime;

public class ErrorResponse {
	private int statusCode;
	private String message;
	private String details;
	private LocalDateTime timestamp;

	public ErrorResponse(int statusCode, String message, String details) {
		this.statusCode = statusCode;
		this.message = message;
		this.details = details;
		this.timestamp = LocalDateTime.now();
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
