package com.chunyan.chunyan.common.converver;

import org.springframework.core.convert.converter.Converter;

import com.chunyan.chunyan.dao.enums.BagStatus;

public class StringToBagStatusConverter implements Converter<String, BagStatus> {
	@Override
	public BagStatus convert(String source) {
		try {
			return BagStatus.valueOf(source.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
