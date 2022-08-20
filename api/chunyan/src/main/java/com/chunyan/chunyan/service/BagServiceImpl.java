package com.chunyan.chunyan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.repository.BagRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BagServiceImpl implements BagService {

	BagRepository bagRepository;

	@Override
	public void addBag(Bag bag) {
		// TODO: 중복된 item_id와 user_id 조합 있는지 검사
		bagRepository.save(bag);
	}

	@Override
	public void updateBag(Bag bag) {
		bagRepository.save(bag);
	}

	@Override
	public List<Bag> getBagById(String user_id) {
		return bagRepository.findAllByUserId(user_id);
	}
}
