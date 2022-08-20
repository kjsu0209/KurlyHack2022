package com.chunyan.chunyan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
public class HelloController {
	@Getter
	@AllArgsConstructor
	private static class HelloDTO {
		private String hello;
		private String name;
	}

	@RequestMapping("/hello")
	public HelloDTO hello(@RequestParam(required = false) String name) {
		HelloDTO hello = new HelloDTO("hello", name);

		return hello;
	}

}
