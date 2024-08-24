package com.CloudClub.jpaStudy.service.follow.model.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RetrieveFollowingResponse {

  private final List<FollowingInfo> followingInfos;

  private RetrieveFollowingResponse(List<FollowingInfo> followingInfos) {
    this.followingInfos = followingInfos;
  }

  public static RetrieveFollowingResponse of(List<FollowingInfo> followingInfos) {
    return new RetrieveFollowingResponse(followingInfos);
  }
}
