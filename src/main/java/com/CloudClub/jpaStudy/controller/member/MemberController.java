package com.CloudClub.jpaStudy.controller.member;

import com.CloudClub.jpaStudy.controller.member.dtos.MemberForm;
import com.CloudClub.jpaStudy.domain.member.Address;
import com.CloudClub.jpaStudy.domain.member.Member;
import com.CloudClub.jpaStudy.global.security.SecurityUtils;
import com.CloudClub.jpaStudy.service.follow.CreateFollowUseCase;
import com.CloudClub.jpaStudy.service.follow.DeleteFollowUseCase;
import com.CloudClub.jpaStudy.service.follow.RetrieveFollowingUseCase;
import com.CloudClub.jpaStudy.service.follow.model.response.RetrieveFollowingResponse;
import com.CloudClub.jpaStudy.service.member.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final CreateFollowUseCase followUseCase;
  private final DeleteFollowUseCase unfollowUseCase;
  private final RetrieveFollowingUseCase retrieveFollowingUseCase;

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(MemberForm memberForm, BindingResult result) {

    if (result.hasErrors()) {
      return "members/createMemberForm";
    }

    Address address = new Address(memberForm.getCity(), memberForm.getStreet(),
        memberForm.getZipcode());

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

  @PostMapping("/members/{followingId}/followers")
  public String follow(@PathVariable("followingId") Long followingId) {
    followUseCase.execute(
        SecurityUtils.getCurrentMemberId(), followingId);
    return "/members";
  }

  @DeleteMapping("/members/{followingId}/followers")
  public String unfollow(@PathVariable("followingId") Long followingId) {
    unfollowUseCase.execute(SecurityUtils.getCurrentMemberId(), followingId);
    return "/members";
  }

  @GetMapping("/members/following/{id}")
  public String getFollowingMembers(@PathVariable("id") Long memberId, Model model) {
    RetrieveFollowingResponse response = retrieveFollowingUseCase.execute(memberId);
    model.addAttribute("response", response);
    model.addAttribute("memberId", memberId);
    return "retrieve-following";
  }

  @GetMapping("/members/form")
  public String showForm() {
    return "following-form";
  }
}
