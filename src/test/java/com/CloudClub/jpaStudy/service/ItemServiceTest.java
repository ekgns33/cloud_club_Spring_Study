package com.CloudClub.jpaStudy.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	EntityManager em;

	@Test
	public void update() throws Exception {


	}
	@Test
	public void save() {}

}