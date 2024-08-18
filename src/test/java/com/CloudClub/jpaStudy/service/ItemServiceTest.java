package com.CloudClub.jpaStudy.service;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import jakarta.persistence.EntityManager;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class ItemServiceTest {

	@Autowired
	EntityManager em;

	@Test
	@WithMockUser
	public void update() throws Exception {


	}
	@Test
	@WithMockUser
	public void save() {}

}