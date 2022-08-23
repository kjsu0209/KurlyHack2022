package com.chunyan.chunyan.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Purchase;
import com.chunyan.chunyan.service.PurchaseService;
import com.chunyan.chunyan.vo.PurchaseVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

	PurchaseService purchaseService;

	@PostMapping
	public Response<String> addPurchase(@RequestBody PurchaseDto purchaseDto) throws NotFoundException {
		Purchase purchase = Purchase.fromPurchaseDto(purchaseDto);
		String purchaseId = purchaseService.addPurchase(purchase);
		return new Response<>(HttpStatus.CREATED.value(), purchaseId);
	}

	@GetMapping("/{user_id}")
	public Response<List<PurchaseVO>> getPurchaseList(@PathVariable String user_id, @RequestParam String start,
		@RequestParam String end, @RequestParam(required = false) Boolean sample,
		@RequestParam(required = false) Boolean review) {
		log.error("hi");
		List<PurchaseVO> purchaseList = purchaseService.findAllByRange(user_id, start, end, sample, review);
		return new Response<>(HttpStatus.OK.value(), purchaseList);
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	public static class PurchaseDto {
		private String purchase_id;
		private String user_id;
		private String purchase_dt;
		private int delivery_fee;
		private int payment_amount;
	}
}
