package com.CloudClub.jpaStudy.domain.member;

public enum ConnectionType {
  FOLLOW("FOLLOW"),
  BLOCK("BLOCKED");

  private final String description;

  ConnectionType(String description) {
    this.description = description;
  }
}
