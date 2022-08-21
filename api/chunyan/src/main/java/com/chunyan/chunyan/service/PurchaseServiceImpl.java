package com.chunyan.chunyan.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Purchase;
import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.dao.enums.BagStatus;
import com.chunyan.chunyan.repository.PurchaseRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{

	PurchaseRepository purchaseRepository;

	UserService userService;
	BagService bagService;

	@Override
	@Transactional
	public String addPurchase(Purchase purchase) throws NotFoundException {
		User user = userService.getUser(purchase.getUser_id());
		List<Bag> bag = bagService.getBagById(purchase.getUser_id());

		purchase.setPurchase_id(generatePurcahseId());
		purchase.setBag(bag);

		purchaseRepository.save(purchase);

		for (Bag b : bag) {
			b.setPurchase_id(purchase.getPurchase_id());
			b.setStatus(BagStatus.PURCHASED);
			bagService.updateBag(b);
		}

		return purchase.getPurchase_id();
	}

	@Override
	public List<Purchase> findAllByRange(String user_id, String sDate, String eDate) {
		purchaseRepository.findAllByRange(user_id, sDate, eDate);
		return null;
	}

	private String generatePurcahseId() {
		return UUID.randomUUID().toString().substring(0, 20);
	}
}
