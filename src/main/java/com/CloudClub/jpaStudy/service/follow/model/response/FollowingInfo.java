package com.CloudClub.jpaStudy.service.follow.model.response;

import lombok.Getter;

@Getter
public class FollowingInfo {

  private final Long id;
  private final Long followingId;
  private final String name;

  public FollowingInfo(Long id, Long followingId, String name) {
    this.id = id;
    this.followingId = followingId;
    this.name = name;
  }
}
