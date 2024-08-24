package com.CloudClub.jpaStudy.service;


import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.CloudClub.jpaStudy.domain.item.exception.NotEnoughStockException;
import com.CloudClub.jpaStudy.service.orders.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.CloudClub.jpaStudy.repository.OrderRepository;
import com.CloudClub.jpaStudy.domain.member.Address;
import com.CloudClub.jpaStudy.domain.item.Item;
import com.CloudClub.jpaStudy.domain.member.Member;
import com.CloudClub.jpaStudy.domain.order.Order;
import com.CloudClub.jpaStudy.domain.order.OrderStatus;
import com.CloudClub.jpaStudy.domain.item.Book;
import jakarta.persistence.EntityManager;

@SpringBootTest
class OrderServiceTest {

	@Autowired EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired OrderRepository orderRepository;

	private Item createBook(String name, int price, int stockQuantity) {
		Item book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member= Member.builder()
				.name("kim")
				.build();
		member.setAddress(new Address("a", "b", "c"));
		em.persist(member);
		return member;
	}

	@Test
	@Transactional
	public void orderProduct() throws Exception {
	    //given
		Member member = createMember();

		Item book = createBook("book1", 10000, 10);
		//when
		int orderCount = 2;

		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		//then
		Order getOrder = orderRepository.findOne(orderId);

		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야한다", 1, getOrder.getOrderItems().size());
		assertEquals("주문 가격은 가격 * 수량이다", 10000 * orderCount, getOrder.getTotalPrice());
		assertEquals("주문하면 재고가 줄어야한다,", 8, book.getStockQuantity());
	}

	@Test
	@Transactional

	public void cancelOrder() throws Exception {
	    //given
		Member member = createMember();
	    Item item = createBook("12312", 10000, 10);

		int orderCount = 2;

		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		//when
		orderService.cancelOrder(orderId);
	    //then
		Order getOrder = orderRepository.findOne(orderId);
		assertEquals("주문 상태가 CANCELdlek", OrderStatus.CANCEL, getOrder.getStatus());
		assertEquals("주문이 취소되면 재고가 다시 증가해야한다", 10, item.getStockQuantity());
	}

	@Test
	@Transactional
	public void outOfStockException() throws Exception {
	    //given
		Member member = createMember();
		Item item = createBook("ddd", 10000, 10);
		int orderCount = 11;
	    //when

			//then
		fail("재고 수량 부족 예외가 발생해야한다.");
		assertThrows(NotEnoughStockException.class,
				() -> orderService.order(member.getId(), item.getId(), orderCount));
	}
}