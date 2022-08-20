package com.chunyan.chunyan.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	@AllArgsConstructor
	@Getter
	private static class ErrorResponse {
		private int statusCode;
		private String message;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> notSupported(HttpRequestMethodNotSupportedException e) {
		HttpStatus status =  HttpStatus.METHOD_NOT_ALLOWED;
		ErrorResponse response = new ErrorResponse(status.value(), "method not allowed");
		return new ResponseEntity<>(response, status);
	}
}
