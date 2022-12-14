package com.chunyan.chunyan.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.utils.RequestUtils;
import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.dao.Purchase;
import com.chunyan.chunyan.dao.Review;
import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.vo.PurchaseVO;
import com.chunyan.chunyan.vo.RecommendItem;
import com.chunyan.chunyan.vo.ReviewVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class RecommendServiceImpl implements RecommendService{

	@Value("${ec2.ml.host}")
	private String mlHost;

	@Value("${ec2.ml.port}")
	private int mlPort;

	@Autowired
	private UserService userService;
	@Autowired
	private BagService bagService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private PurchaseService purchaseService;

	private final int SAMPLE_CNT = 5;
	private final int ITEM_CNT = 10;

	@Override
	@Transactional
	public List<RecommendItem> getRecommendSample(String user_id) throws NotFoundException, JsonProcessingException {
		List<RecommendItem> result;
		try {
			result = getML(user_id, SAMPLE_CNT).stream().map(RecommendItem::fromEntity)
				.filter(RecommendItem::isHas_sample)
				.collect(Collectors.toList());
		} catch (NullPointerException e) {
			result = getRecommendItemByUser(user_id, SAMPLE_CNT);
		}

		if (result.isEmpty() || result.size() < SAMPLE_CNT) {
			result.addAll(getRecommendItemByUser(user_id, SAMPLE_CNT)); // ????????? ?????? ?????? ??????
		}

		return result.stream().filter(RecommendItem::isHas_sample).limit(SAMPLE_CNT)
			.collect(Collectors.toList());
	}

	@Override
	public List<RecommendItem> getRecommendItem(String user_id) throws NotFoundException, JsonProcessingException {
		// ????????? ??????????????? ?????? ??? ?????? ???????????? ??????
		List<RecommendItem> result;
		try {
			if (user_id.charAt(user_id.length()-1) == '*') { // ????????? ???????????? ?????? (?????? ????????? ??????)
				result = getDL(user_id, ITEM_CNT).stream().map(RecommendItem::fromEntity).collect(Collectors.toList());
			} else { // ???????????? ???????????? ??????
				result = getML(user_id, ITEM_CNT).stream().map(RecommendItem::fromEntity).collect(Collectors.toList());
			}
		} catch (NullPointerException e) {
			result = getRecommendItemByUser(user_id, ITEM_CNT);
		}

		if (result.isEmpty() || result.size() < ITEM_CNT) {
			result.addAll(getRecommendItemByUser(user_id, ITEM_CNT)); // ????????? ?????? ?????? ??????
		}

		return result.stream().limit(ITEM_CNT).collect(Collectors.toList());
	}

	@Override
	public List<RecommendItem> getRecommendItemByUser(String user_id, int limit) {
		// 1???: ?????? ?????? 4??? ????????? ?????? (????????? -> ??????????????? ?????? ????????? ?????? ?????? ??????)
		List<RecommendItem> result = reviewService.getReviewByUserId(user_id).stream().map(ReviewVO::fromEntity).filter(r -> r.getUser_rating() >= 4)
			.map(r -> {
			try {
				return itemService.getItemById(r.getItem_id());
			} catch (NotFoundException e) {
				return null;
			}
		}).filter(Objects::nonNull).map(RecommendItem::fromEntity).collect(Collectors.toList());

		// 3???: ?????? ?????? ?????? limit???
		if (result.size() < limit) {
			result.addAll(itemService.getItemRankByReview().stream().map(RecommendItem::fromEntity).limit(limit*2).collect(Collectors.toList()));
		}

		remainRandItems(result, limit);

		return result.stream().distinct().filter(Objects::nonNull).limit(limit).collect(Collectors.toList());
	}

	public List<Item> getML(String user_id, int limit) throws NotFoundException, JsonProcessingException {
		User user = userService.getUser(user_id);
		List<Bag> bags = bagService.getBagById(user_id);
		String item_id;
		if (bags.isEmpty()) {
			item_id = "111950000047"; // ?????? ??????????????? ??????????????? ????????? ?????? ????????? ?????? ?????? ???????????? ????????? ??????
		} else {
			item_id = bags.get(0).getItem_id();
		}

		JSONObject input = new JSONObject();
		input.put("skin_type", convertSkinType(user.getSkin_type()));
		input.put("skin_info", convertSkinInfo(user.getSkin_info()));
		input.put("item_name", Long.parseLong(item_id));
		input.put("recommend_data_nums", limit);

		log.info("input : " + input.toJSONString());
		JSONObject res = RequestUtils.postJSON("http://" + mlHost + ":" + mlPort + "/predict", input);
		log.info("output" + res.toJSONString());

		ObjectMapper objectMapper = new ObjectMapper();
		RecommendServiceImpl.MLResponseData data = objectMapper.readValue(res.toJSONString(),
			RecommendServiceImpl.MLResponseData.class);

		List<Item> result = new ArrayList<>();
		for (Long itemId : data.getRecommend_list()) {
			if (itemId == null) continue;
			try {
				result.add(itemService.getItemById(itemId + ""));
			} catch (NotFoundException e) {
				log.warn("item not found - " + itemId);
			}
		}

		return result;
	}

	public List<Item> getDL(String user_id, int limit) throws NotFoundException, JsonProcessingException {
		User user = userService.getUser(user_id);
		List<Review> reviews = reviewService.getReviewByUserId(user_id);
		reviews.sort(Comparator.comparing(Review::getDt));
		Item item = reviews.get(reviews.size()-1).getItem();

		JSONObject input = new JSONObject();
		input.put("skin_type", convertSkinType(user.getSkin_type()));
		input.put("skin_info", convertSkinInfo(user.getSkin_info()));
		input.put("user_id", user_id);
		input.put("age_group", user.getAge_group());
		input.put("gender", convertGender(user.getGender()));
		input.put("category", convertCategory(item.getCategory_id()));
		input.put("recommend_data_nums", limit);

		log.info("input : " + input.toJSONString());
		JSONObject res = RequestUtils.postJSON("http://" + mlHost + ":" + mlPort + "/deep_learning_predict", input);
		log.info("output" + res.toJSONString());

		ObjectMapper objectMapper = new ObjectMapper();
		RecommendServiceImpl.MLResponseData data = objectMapper.readValue(res.toJSONString(),
			RecommendServiceImpl.MLResponseData.class);


		List<Item> result = new ArrayList<>();
		for (Long itemId : data.getRecommend_list()) {
			if (itemId == null) continue;
			try {
				result.add(itemService.getItemById(itemId + ""));
			} catch (NotFoundException e) {
				log.warn("item not found - " + itemId);
			}
		}

		return result;
	}

	public static Integer convertCategory(String categoryId) {
		return Integer.parseInt(categoryId.charAt(5) + "");
	}

	public static Integer convertGender(String gender) {
		if (gender.equals("m")) return 0;
		return 1;
	}

	public static Integer convertSkinType(String skinType) {
		List<String> skinTypeMeta = Arrays.asList("??????", "??????", "??????", "??????????????????", "?????????", "?????????");
		for (int i=0; i<skinTypeMeta.size(); i++) {
			if (skinType.equals(skinTypeMeta.get(i))) {
				return i;
			}
		}

		return 2;
	}

	public static Integer convertSkinInfo(String skinInfo) {
		List<String> skinInfoMeta = Arrays.asList("?????????", "??????", "????????????", "?????????", "?????????", "?????????", "??????");
		for (int i=0; i<skinInfoMeta.size(); i++) {
			if (skinInfo.equals(skinInfoMeta.get(i))) {
				return i;
			}
		}

		return 0;
	}

	public static void remainRandItems(List<RecommendItem> list, int offset) {
		Random rand = new Random();

		for (int i=0; i< offset; i++) {
			int idx = rand.nextInt(list.size());
			list.remove(idx);
		}
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	public static class MLResponseData {
		private List<Long> recommend_list;
	}
}
