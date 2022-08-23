package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.vo.RecommendItem;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecommendService {

	List<RecommendItem> getRecommendSample(String user_id) throws NotFoundException, JsonProcessingException;
	List<RecommendItem> getRecommendItem(String user_id) throws NotFoundException, JsonProcessingException;
	List<RecommendItem> getRecommendItemByUser(String user_id, int limit) throws NotFoundException, JsonProcessingException;
}
