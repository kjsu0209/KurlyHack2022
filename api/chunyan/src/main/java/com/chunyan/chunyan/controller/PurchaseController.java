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
import com.chunyan.chunyan.dao.Purchase;
import com.chunyan.chunyan.service.PurchaseService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@AllArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

	PurchaseService purchaseService;

	@PostMapping
	public Response<Purchase> addPurchase(@RequestBody PurchaseDto purchaseDto) throws NotFoundException {
		Purchase purchase = Purchase.fromPurchaseDto(purchaseDto);
		String purchaseId = purchaseService.addPurchase(purchase);
		purchase.setPurchase_id(purchaseId);
		return new Response<>(HttpStatus.CREATED.value(), purchase);
	}

	@GetMapping("/{user_id}")
	public Response<List<Purchase>> getPurchaseList(@PathVariable String user_id, @RequestParam String start, @RequestParam String end) {
		List<Purchase> purchaseList = purchaseService.findAllByRange(user_id, start, end);

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
