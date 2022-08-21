package com.chunyan.chunyan.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.controller.BagController;
import com.chunyan.chunyan.controller.PurchaseController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="purchase", schema = "kurly_schema")
public class Purchase {
	@Id
	private String purchase_id;

	private String user_id;
	@CreationTimestamp
	private LocalDateTime purchase_dt;
	private int delivery_fee;
	private int payment_amount;

	@OneToMany
	@JoinColumn(name = "purchase_id")
	private List<Bag> bag;

	public static Purchase fromPurchaseDto(PurchaseController.PurchaseDto purchaseDto) {
		Purchase purchase = new Purchase();
		BeanUtils.copyProperties(purchaseDto, purchase);
		if (purchaseDto.getPurchase_dt() != null)
			purchase.setPurchase_dt(LocalDateTime.parse(purchaseDto.getPurchase_dt(), DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		return purchase;
	}
}
