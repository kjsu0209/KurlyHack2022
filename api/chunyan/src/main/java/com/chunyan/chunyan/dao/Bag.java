package com.chunyan.chunyan.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import com.chunyan.chunyan.controller.BagController;
import com.chunyan.chunyan.controller.ReviewController;
import com.chunyan.chunyan.dao.enums.BagStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="bag", schema = "kurly_schema")
public class Bag {
	@Id
	@Generated(GenerationTime.INSERT)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bag_id;

	private String user_id;
	private Boolean is_sample;
	@Enumerated(EnumType.STRING)
	private BagStatus status;
	private int count;
	private LocalDateTime dt;
	private String item_id;
	private String purchase_id;
	private Boolean reordered;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	private Item item;

	public static Bag fromBagDto(BagController.BagDto bagDto) {
		Bag bag = new Bag();
		BeanUtils.copyProperties(bagDto, bag, "bag_id", "purchase_id");
		if (!ObjectUtils.isEmpty(bagDto.getBag_id())) {
			bag.setBag_id(bagDto.getBag_id());
		}

		if (!ObjectUtils.isEmpty(bagDto.getPurchase_id())) {
			bag.setPurchase_id(bagDto.getPurchase_id());
		}


		bag.setDt(LocalDateTime.parse(bagDto.getDt(), DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		return bag;
	}
}
