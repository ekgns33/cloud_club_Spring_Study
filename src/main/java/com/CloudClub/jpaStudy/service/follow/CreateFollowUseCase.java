package com.CloudClub.jpaStudy.service.follow;

import com.CloudClub.jpaStudy.domain.member.Connection;
import com.CloudClub.jpaStudy.domain.member.Member;
import com.CloudClub.jpaStudy.repository.ConnectionRepository;
import com.CloudClub.jpaStudy.repository.MemberRepository;
import com.CloudClub.jpaStudy.service.follow.model.response.CreateFollowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateFollowUseCase {

  private final MemberRepository memberRepository;
  private final ConnectionRepository connectionRepository;

  @Transactional
  public CreateFollowResponse execute(Long currentMemberId, Long targetMemberId) {
    Member currentMember = memberRepository.findOne(currentMemberId);
    Member targetMember = memberRepository.findOne(targetMemberId);
    checkAlreadyFollowed(currentMember, targetMemberId);
    Connection followingConnection = Connection.createFollow(currentMember, targetMember);
    connectionRepository.save(followingConnection);
    return CreateFollowResponse.of(followingConnection);
  }

  private void checkAlreadyFollowed(Member member, Long targetMemberId) {
    connectionRepository.findFollowingByMember(member, targetMemberId)
        .ifPresent((e) -> {
          throw new IllegalStateException("Already Followed");
        });
  }

}
