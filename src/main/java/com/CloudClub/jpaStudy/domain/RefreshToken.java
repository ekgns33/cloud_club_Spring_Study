package com.CloudClub.jpaStudy.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
public class RefreshToken {

  private String token;
  private LocalDateTime expiration;

  public RefreshToken(String token, Long expiration) {
    this.token = token;
    this.expiration = LocalDateTime.now().plus(expiration, ChronoUnit.MILLIS);
  }

  public void validate(String token) {
    if (!this.token.equals(token)) {
      throw new IllegalArgumentException("Invalid refresh token");
    }
    if (LocalDateTime.now().isAfter(expiration)) {
      throw new IllegalArgumentException("Expired refresh token");
    }
  }
}
