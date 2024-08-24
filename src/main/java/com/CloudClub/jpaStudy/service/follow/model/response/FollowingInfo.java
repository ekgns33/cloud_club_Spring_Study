package com.CloudClub.jpaStudy.service.follow.model.response;

import lombok.Getter;

@Getter
public class FollowingInfo {

  private final Long id;
  private final String name;

  public FollowingInfo(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
