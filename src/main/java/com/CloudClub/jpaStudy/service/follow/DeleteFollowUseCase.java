package com.CloudClub.jpaStudy.service.follow;

import com.CloudClub.jpaStudy.domain.member.Connection;
import com.CloudClub.jpaStudy.domain.member.Member;
import com.CloudClub.jpaStudy.repository.ConnectionRepository;
import com.CloudClub.jpaStudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteFollowUseCase {

  private final MemberRepository memberRepository;
  private final ConnectionRepository connectionRepository;

  @Transactional
  public void execute(Long currentId, Long targetMemberId) {
    Member currentMember = memberRepository.findOne(currentId);
    Connection followingConnection = connectionRepository
        .findFollowingByMember(currentMember, targetMemberId)
        .orElseThrow(IllegalArgumentException::new);
    connectionRepository.delete(followingConnection);
  }
}
