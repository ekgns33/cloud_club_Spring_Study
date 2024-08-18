package com.CloudClub.jpaStudy.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.CloudClub.jpaStudy.repository.MemberRepository;
import com.CloudClub.jpaStudy.domain.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;


	@Test
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

	@Test(expected = IllegalStateException.class)
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
		memberService.join(member2);
		// then
		fail("에러가 발생해야한다.");
	}

}