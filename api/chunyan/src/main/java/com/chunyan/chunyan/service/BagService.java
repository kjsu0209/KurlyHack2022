package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.controller.BagController;
import com.chunyan.chunyan.dao.Bag;

public interface BagService {

	void addBag(Bag bag) throws NotFoundException, DuplicateException;

	void updateBag(BagController.BagDto bagDto) throws NotFoundException;
	void updateBag(Bag bag) throws NotFoundException;

	List<Bag> getBagById(String user_id);
}
