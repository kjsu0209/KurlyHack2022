package com.chunyan.chunyan.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Response<T> {
	private int statusCode;
	private T result;
}
