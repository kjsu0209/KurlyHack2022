package com.chunyan.chunyan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.common.enums.BagStatus;
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
	public Response<String> addBag(@RequestBody BagDto bagDto) throws DuplicateException, NotFoundException {
		Bag bag = Bag.fromBagDto(bagDto);
		bagService.addBag(bag);

		return new Response<>(HttpStatus.CREATED.value(), "Bag created");
	}

	@PutMapping
	public Response<String> updateBag(@RequestBody BagDto bagDto) throws NotFoundException {
		bagService.updateBag(bagDto);

		return new Response<>(HttpStatus.OK.value(), "Bag updated");
	}

	@GetMapping("/{user_id}")
	public Response<List<Bag>> getBagByUserId(@PathVariable String user_id) {
		List<Bag> bags = bagService.getBagById(user_id);

		return new Response<>(HttpStatus.OK.value(), bags);
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	public static class BagDto {
		private Integer bag_id;
		private String user_id;
		private Boolean is_sample;
		private BagStatus status;
		private String item_id;
		private int count;
		private String purchase_id;
		private String dt;
	}
}
