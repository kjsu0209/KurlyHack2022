package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.dao.Bag;

public interface BagService {

	void addBag(Bag bag);

	void updateBag(Bag bag);

	List<Bag> getBagById(String user_id);
}
