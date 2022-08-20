package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.dao.Purchase;

public interface PurchaseService {
	void addPurchase(Purchase purchase);

	List<Purchase> findAllByRange(String user_id, String sDate, String eDate);
}
