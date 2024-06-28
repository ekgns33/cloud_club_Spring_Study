package com.CloudClub.jpaStudy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CloudClub.jpaStudy.ItemRepository;
import com.CloudClub.jpaStudy.MemberRepository;
import com.CloudClub.jpaStudy.OrderRepository;
import com.CloudClub.jpaStudy.OrderSearch;
import com.CloudClub.jpaStudy.domain.Delivery;
import com.CloudClub.jpaStudy.domain.Item;
import com.CloudClub.jpaStudy.domain.Member;
import com.CloudClub.jpaStudy.domain.Order;
import com.CloudClub.jpaStudy.domain.OrderItem;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	// 주문
	/*
	* 주문
	*
	* */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		Order order = Order.createOrder(member, delivery, orderItem);

		orderRepository.save(order);

		return order.getId();
	}
	// 취소

	@Transactional
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findOne(orderId);
		order.cancel();
	}

	// 검색
	// public List<Order> findOrders(OrderSearch orderSearch) {
	// 	return orderRepository,findOrders(orderSearch);
	// }
}
