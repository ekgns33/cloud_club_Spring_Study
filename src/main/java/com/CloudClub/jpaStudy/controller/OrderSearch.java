package com.CloudClub.jpaStudy.controller;

import com.CloudClub.jpaStudy.domain.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

	private String memberName; // member name
	private OrderStatus orderStatus;

}
