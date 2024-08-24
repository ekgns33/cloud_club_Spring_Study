package com.CloudClub.jpaStudy.service.follow;

import com.CloudClub.jpaStudy.repository.ConnectionRepository;
import com.CloudClub.jpaStudy.service.follow.model.response.FollowingInfo;
import com.CloudClub.jpaStudy.service.follow.model.response.RetrieveFollowingResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RetrieveFollowingUseCase {

  private final ConnectionRepository connectionRepository;

  @Transactional(readOnly = true)
  public RetrieveFollowingResponse execute(Long memberId) {
    List<FollowingInfo> followingInfos = connectionRepository.findFollowingByMemberId(memberId);
    return RetrieveFollowingResponse.of(followingInfos);
  }
}
