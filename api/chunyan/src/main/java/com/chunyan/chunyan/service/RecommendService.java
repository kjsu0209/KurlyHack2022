package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.vo.RecommendItem;

public interface RecommendService {

	List<Item> getRecommendSample(String user_id);
	List<RecommendItem> getRecommendItem(String user_id);
}
