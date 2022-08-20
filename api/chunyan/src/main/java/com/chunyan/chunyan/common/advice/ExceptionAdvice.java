package com.chunyan.chunyan.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(NotFoundException.class)
	public Response<String> exception(NotFoundException e) {
		HttpStatus status =  HttpStatus.NOT_FOUND;
		return new Response<>(status.value(), e.getMessage());
	}

	@ExceptionHandler(DuplicateException.class)
	public Response<String> exception(DuplicateException e) {
		HttpStatus status =  HttpStatus.BAD_REQUEST;
		return new Response<>(status.value(), e.getMessage());
	}
}
