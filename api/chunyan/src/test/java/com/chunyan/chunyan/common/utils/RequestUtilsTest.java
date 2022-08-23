package com.chunyan.chunyan.common.utils;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import com.chunyan.chunyan.service.RecommendServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class RequestUtilsTest {

	@Test
	public void requestTest() {
		String url = "http://3.37.191.95:5000/predict";
		JSONObject obj = RequestUtils.getJSON(url);
		assert obj != null;
		System.out.println(obj.toJSONString());
	}

	@Test
	public void postTest() {
		String url = "http://3.37.191.95:5000/predict";
		JSONObject input = new JSONObject();
		input.put("skin_type", 0);
		input.put("skin_info", 2);
		input.put("item_name", Long.parseLong("111950000047"));
		input.put("recommend_data_nums", 10);
		System.out.println(input.toJSONString());
		JSONObject obj = RequestUtils.postJSON(url, input);
		assert obj != null;
		System.out.println(obj.toJSONString());
	}

	@Test
	public void postTest2() throws JsonProcessingException {
		String url = "http://3.37.191.95:5000/deep_learning_predict";
		JSONObject input = new JSONObject();
		input.put("skin_type", 1);
		input.put("skin_info", 2);
		input.put("user_id", "supe****");
		input.put("age_group", 40);
		input.put("gender", 0);
		input.put("category", 1);
		input.put("recommend_data_nums", 10);
		System.out.println(input.toJSONString());
		JSONObject obj = RequestUtils.postJSON(url, input);
		assert obj != null;
		System.out.println(obj.toJSONString());
		ObjectMapper objectMapper = new ObjectMapper();
		RecommendServiceImpl.MLResponseData data = objectMapper.readValue(obj.toJSONString(),
			RecommendServiceImpl.MLResponseData.class);
		System.out.println(data.getRecommend_list());
	}

}