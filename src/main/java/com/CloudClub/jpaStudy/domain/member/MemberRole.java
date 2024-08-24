package com.CloudClub.jpaStudy.domain.member;

import lombok.Getter;

@Getter
public enum MemberRole {

  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER");

  private final String key;

  MemberRole(String key) {
    this.key = key;
  }
}
