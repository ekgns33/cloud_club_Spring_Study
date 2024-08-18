package com.CloudClub.jpaStudy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.CloudClub.jpaStudy.domain.Address;
import com.CloudClub.jpaStudy.domain.Member;
import com.CloudClub.jpaStudy.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/members/new")
	public String createForm(Model model) {
		model.addAttribute("memberForm", new MemberForm());
		return "members/createMemberForm";
	}

	@PostMapping("/members/new")
	public String create(MemberForm memberForm, BindingResult result) {

		if(result.hasErrors()) {
			return "members/createMemberForm";
		}

		Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

		Member member = Member.builder()
						.name(memberForm.getName())
						.build();

		member.setName(memberForm.getName());
		member.setAddress(address);

		memberService.join(member);
		return "redirect:/";
	}

	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}
