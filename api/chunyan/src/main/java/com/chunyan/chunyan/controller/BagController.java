package com.chunyan.chunyan.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.service.BagService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/bag")
public class BagController {

	BagService bagService;

	@PostMapping
	public Response<String> addBag(@RequestBody BagDto bagDto) {
		Bag bag = Bag.fromBagDto(bagDto);
		bagService.addBag(bag);

		return new Response<>(HttpStatus.CREATED.value(), "Bag created");
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	public static class BagDto {
		private Integer bag_id;
		private String user_id;
		private boolean is_sample;
		private String status;
		private int count;
		private String dt;
	}
}
