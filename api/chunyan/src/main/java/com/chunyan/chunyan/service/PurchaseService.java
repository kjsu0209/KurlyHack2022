package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.dao.Purchase;

public interface PurchaseService {
	String addPurchase(Purchase purchase) throws NotFoundException;

	List<Purchase> findAllByRange(String user_id, String sDate, String eDate);
}
