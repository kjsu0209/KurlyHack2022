package com.chunyan.chunyan.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.controller.PurchaseController;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PurchaseVO {
	private String purchase_id;
	private String user_id;
	private LocalDateTime purchase_dt;
	private int delivery_fee;
	private int payment_amount;
	private List<Bag> bag;

	public static PurchaseVO fromEntity(Purchase purchaseEntity) {
		PurchaseVO purchase = new PurchaseVO();
		BeanUtils.copyProperties(purchaseEntity, purchase);
		return purchase;
	}

	public static PurchaseVO fromPurchaseDto(PurchaseController.PurchaseDto purchaseDto) {
		PurchaseVO purchase = new PurchaseVO();
		BeanUtils.copyProperties(purchaseDto, purchase);
		if (purchaseDto.getPurchase_dt() != null)
			purchase.setPurchase_dt(LocalDateTime.parse(purchaseDto.getPurchase_dt(), DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		return purchase;
	}

}
