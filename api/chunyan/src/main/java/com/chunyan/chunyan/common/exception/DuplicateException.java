package com.chunyan.chunyan.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DuplicateException extends Exception{
	private String message;
}