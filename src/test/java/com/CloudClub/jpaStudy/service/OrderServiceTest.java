package com.CloudClub.jpaStudy.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.CloudClub.jpaStudy.OrderRepository;
import com.CloudClub.jpaStudy.domain.Address;
import com.CloudClub.jpaStudy.domain.Item;
import com.CloudClub.jpaStudy.domain.Member;
import com.CloudClub.jpaStudy.domain.Order;
import com.CloudClub.jpaStudy.domain.OrderStatus;
import com.CloudClub.jpaStudy.domain.item.Book;
import com.CloudClub.jpaStudy.exception.NotEnoughStockException;

import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

	@Autowired EntityManager em;
	@Autowired OrderService orderService;
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
		Member member = new Member();
		member.setName("1");
		member.setAddress(new Address("a", "b", "c"));
		em.persist(member);
		return member;
	}

	@Test
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

	@Test(expected = NotEnoughStockException.class)
	public void outOfStockException() throws Exception {
	    //given
		Member member = createMember();
		Item item = createBook("ddd", 10000, 10);
		int orderCount = 11;
	    //when


		orderService.order(member.getId(), item.getId(), orderCount);
	    //then
		fail("재고 수량 부족 예외가 발생해야한다.");
	}
}