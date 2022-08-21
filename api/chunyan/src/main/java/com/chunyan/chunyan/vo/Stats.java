package com.chunyan.chunyan.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stats {
	private StatsValue gender;
	private StatsValue skin_type;
	private StatsValue age_group;
	private StatsValue skin_info;
	private StatsValue skin_tone;
}
