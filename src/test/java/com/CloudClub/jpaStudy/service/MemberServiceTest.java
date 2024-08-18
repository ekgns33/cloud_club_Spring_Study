package com.CloudClub.jpaStudy.service;


import static org.assertj.core.api.BDDAssertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.CloudClub.jpaStudy.repository.MemberRepository;
import com.CloudClub.jpaStudy.domain.Member;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;


	@Test
	@WithMockUser
	public void signup() throws Exception{
		// given
		Member member = Member.builder()
				.name("kim")
				.build();

		// when
		Long savedId = memberService.join(member);

		// then
		assertEquals(member, memberRepository.findOne(savedId));
	}

	@Test
	@WithMockUser
	public void duplicateUserException() throws Exception {
		// given
		Member member1 = Member.builder()
				.name("kim")
				.build();

		Member member2 = Member.builder()
				.name("kim")
				.build();

		// when
		memberService.join(member1);
		// then
		assertThrows(IllegalStateException.class, () -> memberService.join(member2));
	}

}