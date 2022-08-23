package com.chunyan.chunyan.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Purchase;
import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.common.enums.BagStatus;
import com.chunyan.chunyan.repository.PurchaseRepository;
import com.chunyan.chunyan.vo.PurchaseVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{

	PurchaseRepository purchaseRepository;

	UserService userService;
	BagService bagService;
	ReviewService reviewService;

	@Override
	@Transactional
	public String addPurchase(Purchase purchase) throws NotFoundException {
		User user = userService.getUser(purchase.getUser_id());
		List<Bag> bag = bagService.getBagById(purchase.getUser_id());
		if (bag.isEmpty()) {
			throw new NotFoundException("no items in bag");
		}

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
	@Transactional
	public List<PurchaseVO> findAllByRange(String user_id, String sDate, String eDate, Boolean sample, Boolean review) {
		List<Purchase> resultRaw = purchaseRepository.findAllByRange(user_id, sDate, eDate);
		List<PurchaseVO> result = resultRaw.stream().map(PurchaseVO::fromEntity).collect(Collectors.toList());
		if (!ObjectUtils.isEmpty(sample)) {
			result = result.stream()
				.peek(p -> p.setBag(p.getBag().stream()
					.filter(b -> b.getIs_sample() == sample)
					.collect(Collectors.toList())))
				.filter(p -> !p.getBag().isEmpty()).collect(Collectors.toList());
		}

		if (!ObjectUtils.isEmpty(review)) {
			result = result.stream()
				.peek(p -> p.setBag(p.getBag().stream()
					.filter(b -> checkHasReviewed(b.getItem_id(), b.getUser_id()) == review)
					.collect(Collectors.toList())))
				.filter(p -> !p.getBag().isEmpty()).collect(Collectors.toList());
		}

		return result.stream().filter(p -> !p.getBag().isEmpty()).collect(Collectors.toList());
	}

	private boolean checkHasReviewed(String itemId, String userId) {
		log.error("result = " + reviewService.getReviewByItemIdAndUserId(itemId, userId).size());
		return !ObjectUtils.isEmpty(reviewService.getReviewByItemIdAndUserId(itemId, userId));
	}

	private String generatePurcahseId() {
		return UUID.randomUUID().toString().substring(0, 20);
	}
}
