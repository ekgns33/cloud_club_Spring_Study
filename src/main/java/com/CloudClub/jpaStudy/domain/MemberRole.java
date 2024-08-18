package com.CloudClub.jpaStudy.domain;

import lombok.Getter;

@Getter
public enum MemberRole {

  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER");

  private String key;

  MemberRole(String key) {
    this.key = key;
  }
}
