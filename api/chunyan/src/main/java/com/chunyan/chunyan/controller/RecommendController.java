package com.chunyan.chunyan.controller;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.service.RecommendService;
import com.chunyan.chunyan.vo.RecommendItem;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

	private RecommendService recommendService;

	@GetMapping("/sample/{user_id}")
	public Response<List<RecommendItem>> recommendSampleItems(@PathVariable String user_id) throws
		NotFoundException,
		JsonProcessingException {
		return new Response<>(HttpStatus.OK.value(), recommendService.getRecommendSample(user_id));
	}

	@GetMapping("/item/{user_id}")
	public Response<List<RecommendItem>> recommendItem(@PathVariable String user_id) throws
		NotFoundException,
		JsonProcessingException {
		return new Response<>(HttpStatus.OK.value(), recommendService.getRecommendItem(user_id));
	}

	@GetMapping("/user/{user_id}")
	public Response<List<RecommendItem>> recommendItemByUserHist(@PathVariable String user_id) throws
		NotFoundException,
		JsonProcessingException {
		return new Response<>(HttpStatus.OK.value(), recommendService.getRecommendItemByUser(user_id, 5));
	}
}
