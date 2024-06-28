package com.CloudClub.jpaStudy.service;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.CloudClub.jpaStudy.domain.item.Book;

import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	EntityManager em;

	@Test
	public void update() throws Exception {
	    //given
	 	Book book = new Book();

		 book.setAuthor("aaa");
		 book.setIsbn("12312");
		 book.setName("hi");
		 book.setPrice(123123);
		 ItemService.
		Book book1 = em.find(Book.class, 1L);
	    //when

	    //then

	}

}