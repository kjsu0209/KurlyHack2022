package com.chunyan.chunyan.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.controller.BagController;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.repository.BagRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BagServiceImpl implements BagService {

	BagRepository bagRepository;
	ItemService itemService;

	@Override
	@Transactional
	public void addBag(Bag bag) throws NotFoundException, DuplicateException {
		Item item = itemService.getItemById(bag.getItem_id());
		List<Bag> bags = getBagById(bag.getUser_id());
		if (bags.stream().anyMatch(b -> b.getItem_id().equals(bag.getItem_id()))) {
			throw new DuplicateException("duplicated! please use update api");
		}

		bagRepository.save(bag);
	}

	@Override
	public void updateBag(BagController.BagDto bagDto) throws NotFoundException {
		if (!bagRepository.existsById(bagDto.getBag_id())) {
			throw new NotFoundException("Bag not exists");
		}

		Bag bag = bagRepository.findById(bagDto.getBag_id()).get();
		if (ObjectUtils.isEmpty(bag.getPurchase_id())) {
			bag.setPurchase_id(bagDto.getPurchase_id());
		}

		if (!ObjectUtils.isEmpty(bagDto.getIs_sample()) && bagDto.getIs_sample() != bag.getIs_sample()) {
			bag.setIs_sample(bagDto.getIs_sample());
		}

		if (!ObjectUtils.isEmpty(bagDto.getStatus()) && bagDto.getStatus() != bag.getStatus()) {
			bag.setStatus(bagDto.getStatus());
		}

		if (bagDto.getCount() != bag.getCount()) {
			bag.setCount(bagDto.getCount());
		}

		bagRepository.save(bag);

	}

	@Override
	public void updateBag(Bag bag) throws NotFoundException {
		if (!bagRepository.existsById(bag.getBag_id())) {
			throw new NotFoundException("Bag not exists");
		}

		bagRepository.save(bag);
	}

	@Override
	public List<Bag> getBagById(String user_id) {
		return bagRepository.findAllByUserId(user_id);
	}
}
