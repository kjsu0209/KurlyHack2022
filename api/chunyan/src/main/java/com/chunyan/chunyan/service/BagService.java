package com.chunyan.chunyan.service;

import com.chunyan.chunyan.dao.Bag;

public interface BagService {

	void addBag(Bag bag);

	void updateBag(Bag bag);

	void getBagById(String user_id);
}
